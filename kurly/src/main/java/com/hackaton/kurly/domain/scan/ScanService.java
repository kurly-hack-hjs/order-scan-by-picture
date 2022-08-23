package com.hackaton.kurly.domain.scan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackaton.kurly.LongRestTemplate;
import com.hackaton.kurly.domain.Item.DetailItem;
import com.hackaton.kurly.domain.Item.Item;
import com.hackaton.kurly.domain.Item.dto.ItemsResponse;
import com.hackaton.kurly.domain.Item.dto.OrderedItemInfo;
import com.hackaton.kurly.domain.Item.repository.DetailItemRepository;
import com.hackaton.kurly.domain.Item.repository.ItemRepository;
import com.hackaton.kurly.domain.itemCart.dto.SnapshotResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ScanService {
    private final DetailItemRepository detailItemRepository;

    @Value("${ocr-key}")
    private String ocrKey;

    public List<String> contactToOcr(String imageUrl) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-OCR-SECRET", ocrKey);

        String json = "{\"images\":[{\"format\":\"jpg\",\"name\":\"demo\",\"url\":\"" +
                imageUrl +
                "\"}],\"requestId\":\"guide-json-demo\",\"version\":\"V2\",\"timestamp\":1584062336793}";
        LongRestTemplate restTemplate = new LongRestTemplate(10);
        HttpEntity<String> request = new HttpEntity<String>(json, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://uo3w7cyx2k.apigw.ntruss.com/custom/v1/17834/61a3bd339557ae2268b281c268ba43e511e4a93e5667b1e44aabb37306535dc6/general",
                request, String.class);
        System.out.println(response.toString());
        if (response.getStatusCode() == HttpStatus.OK) {
            return extractTextFromResponse(response);
        }
        return null;
    }

    private List<String> extractTextFromResponse(ResponseEntity<String> response) throws JsonProcessingException {
        //0. 감지한 텍스트는 inferText 라는 이름으로 텍스트 안에 저장된다.
        String responseBodyString = response.getBody();

        //1. json String으로 받은 responseBody를 Map 구조라고 생각하고 들여다보자.
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(responseBodyString, Map.class);
        List<Object> images = (List<Object>) map.get("images");
        Map<String, Object> firstImage = (Map<String, Object>) images.get(0);
        List<Object> fields = (List<Object>) firstImage.get("fields");
        List<String> texts = new ArrayList<>();

        //2. 근성있게 response를 들여다보면 inferText가 우리가 이용할 dataSet 을 알 수 있다.
        for (Object field : fields) {
            Map<String, Object> fieldMap = (Map<String, Object>) field;
            Object inferText = fieldMap.get("inferText");
            texts.add(inferText.toString());
        }
        // 테스트해보니 네이버 ocr은 도출된 텍스트를 set으로 준다고 생각했는데-> 실제 API에서는 좌표정보를 포함하기때문에 모두 IDENTITY 함
        // 이걸로 Pseudo relevance feedback 데이터 셋 구성
        //now text set : ["L","grow","유기농","담음","신동진쌀","·","Organic","Rice","·","자연을","담은","건강한","식탁","유기농","(ORGANIC)","농협축산식품부","유기농","담을","1","쌀","한알","한알에","신신함과","장성을","가득","당은","유기농발","궁은","쌀알,","담백한","밥맛으로","유명한","신동산발을","유기농으로","건강하게","길러냈습니다.","원산지","용량","등급","친환경","국내산","4kg","상","유기농"]
        return texts;
    }

    public List<Item> compare2DataSetForScan(SnapshotResponse itemsResponse, List<String> textListFromImage){
        List<OrderedItemInfo> itemList=itemsResponse.getItemList();
        List<DetailItem> scoreDictionary = new ArrayList<DetailItem>();
        List<Item> result = new ArrayList<>();
        //1. 필요한 단어셋 준비
        for(OrderedItemInfo itemInfo : itemList){
            Optional<DetailItem> detailItem =detailItemRepository.findById(itemInfo.getId());
            if(detailItem.isPresent()){
                scoreDictionary.add(detailItem.get());
            }
        }

        //2.준비한 단어set에,대해서, textListFromImage가 그 단어을 갖고 있는지 확인
        for(DetailItem item : scoreDictionary){
            int score = 0;
            if (textListFromImage.contains(item.getKey1())){
                score = score+3;
            }
            if (textListFromImage.contains(item.getKey2())){
                score = score+2;
            }
            if (textListFromImage.contains(item.getKey3())){
                score = score+1;
            }
            if (textListFromImage.contains(item.getKey4())){
                score = score+1;
            }
            //3. 최종 스코어가 정해놓은 점수 이상(>=)이면, 검증리스트에 추가된다.(두배이상 가점을 기록했을때?나 2개 검증할수도있을듯...)
            if (score>= item.getMinimumScore()){
                result.add(item.toItem());
            }
        }
        return result;
    }
}

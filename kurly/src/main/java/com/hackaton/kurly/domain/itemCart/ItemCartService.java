package com.hackaton.kurly.domain.itemCart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackaton.kurly.domain.Item.Item;
import com.hackaton.kurly.domain.Item.dto.OrderedItemInfo;
import com.hackaton.kurly.domain.itemCart.dto.SnapshotResponse;
import com.hackaton.kurly.domain.itemCart.repository.CartSnapshotRepository;
import com.hackaton.kurly.domain.order.ScanStatus;
import com.hackaton.kurly.domain.scan.dto.ScanRequest;
import com.hackaton.kurly.domain.snapshot.Snapshot;
import com.hackaton.kurly.domain.snapshot.SnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItemCartService {
    private final CartSnapshotRepository cartSnapshotRepository;
    private final SnapshotRepository snapshotRepository;
    private final ObjectMapper mapper;

    public void save(CartSnapshot cartSnapshot){
        cartSnapshotRepository.save(cartSnapshot);
    }


    public SnapshotResponse findCartSnapshotById(Long orderId) throws JsonProcessingException {

        CartSnapshot cartSnapshot = cartSnapshotRepository.findById(orderId).get();

        return makeSnapshotResponse(cartSnapshot, orderId);
    }

    public SnapshotResponse checkNextStatus(SnapshotResponse snapshot, List<Item> foundItems, ScanRequest scanRequest) throws IOException {
        Long orderId = scanRequest.getOrderId();
        int tryCount = scanRequest.getTryCount();
        String userId = scanRequest.getLoginId();

        List<OrderedItemInfo> stock = snapshot.getRestList();

        for(Item item : foundItems){
            for(OrderedItemInfo stockItem : stock){
                int itemId = item.getId();;
                int orderedItemId = stockItem.getId();
                if(itemId == orderedItemId ){ //1. 찾아낸 아이템 중에, 주문 목록(scanStock)에 같은 상품이 있으면
                    //2. 주문 목록의 해당 아이템에서 수량을 하나 빼서, 저장해준다.
                    System.out.println("same!!"+itemId +"/"+ orderedItemId);
                    stockItem.setCount(stockItem.getCount()-1);
                    //3. 만약 이 다음 주문목록의 해당아이템 수량이 0이면 검증 COMPLETE / 0이 아니면 수량 미충족..으로 상태 변경
                    if(stockItem.getCount() == 0){
                        stockItem.setScanStatus(ScanStatus.COMPLETE);
                    }
                    else{
                        stockItem.setScanStatus(ScanStatus.COUNT_ERROR);
                    }
                }
            }
        }

        //4. 검증후에 stock을 보면서 모두 완료됐는지 아닌지 판단함
        Set<Boolean> resultSet = new HashSet<>();
        for(OrderedItemInfo item : stock) {
            if (item.getScanStatus().equals(ScanStatus.COMPLETE)){
                resultSet.add(true);
            }
            else{
                resultSet.add(false);
            }
        }
        boolean isSatisfied = true;
        if (resultSet.contains(false)) {
            isSatisfied = false;
        }



        // 5. 123의 변경되게된 상태들만 snapshot에 copy해준다..
        snapshot.injectNextStatusFrom(stock);
        // 6. 새 상태(snapshot, stock) 와, 지금이 몇회차(tryCount)인지 DB에 저장한다...
        CartSnapshot cartSnapshot = cartSnapshotRepository.findById(orderId).get();
        cartSnapshot.updateByScanResult(formatByJson(snapshot.getSnapshot()), formatByJson(stock), tryCount);
        cartSnapshotRepository.save(cartSnapshot);

        snapshotRepository.save(new Snapshot(orderId, tryCount, cartSnapshot.getSnapshot(), scanRequest.getLoginId(), isSatisfied));
        return makeSnapshotResponse(cartSnapshot, orderId);
    }

    private String formatByJson(List<OrderedItemInfo> list) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, list);
        final byte[] data = out.toByteArray();
        return new String(data);
    }

    private SnapshotResponse makeSnapshotResponse(CartSnapshot cartSnapshot, Long orderId) throws JsonProcessingException {
        List<OrderedItemInfo> itemList = Arrays.asList(mapper.readValue(cartSnapshot.getOrderList(), OrderedItemInfo[].class));
        List<OrderedItemInfo> snapshot = Arrays.asList(mapper.readValue(cartSnapshot.getSnapshot(), OrderedItemInfo[].class));
        List<OrderedItemInfo> stock = Arrays.asList(mapper.readValue(cartSnapshot.getScanStock(), OrderedItemInfo[].class));

        return new SnapshotResponse(orderId, itemList, snapshot, stock, itemList.size(), cartSnapshot.getTryCount());
    }
}

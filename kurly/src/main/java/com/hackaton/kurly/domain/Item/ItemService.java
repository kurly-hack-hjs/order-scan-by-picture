package com.hackaton.kurly.domain.Item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackaton.kurly.domain.Item.dto.ItemsResponse;
import com.hackaton.kurly.domain.Item.repository.ItemCartRepository;
import com.hackaton.kurly.domain.Item.repository.ItemRepository;
import com.hackaton.kurly.domain.Item.dto.OrderedItemInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemCartRepository itemCartRepository;
    private final ObjectMapper mapper;

    public ItemsResponse findOneItemCartByOrderId(Long orderId) throws IOException {
        List<OrderedItemInfo> list = new ArrayList<>();
        list.add(new OrderedItemInfo(UUID.randomUUID(), "[가농바이오] 오메가에그 무항생제 1+등급 특란 10구", 2));
        list.add(new OrderedItemInfo(UUID.randomUUID(), "[풀무원] 파기름 볶음면 2인분", 1));
        list.add(new OrderedItemInfo(UUID.randomUUID(), "[연세우유 x 마켓컬리] 전용목장우유 900mL", 2));
        list.add(new OrderedItemInfo(UUID.randomUUID(), "[다향오리] 훈제오리 슬라이스 150g (3개입)", 1));

        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        mapper.writeValue(out, list);

        final byte[] data = out.toByteArray();
        String json = new String(data);
        //ItemCart itemCart = new ItemCart(orderId, json);
        // itemCartRepository.save(itemCart);
        List<OrderedItemInfo> itemList = Arrays.asList(mapper.readValue(json, OrderedItemInfo[].class));
        return new ItemsResponse(orderId, itemList);
    }

}

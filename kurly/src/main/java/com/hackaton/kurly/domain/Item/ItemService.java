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
import java.util.*;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemCartRepository itemCartRepository;
    private final ObjectMapper mapper;

    public ItemsResponse findOneItemCartByOrderId(Long orderId) throws IOException {

        ItemCart itemCart = itemCartRepository.findById(orderId).get();
        /*List<OrderedItemInfo> list = new ArrayList<>();


        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        mapper.writeValue(out, list);

        final byte[] data = out.toByteArray();
        String json = new String(data);
        //ItemCart itemCart = new ItemCart(orderId, json);

        // itemCartRepository.save(itemCart);*/
        List<OrderedItemInfo> itemList = Arrays.asList(mapper.readValue(itemCart.getOrderList(), OrderedItemInfo[].class));

        return new ItemsResponse(orderId, itemList, itemList.size());
    }

}

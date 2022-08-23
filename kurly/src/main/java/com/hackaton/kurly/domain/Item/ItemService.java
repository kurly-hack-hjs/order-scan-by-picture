package com.hackaton.kurly.domain.Item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackaton.kurly.domain.Item.dto.ItemsResponse;
import com.hackaton.kurly.domain.Item.repository.DetailItemRepository;
import com.hackaton.kurly.domain.itemCart.repository.ItemCartRepository;
import com.hackaton.kurly.domain.Item.repository.ItemRepository;
import com.hackaton.kurly.domain.Item.dto.OrderedItemInfo;
import com.hackaton.kurly.domain.itemCart.ItemCart;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemCartRepository itemCartRepository;
    private final DetailItemRepository detailItemRepository;
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

    public Page<DetailItem> findDetailItems(Pageable pageable){
        return detailItemRepository.findAll(pageable);
    }

}

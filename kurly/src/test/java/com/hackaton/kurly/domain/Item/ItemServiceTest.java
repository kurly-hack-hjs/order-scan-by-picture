package com.hackaton.kurly.domain.Item;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackaton.kurly.domain.Item.origin.Item;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

class ItemServiceTest {

    @Test
    public void canItemsParseToStringLikeJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<Item> list = new ArrayList<>();
        list.add(new Item(UUID.randomUUID(), "볶음밥"));
        list.add(new Item(UUID.randomUUID(), "즉석밥"));
        list.add(new Item(UUID.randomUUID(), "햇반"));
        list.add(new Item(UUID.randomUUID(), "컵라면"));


        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, list);
        final byte[] data = out.toByteArray();

        System.out.println(new String(data));
        //예: [{"id":"2c8c8f6e-ab5b-43e7-b099-f20798488992","name":"볶음밥"},{"id":"673a0bbc-aca7-4871-be60-891522174ac9","name":"즉석밥"},{"id":"16399cf7-0728-4bab-be0a-a3d8d62e96a7","name":"햇반"},{"id":"436949e3-3755-4de9-aa0c-eebb098ae520","name":"컵라면"}]
    }

}
package com.hackaton.kurly.domain.Item;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

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

    @Test
    public void reverse(){
        ObjectMapper mapper = new ObjectMapper();
        String json = "[{\"id\":\"8a602958-cbd9-46fc-af24-8c055f514864\",\"name\":\"푸딩\",\"count\":2},{\"id\":\"95cbfa01-57f8-4097-9e35-66e346156106\",\"name\":\"오렌지주스\",\"count\":1},{\"id\":\"4b64fc14-64b6-482f-8c84-2abb395c77c9\",\"name\":\"병아리만주\",\"count\":2}]";

        try {

            // convert JSON string to Map
            //Map<String, String> map
            List<Object> list = Arrays.asList(mapper.readValue(json, Object[].class));

            // it works
            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});


            System.out.println(list);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
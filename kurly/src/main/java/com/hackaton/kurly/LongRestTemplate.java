package com.hackaton.kurly;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class LongRestTemplate extends RestTemplate {

    public LongRestTemplate(int readTimeout) {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(1000);
        factory.setReadTimeout(readTimeout * 1000);
        setRequestFactory(factory);
    }
}

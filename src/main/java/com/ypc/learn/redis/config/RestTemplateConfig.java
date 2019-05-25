package com.ypc.learn.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: ypcfly
 * @Date: 19-5-20 20:54
 * @Description:
 */
@Configuration
public class RestTemplateConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateConfig.class);

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        LOGGER.info(">>>> custom rest template configuration start <<<<");
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        ClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        // 连接和读取超时时间
        ((SimpleClientHttpRequestFactory) clientHttpRequestFactory).setConnectTimeout(10000);
        ((SimpleClientHttpRequestFactory) clientHttpRequestFactory).setReadTimeout(10000);
        // 设置是否缓冲请求体
        ((SimpleClientHttpRequestFactory) clientHttpRequestFactory).setBufferRequestBody(false);

        return clientHttpRequestFactory;
    }
}

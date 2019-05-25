package com.ypc.learn.redis.service.impl;

import com.google.gson.Gson;
import com.ypc.learn.redis.dto.BookDTO;
import com.ypc.learn.redis.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author: ypcfly
 * @Date: 19-5-20 20:41
 * @Description:
 */
@Service
public class TestServiceImpl implements TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    private Gson gson = new Gson();

    @Override
    public Map<String, Object> getUserList() {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",false);
        resultMap.put("code",400);

        // 请求参数request
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor("ypc");

        // 创建一个线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<ResponseEntity> callable = () -> {
            try {
                return restTemplate.postForEntity("http://localhost:9090/redis/getList",bookDTO,Object.class);
            } catch (Exception e){
                LOGGER.error(">>>> call rest API error,error message:{} <<<<",e.getMessage());
                return null;
            }
        };

        Future<ResponseEntity> future = executorService.submit(callable);

        ResponseEntity responseEntity = null;
        Object result = null;
        try {
            responseEntity = future.get(10,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            LOGGER.error(">>>> call rest API time out exception <<<<");
            resultMap.put("message","请求接口超时，没有获取到结果");
        } finally {
            // 关闭线程
            executorService.shutdown();
            if (responseEntity != null && responseEntity.getStatusCodeValue() == 200) {
                result = responseEntity.getBody();
                LOGGER.info("object result={}",gson.toJson(result));
                resultMap.put("success",true);
                resultMap.put("code",200);
                resultMap.put("message","成功");
                resultMap.put("result",result);
            } else if (responseEntity != null){
                // 返回状态不是200
                LOGGER.info("rest result={}",gson.toJson(responseEntity));
                resultMap.put("success",true);
                resultMap.put("code",responseEntity.getStatusCodeValue());
                resultMap.put("message",responseEntity.getBody());
            }
        }

        return resultMap;
    }
}

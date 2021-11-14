package com.elon.rpcproxy;

import com.elon.model.Result;
import com.google.common.base.Joiner;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTP远程接口调用封装服务.
 *
 * @author elon
 * @since 2021/10/17
 */
@Component
@Api(tags = "HTTP远程接口调用封装服务")
public class HTTPRemoteInvokeService {
    @Autowired
    @Qualifier("httpRestConfigTemplate")
    private RestTemplate restTemplate;

    /**
     * 请求GET方法
     *
     * @param url 请求接口的URL
     * @param headerMap 请求头参数
     * @param requestParamMap 请求参数列表
     * @return 返回值
     * @author elon
     */
    public <T> Result<T> requestGet(String url, Map<String, String> headerMap, Map<String, Object> requestParamMap){
        HttpHeaders httpHeaders = new HttpHeaders();
        headerMap.forEach(httpHeaders::add);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        StringBuilder sb = new StringBuilder();
        sb.append(url);

        List<String> paramList = new ArrayList<>();
        requestParamMap.forEach((key, value)->paramList.add(key + "=" + value));
        if (!paramList.isEmpty()) {
            sb.append("?").append(Joiner.on("&").join(paramList));
        }
        ResponseEntity<Result<T>> result = restTemplate.exchange(sb.toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<Result<T>>() {});
        return result.getBody();
    }
}

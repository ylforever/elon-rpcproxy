package com.elon.rest;

import com.elon.rpcproxy.HTTPRemoteInvokeService;
import com.elon.rpcproxy.HTTPSRemoteInvokeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * RPC代理接口测试类
 *
 * @author elon
 * @since 2021/10/17
 */
@RestController
@RequestMapping("/v1/rpc-proxy")
public class RPCTestController {
    private static final String HTTP_URL = "http://localhost:10001/elon-springboot-https";

    private static final String HTTPS_URL = "https://localhost:10000/elon-springboot-https";

    @Resource
    private HTTPRemoteInvokeService httpRemoteInvokeService;

    @Resource
    private HTTPSRemoteInvokeService httpsRemoteInvokeService;

    @GetMapping("test-http-get")
    @ApiOperation(value = "测试 HTTP GET")
    public String TestHttpGet(@RequestParam(value = "param") String param){
        String url = HTTP_URL + "/v1/https-controller/get";
        Map<String, String> headerMap =  new HashMap<>();
        headerMap.put("account", "elon");

        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("param", param);
        String result = httpRemoteInvokeService.requestGet(url, headerMap, requestParamMap);
        return result;
    }

    @GetMapping("test-https-get")
    @ApiOperation(value = "测试 HTTPS GET")
    public String TestHttpsGet(@RequestParam(value = "param") String param){
        String url = HTTPS_URL + "/v1/https-controller/get";
        Map<String, String> headerMap =  new HashMap<>();
        headerMap.put("account", "elon");

        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("param", param);
        String result = httpsRemoteInvokeService.requestGet(url, headerMap, requestParamMap);
        return result;
    }
}

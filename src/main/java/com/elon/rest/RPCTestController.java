package com.elon.rest;

import com.elon.model.Result;
import com.elon.model.User;
import com.elon.rpcproxy.HTTPRemoteInvokeService;
import com.elon.rpcproxy.HTTPSRemoteInvokeService;
import com.elon.service.FileService;
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

    private static final String HTTPS_URL = "https://localhost:10002/elon-springboot-https";

    @Resource
    private HTTPRemoteInvokeService httpRemoteInvokeService;

    @Resource
    private HTTPSRemoteInvokeService httpsRemoteInvokeService;

    @Resource
    private FileService fileService;

    @GetMapping("test-http-get")
    @ApiOperation(value = "测试 HTTP GET")
    public Result<User> TestHttpGet(@RequestParam(value = "param") String param){
        String url = HTTP_URL + "/v1/https-controller/get";
        Map<String, String> headerMap =  new HashMap<>();
        headerMap.put("account", "elon");

        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("userName", param);
        Result<User> result = httpRemoteInvokeService.requestGet(url, headerMap, requestParamMap);
        return result;
    }

    @GetMapping("test-https-get")
    @ApiOperation(value = "测试 HTTPS GET")
    public Result<User> TestHttpsGet(@RequestParam(value = "param") String param){
        String url = HTTPS_URL + "/v1/https-controller/get";
        Map<String, String> headerMap =  new HashMap<>();
        headerMap.put("account", "elon");

        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("userName", param);
        Result<User> result = httpsRemoteInvokeService.requestGet(url, headerMap, requestParamMap);
        return result;
    }

    /**
     * 下载文件到本地
     *
     * @param fileName 文件名
     * @return 文件字节数
     */
    @GetMapping("/download-file")
    @ApiOperation(value = "下载文件")
    public Result<Integer> downloadFile(@RequestParam(value = "fileName") String fileName) {
        int bytesAmount = fileService.downloadFile(fileName);
        return Result.createSuccessResult(bytesAmount);
    }
}

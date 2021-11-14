package com.elon.service;

import com.elon.rpcproxy.HTTPSRemoteInvokeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;

@Component
public class FileService {
    private static final Logger LOGGER = LogManager.getLogger(FileService.class);

    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator + "file";

    private static final String HTTPS_URL = "https://localhost:10002/elon-springboot-https";

    @Resource
    private HTTPSRemoteInvokeService httpsRemoteInvokeService;

    /**
     * 下载文件到本地临时文件夹.
     *
     * @param fileName 文件名
     * @return 文件字节数
     * @author elon
     */
    public int downloadFile(String fileName) {
        String url = HTTPS_URL + "/v1/https-controller/download-file";
        byte[] bytes = httpsRemoteInvokeService.downloadFile(url, fileName);

        byte[] bytes2 = new byte[bytes.length];
        InputStream inputStream = write2InputStream(bytes);
        try {
            inputStream.read(bytes2);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        write2file(bytes2, fileName);
        return bytes.length;
    }

    /**
     * 将字节流写入临时文件
     *
     * @param bytes    字节数组
     * @param fileName 文件名称
     */
    private void write2file(byte[] bytes, String fileName) {
        String fullFilePath = FILE_PATH + File.separator + fileName;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fullFilePath);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } catch (Exception e) {
            LOGGER.error("Write file exception.", e);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    LOGGER.error("Close file exception.", e);
                }
            }
        }
    }

    /**
     * 将文件字节数组写入到输入流中.
     *
     * @param bytes 字节数组
     * @return 输入流
     * @author elon
     */
    private InputStream write2InputStream(byte[] bytes) {
        InputStream input = new ByteArrayInputStream(bytes);
        return input;
    }
}

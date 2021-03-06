package com.luckybag.fileserver;

import com.izkml.mlyun.fs.sdk.common.FSUploadFileParam;
import com.izkml.mlyun.fs.sdk.request.MlyunFSUploadRequest;
import com.zkml.api.ApiException;
import com.zkml.api.DefaultMlyunClient;
import com.zkml.api.MlyunClient;
import com.zkml.api.MlyunResponse;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileServerUtils {
    public static Logger logger = Logger.getLogger(FileServerUtils.class);
    public static MlyunClient testClient = new DefaultMlyunClient("http://10.5.4.60:41002");

    /**
     * 公司文件服务器上传文件
     *
     * @param args
     * @throws ApiException
     * @throws IOException
     */
    public static void main(String[] args) throws ApiException, IOException {

        //把目录下所有文件上传，结果会保存在项目目录/logs/hrssc-log.log文件中
        String filePath = "D:\\data\\Intellij\\LuckyBag\\src\\main\\resources\\images020723";
        File file = new File(filePath);
        File[] files = file.listFiles();
        for (File f : files) {
            uploadFile(f.getPath());
        }
    }

    private static void uploadFile(String path) throws ApiException, IOException {
//         String filePath = "src/main/resources/images/1.jpg";
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        FSUploadFileParam fsUploadFileParam = new FSUploadFileParam();
        MultipartFile testFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.IMAGE_JPEG.toString(), fileInputStream);
        fsUploadFileParam.setFile(testFile);
        MlyunFSUploadRequest mlyunFSUploadRequest = new MlyunFSUploadRequest(fsUploadFileParam);

//        MlyunClient testClient = new DefaultMlyunClient("http://10.5.4.60:41002");
        MlyunResponse execute = testClient.execute(mlyunFSUploadRequest);
        logger.info(path + ": " + execute.getBody());

        System.out.println(execute.getBody());
    }


    @Test
    public void testMultiFile() throws IOException {

        String filePath = "src/main/resources/images/1.jpg";
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        // MockMultipartFile(String name, @Nullable String originalFilename, @Nullable String contentType, InputStream contentStream)
        // 其中originalFilename,String contentType 旧名字，类型  可为空
        // ContentType.APPLICATION_OCTET_STREAM.toString() 需要使用HttpClient的包
        MultipartFile multipartFile = new MockMultipartFile("copy" + file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        System.out.println(multipartFile.getName()); // 输出copytest.txt

    }

    @Test
    public void testLog() throws IOException {
        String filePath = "src/main/resources/images";
        File file = new File(filePath);
        File[] files = file.listFiles();
        for (File f : files) {
            logger.info(f.getPath());
        }

    }


}

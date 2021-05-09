package com.fxsh.file.server;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class FileOperationServiceImpl implements FileOperationService{
    @Autowired
    private MinioClient minioClient;
    @Value("${minio.server.endpoint}")
    private String endpoint;
    @Override
    public String uploadAvatar(MultipartFile file) {
        String bucketName = "avatars"; // 桶名
        String folderName = DateUtil.today(); // 文件夹名
        String objectName = folderName + "/" + IdUtil.simpleUUID(); // 文件名
        String suffix = "";
        String[] fileNames = file.getOriginalFilename().split("\\.");
        if (fileNames.length > 1){
            suffix = fileNames[fileNames.length-1];
            objectName += "." + suffix;
        }
        PutObjectOptions putObjectOptions = new PutObjectOptions(file.getSize(),PutObjectOptions.MIN_MULTIPART_SIZE);
        putObjectOptions.setContentType("image/jpeg");
        try {
            minioClient.putObject(bucketName,objectName,file.getInputStream(),putObjectOptions);
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        String fileUrl = "{}/{}/{}";
        fileUrl = StrUtil.format(fileUrl,endpoint,bucketName,objectName);
        return fileUrl;
    }
}

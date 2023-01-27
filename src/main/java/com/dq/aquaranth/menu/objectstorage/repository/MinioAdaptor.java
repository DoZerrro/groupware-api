package com.dq.aquaranth.menu.objectstorage.repository;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

/**
 * MinIO Object Storage를 제어합니다.
 *
 * @author 김민준
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class MinioAdaptor {

    private final MinioClient minioClient;
    @Value("${minio.bucket.name}")
    private String bucketName;

    /**
     * HTTP 프로토콜로 들어오는 multipart/form-data를
     * Object Storage에 저장합니다.
     *
     * @param multipartFile
     * @throws Exception
     * @author 김민준
     */
    public void putObject(String filename, MultipartFile multipartFile) throws Exception {
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                .contentType(multipartFile.getContentType())
                .build());
    }

    /**
     * 요청 DTO 내에 있는 파일 이름을 통해
     * Object Storage 내에 해당하는 파일을
     * 받을 수 있는 URL을 리턴합니다.
     * 만료시간은 2시간 입니다.
     *
     * @throws Exception
     * @author 김민준
     */
    public String getPresignedObjectUrl(String filename) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(filename)
//                .expiry(2, TimeUnit.HOURS)
                .build());
    }

    public String getPresignedObjectUrl(String filename, Integer time) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(filename)
                .expiry(time, TimeUnit.HOURS)
                .build());
    }
}

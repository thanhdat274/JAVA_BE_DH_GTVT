package vn.com.javaapi.config.minio;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class MinIOClientManager {
    private static final int URL_TIME_SHARE_DEFAULT = 180;
    private static final long OBJECT_SIZE = -1;
    private static final long PART_SIZE = 10485760;

    private final MinioClient minioClient;

    public MinIOClientManager(MinioClient minioClient) {
        this.minioClient = minioClient;
    }


    public void uploadFileToMinIO(String bucket, String fileName, ByteArrayInputStream inputStream) throws Exception {
        try {
            log.info("Begin upload file: {}, to bucket: {}", fileName, bucket);
            makeBucket(minioClient, bucket);
            PutObjectArgs putObjectArgs = new PutObjectArgs.Builder()
                .contentType("application/octet-stream")
                .bucket(bucket)
                .object(fileName)
                .stream(inputStream, OBJECT_SIZE, PART_SIZE)
                .build();
            minioClient.putObject(putObjectArgs);
            log.info("End upload file: {}, to bucket: {} is success", fileName, bucket);
        } catch (Exception e) {
            log.debug("Upload file to MinIO error:", e);
            throw e;
        }
    }


    public String getUrlPublicFromMinIO(String bucket, String fileName) throws Exception {
        return getUrlPublicFromMinIO(bucket, fileName, URL_TIME_SHARE_DEFAULT);
    }


    public String getUrlPublicFromMinIO(String bucket, String fileName, int timeShareFile) throws Exception {
        try {
            log.info("Begin get url public from MinIO with bucket: {}, fileName : {}", bucket, fileName);
            String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucket)
                    .object(fileName)
                    .expiry(timeShareFile)
                    .build());
            log.info("End get url public from MinIO success with url: {}", url);
            return url;
        } catch (Exception e) {
            log.debug("Get url public from MinIO error:", e);
            throw e;
        }
    }


    private void makeBucket(MinioClient minioClient, String bucket) throws IOException,
        InvalidKeyException,
        NoSuchAlgorithmException,
        InsufficientDataException,
        InternalException,
        ErrorResponseException,
        XmlParserException,
        ServerException,
        InvalidResponseException {
        if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build()))
            return;
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
    }
}

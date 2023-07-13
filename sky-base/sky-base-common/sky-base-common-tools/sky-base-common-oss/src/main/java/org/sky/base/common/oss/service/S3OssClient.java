package org.sky.base.common.oss.service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.sky.base.common.oss.OssProperties;
import org.springframework.beans.factory.InitializingBean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * <p>
 * aws-s3 通用存储操作
 * 兼容s3协议的云存储：{阿里云OSS、腾讯云OSS、七牛云、京东云、minio、AmazonOSS等}
 * </p>
 *
 * @author liusongling
 * @since 2023-07-08 22:40:32
 */
@RequiredArgsConstructor
public class S3OssClient implements InitializingBean {
    private final OssProperties ossProperties;
    private AmazonS3 amazonS3;

    /**
     * 创建桶
     *
     * @param bucketName bucket名称
     * @see <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_CreateBucket.html">AWS API Documentation</a>
     */
    public void createBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
        }
    }

    /**
     * 获取指定桶
     *
     * @param bucketName bucket名称
     * @return {@link Optional}<{@link Bucket}>
     * @see <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_ListBuckets.html">AWS API Documentation</a>
     */
    public Optional<Bucket> getBucket(String bucketName) {
        return amazonS3.listBuckets().stream().filter(bucket -> bucket.getName().equals(bucketName)).findFirst();
    }

    /**
     * 得到所有桶
     *
     * @return {@link List}<{@link Bucket}>
     * @see <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_ListBuckets.html">AWS API Documentation</a>
     */
    public List<Bucket> getAllBuckets() {
        return amazonS3.listBuckets();
    }

    /**
     * 删除桶
     *
     * @param bucketName bucket名称
     * @see <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_DeleteBucket.html">AWS API Documentation</a>
     */
    public void removeBucket(String bucketName) {
        amazonS3.deleteBucket(bucketName);
    }

    /**
     * 保存对象
     *
     * @param bucketName  bucket名称
     * @param objectName  对象名字
     * @param inputStream 输入流
     * @param size        大小
     * @param contentType 内容类型
     * @see <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_PutObject.html">AWS API Documentation</a>
     */
    @SneakyThrows
    public PutObjectResult putObject(String bucketName, String objectName, InputStream inputStream, long size, String contentType) {
        byte[] bytes = IOUtils.toByteArray(inputStream);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        objectMetadata.setContentType(contentType);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return amazonS3.putObject(bucketName, objectName, byteArrayInputStream, objectMetadata);
    }

    /**
     * 获取对象信息
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @return {@link S3Object}
     * @see <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_GetObject.html">AWS API Documentation</a>
     */
    public S3Object getObjectInfo(String bucketName, String objectName) {
        return amazonS3.getObject(bucketName, objectName);
    }

    /**
     * 根据前缀查询对象
     *
     * @param bucketName bucket名称
     * @param prefix     前缀名称
     * @param recursive  是否递归查询
     * @return {@link List}<{@link S3ObjectSummary}>
     * @see <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_ListObjectsV2.html">AWS API Documentation</a>
     */
    public List<S3ObjectSummary> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) {
        ListObjectsV2Result objectListing = amazonS3.listObjectsV2(bucketName, prefix);
        return new ArrayList<>(objectListing.getObjectSummaries());
    }

    /**
     * 获取对象外链
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @param expires    过期时间 <= 7
     * @return {@link String} URL
     */
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        Date date = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, expires);
        URL url = amazonS3.generatePresignedUrl(bucketName, objectName, calendar.getTime());
        return url.toString();
    }

    /**
     * 删除对象
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @see <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_DeleteObject.html">AWS API Documentation</a>
     */
    public void removeObject(String bucketName, String objectName) {
        amazonS3.deleteObject(bucketName, objectName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setMaxConnections(ossProperties.getMaxConnections());

        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                ossProperties.getEndPoint(), ossProperties.getRegion());
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(ossProperties.getAccessKey(),
                ossProperties.getSecretKey());
        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(basicAWSCredentials);
        this.amazonS3 = AmazonS3Client.builder()
                .withClientConfiguration(clientConfiguration)
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(awsStaticCredentialsProvider)
                .disableChunkedEncoding()
                .withPathStyleAccessEnabled(ossProperties.getPathStyleAccess())
                .build();
    }
}

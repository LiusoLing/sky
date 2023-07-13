package org.sky.base.common.oss.http;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.sky.base.common.oss.service.S3OssClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * aws 对外服务端点
 * </p>
 *
 * @author liusongling
 * @since 2023-07-08 22:37:53
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oss")
public class OssEndPoint {
    private final S3OssClient s3OssClient;

    /**
     * 创建桶
     *
     * @param bucketName bucket名称
     * @return {@link Bucket} 桶
     */
    @SneakyThrows
    @PostMapping("/bucket/{bucketName}")
    public Bucket createBucket(@PathVariable String bucketName) {
        s3OssClient.createBucket(bucketName);
        return s3OssClient.getBucket(bucketName).orElse(null);

    }

    /**
     * 获取所有桶
     *
     * @return {@link List}<{@link Bucket}>
     */
    @SneakyThrows
    @GetMapping("/bucket")
    public List<Bucket> getBuckets() {
        return s3OssClient.getAllBuckets();
    }

    /**
     * 获取指定桶
     *
     * @param bucketName bucket名称
     * @return {@link Bucket}
     */
    @SneakyThrows
    @GetMapping("/bucket/{bucketName}")
    public Bucket getBucket(@PathVariable String bucketName) {
        return s3OssClient.getBucket(bucketName).orElseThrow(() -> new IllegalArgumentException("Bucket Name not found!"));
    }

    /**
     * 删除桶
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    @DeleteMapping("/bucket/{bucketName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBucket(@PathVariable String bucketName) {
        s3OssClient.removeBucket(bucketName);
    }

    /**
     * 创建对象
     *
     * @param object     对象
     * @param bucketName bucket名称
     * @return {@link S3Object}
     */
    @SneakyThrows
    @PostMapping("/object/{bucketName}")
    public S3Object createObject(@RequestBody MultipartFile object, @PathVariable String bucketName) {
        String name = object.getOriginalFilename();
        s3OssClient.putObject(bucketName, name, object.getInputStream(), object.getSize(), object.getContentType());
        return s3OssClient.getObjectInfo(bucketName, name);
    }

    /**
     * 创建对象
     *
     * @param object     对象
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @return {@link S3Object}
     */
    @SneakyThrows
    @PostMapping("/object/{bucketName}/{objectName}")
    public S3Object createObject(@RequestBody MultipartFile object, @PathVariable String bucketName, @PathVariable String objectName) {
        s3OssClient.putObject(bucketName, objectName, object.getInputStream(), object.getSize(), object.getContentType());
        return s3OssClient.getObjectInfo(bucketName, objectName);
    }

    /**
     * 匹配模糊对象
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @return {@link List}<{@link S3ObjectSummary}>
     */
    @SneakyThrows
    @GetMapping("/object/{bucketName}/{objectName}")
    public List<S3ObjectSummary> filterObject(@PathVariable String bucketName, @PathVariable String objectName) {
        return s3OssClient.getAllObjectsByPrefix(bucketName, objectName, true);
    }

    /**
     * 获取指定对象
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @param expires    到期
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @SneakyThrows
    @GetMapping("/object/{bucketName}/{objectName}/{expires}")
    public Map<String, Object> getObject(@PathVariable String bucketName, @PathVariable String objectName, @PathVariable Integer expires) {
        Map<String, Object> responseBody = new HashMap<>(8);
        // Put Object info
        responseBody.put("bucket", bucketName);
        responseBody.put("object", objectName);
        responseBody.put("url", s3OssClient.getObjectURL(bucketName, objectName, expires));
        responseBody.put("expires", expires);
        return responseBody;
    }

    /**
     * 删除对象
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     */
    @SneakyThrows
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/object/{bucketName}/{objectName}/")
    public void deleteObject(@PathVariable String bucketName, @PathVariable String objectName) {
        s3OssClient.removeObject(bucketName, objectName);
    }
}

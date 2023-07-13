# 服务端点

提供OSS基础操作能力
- 【POST】  创建桶：/oss/bucket/{bucketName}
- 【GET】   获取所有桶：/oss/bucket
- 【GET】   获取指定桶：/oss/bucket/{bucketName}
- 【DELETE】删除桶：/oss/bucket/{bucketName}
- 【POST】  创建对象：/oss/object/{bucketName}
- 【POST】  创建对象：/oss/object/{bucketName}/{objectName}
- 【GET】   匹配模糊对象：/oss/object/{bucketName}/{objectName}
- 【GET】   获取对象外链：/oss/object/{bucketName}/{objectName}/{expires}
- 【DELETE】删除对象：/oss/object/{bucketName}/{objectName}

# 配置参考

```yaml
oss:
  enable: true
  http: true
  endPoint: http://127.0.0.1:9000
  domain: https://oss.xxx.com/sky
  pathStyleAccess: false # 采用nginx反向代理或者AWS S3 配置成true，支持第三方云存储配置成false
  access-key: adnuahi23eadwerB87Sdfu 
  secret-key: @fasdai212o090bsyye;qiau 
  bucket-name: sky 
  region: shenzhen
```
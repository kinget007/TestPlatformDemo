##配置
####application.yml
* defaultZone: http://admin:admin@localhost:8761/eureka/ 对应于服务注册发现服务的地址即registry部署
* spring.data.mongodb.uri: mongodb://localhost:27017 数据库地址
* server.port: 8080 服务启动端口

####bootstrap.yml
* spring.cloud.config.uri: http://admin:admin@localhost:8761/config  对应于服务注册发现服务的地址即registry部署

####服务部署顺序
* 部署服务注册与发现服务（registry）
* 部署Auth2服务（auth）
* 部署网关服务（gateway）
* 部署其他服务（api_service、jenkins_service、api_manage_service）
* 部署webui（webui）

####服务部署
* 打包：./mvnw -Pprod clean package
* 运行：java -jar target/*.war

####Dev
* 按照顺序在不同项目中运行命令

```
./mvnw
```


####focked repository:

* [jhipster](https://jhipster.github.io/)
* [restfiddle](https://github.com/kinget007/restfiddle)
* [java-client-api](https://github.com/jenkinsci/java-client-api)

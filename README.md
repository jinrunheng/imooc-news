## imooc-news

#### Swagger2 在线调试地址

Swagger2 是一个可以根据代码自动生成 API 文档的框架，用于生成，描述，调用可视化 RESTful 风格的 Web 服务。

在启用服务后，可以通过以下地址来进行文档查阅与在线调试：

- dev-service-user

    http://user.imoocnews.com:8003/doc.html
    
#### 使用 Docker + Flyway 启动 MySQL 容器，并完成数据的初始化

- 下载 MySQL 镜像
    ```bash
    docker pull mysql:8.0.16
    ```
- 运行 MySQL 容器   
    ```bash
    docker run --name imooc-news -e MYSQL_ROOT_PASSWORD=123 -e MYSQL_DATABASE=imooc-news-dev -p 3306:3306 -d mysql
    ```
- 进入到项目的子目录 `mybatis-generator` 下 ，使用 Flyway 完成数据的初始化
    ```bash
    mvn flyway:clean flyway:migrate
    ```

## imooc-news


#### 使用 Docker + Flyway 运行 MySQL 容器，并完成数据的初始化

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

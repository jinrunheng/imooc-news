## imooc-news

#### 运行前端项目

- 下载 Tomcat：[下载地址](https://tomcat.apache.org/)；我使用的版本为 apache-tomcat-8.5.69
- 解压安装包后进入到 bin 目录，并运行 startup 启动程序；使用命令：`./startup.sh`
- 可以通过修改 config 目录下的 server.xml 来自定义 tomcat 访问的端口号；浏览器访问 `localhost:[port]`，默认端口号为 `8080`
- 在 webapps 目录下，放入整个前端项目(拷贝整个 imooc-news 目录)
- 浏览器访问：`localhost:[port]/imooc-news/portal/index.html`

#### 使用 SwitchHosts 绑定虚拟域名

- 下载 SwitchHosts：[下载地址](https://swh.app/zh/)
- 通过本机内网 ip 地址绑定虚拟域名；MacOS 可以通过命令 `ifconfig` 查看内网 ip 地址
- 配置
    ```properties
     192.168.43.15 www.imoocnews.com
     192.168.43.15 writer.imoocnews.com
     192.168.43.15 admin.imoocnews.com
     
     192.168.43.15 article.imoocnews.com
     192.168.43.15 user.imoocnews.com
     192.168.43.15 files.imoocnews.com 
    ```

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

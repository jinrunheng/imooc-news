## imooc-news

### 项目使用技术栈

- Spring Cloud(Hoxton.SR3)
    - xxx
    - xxx
- Docker
- Flyway
- Swagger2
- 腾讯云短信服务

### 如何在本地运行该项目
#### 1. 将项目 clone 到本地，并刷新 Maven 依赖
执行命令：
```bash
git clone git@github.com:jinrunheng/imooc-news.git
```
然后使用 IDE 打开，刷新 Maven 下载项目所需要到依赖

#### 2. 运行前端项目

- 下载 Tomcat：[下载地址](https://tomcat.apache.org/)；我使用的版本为 apache-tomcat-8.5.69
- 解压安装包后，进入到 webapps 目录下，放入整个前端项目(拷贝项目根目录下的 imooc-news 目录)
- 进入到 apache-tomcat 目录下的 bin 目录，并运行 startup 启动程序；使用命令：`./startup.sh`
- 我们可以通过修改 config 目录下的 server.xml 来自定义 tomcat 访问的端口号；浏览器访问 `localhost:[port]`，默认端口号为 `8080`
- 浏览器访问：`localhost:[port]/imooc-news/portal/index.html`

#### 3. 使用 SwitchHosts 绑定虚拟域名

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
  
#### 4. 使用 Docker + Flyway 启动 MySQL 容器，并完成数据的初始化

- 下载 MySQL 镜像
    ```bash
    docker pull mysql:8.0.16
    ```
- 运行 MySQL 容器   
    ```bash
    docker run --name imooc-news -e MYSQL_ROOT_PASSWORD=123 -e MYSQL_DATABASE=imooc-news-dev -e TZ=Asia/Shanghai -p 3306:3306 -d mysql
    ```
- 进入到项目的子目录 `mybatis-generator` 下 ，使用 Flyway 完成数据的初始化
    ```bash
    mvn flyway:clean flyway:migrate
    ```

#### 5. Swagger2 在线调试地址

Swagger2 是一个可以根据代码自动生成 API 文档的框架，用于生成，描述，调用可视化 RESTful 风格的 Web 服务。

在启用后端服务后，可以通过以下地址来进行文档查阅与在线调试：

- dev-service-user

    http://user.imoocnews.com:8003/doc.html
    
### 项目亮点

#### 1. 延迟双删实现 MySQL 和 Redis 的数据一致性

为了减轻数据库的压力，我会将更新频率较低，查询频率较高的接口的数据缓存到 Redis 中：
- 对于查询接口，我们会让请求先到 Redis，如果命中则返回结果；如果缓存失效，则从数据库查询，再写入到缓存中
- 对于更新接口，我们使用**缓存双删策略**，保证数据库与 Redis 缓存数据的一致性

为了保证数据库与缓存的一致性，常用的缓存更新策略有：

- 先更新数据库，再更新缓存
- 先删除缓存，再更新数据库
- 先更新数据库，再删除缓存

方案一：先更新数据库，再更新缓存

假设有请求 A 和请求 B 同时执行更新操作，那么有可能会出现：

1. 线程 A 更新了数据库
2. 线程 B 更新了数据库
3. 线程 B 更新了缓存
4. 线程 A 更新了缓存

线程 A 更新缓存应该比线程 B 更新缓存要早才对，但因为网络等原因，就会导致这种脏数据。

方案二：先删除缓存，再更新数据库

假设有请求 A 进行更新操作，另一个请求 B 进行查询操作，那么有可能会出现：

1. 线程 A 进行更新操作前，先删除了缓存
2. 线程 B 查询发现缓存不存在
3. 线程 B 查询数据库的旧值
4. 线程 B 将旧值写入到缓存
5. 线程 A 执行更新，将新值写入到数据库

这样便出现了数据库与缓存不一致的情况。

方案三：先更新数据库，再删除缓存

大家可以先读一下这篇文章[《Cache-Aside patter》](https://docs.microsoft.com/en-us/azure/architecture/patterns/cache-aside)

我们来看一下该方案导致的并发问题，请求 A 进行查询操作，请求 B 执行更新操作：

1. 缓存刚好失效
2. 线程 A 查询数据库，得到一个旧值
3. 线程 B 将新值写入到数据库
4. 线程 B 删除缓存
5. 线程 A 将旧值写入到缓存

这样就会导致出现脏数据。

该方案虽然存在并发问题，但是出现上述情况的概率是极低的，也有一些企业在使用这种方案。

回过头，我们来看一下本项目使用的**延迟双删**策略：

延迟双删的流程为：

1. 先删除缓存
2. 再写数据库
3. 休眠一段时间，再淘汰缓存

回顾一下方案二，即先删除缓存，再更新数据库可能造成数据库与缓存不一致的情况：

假设有请求 A 进行更新操作，另一个请求 B 进行查询操作，如果使用缓存双删策略：

1. 线程 A 进行更新操作前，先删除了缓存
2. 线程 B 查询发现缓存不存在
3. 线程 B 查询数据库的旧值
4. 线程 B 将旧值写入到缓存
5. 线程 A 执行更新，将新值写入到数据库，执行 `Thread.sleep(t)`
6. **线程 A 苏醒，再次将缓存中的值删除**

这样就有效避免了数据库与缓存不一致的情况。

缓存双删的优点是大大降低了数据库与缓存不一致的概率的发生，缺点为一定程度上降低了吞吐量。

**拓展:分布式系统中的 CAP 理论**

CAP 理论：

- C(Consistency) 一致性
- A(Availability) 可用性
- P(Partition tolerance) 分区容错性

一个分布式系统最多只能同时满足这三项中的两项。

#### Bug Report

- 2021-12-27
    
    后端传递给前端的日期少了一天，譬如后端传递的日期为：`1900-01-01`，而前端显示的日期则为 `1899-12-31`；解决这个 bug 需要注意的问题有两点。
    第一个是在配置文件中需要设置：
    ```yaml
    spring:
        jackson:
            date-format: yyyy-MM-dd HH:mm:ss
            time-zone: GMT+8
    ```
    第二个是需要注意 MySQL 数据库中的时区，我使用了 Docker 启动数据库，可以在启动 Docker 时，通过 -e 属性指定 time zone：
    ```bash
    -e TZ=Asia/Shanghai
    ```
## imooc-news

### 项目使用技术栈

- Spring Cloud(Hoxton.SR3)
    - xxx
    - xxx
- Docker
- Flyway
- Swagger2
- 腾讯云短信服务
- 阿里云 OSS 存储
- ~~阿里云内容安全(服务未开通)~~
- 人脸识别登录
- MongoDB 

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
- 我们可以通过修改 config 目录下的 server.xml 来自定义 tomcat 访问的端口号；浏览器访问 `localhost:[port]`，如果未修改，默认端口号为 `8080`,我设置的端口号为 `9090`
- 页面地址：
    - 作家中心：`http://writer.imoocnews.com:9090/imooc-news/portal/index.html`
    - 管理员登录页面：`http://admin.imoocnews.com:9090/imooc-news/admin/login.html`
    
#### 3. 使用 SwitchHosts 绑定虚拟域名

- 下载 SwitchHosts：[下载地址](https://swh.app/zh/)
- 通过本机内网 ip 地址绑定虚拟域名；MacOS 可以通过命令 `ifconfig` 查看内网 ip 地址
- 配置示例：
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
    cd mybatis-generator 
    mvn flyway:clean flyway:migrate
    ```
#### 5. 启动 MongoDB 

- 下载 MongoDB，本项目中，我使用的版本为 5.0.5，下载地址：`https://www.mongodb.com/try/download/enterprise`
- 在 MongoDB 的根目录下，创建目录与文件，位置关系如下：
    ```text
    ├── data
    │   ├── db
    │   └── logs
    │       └── mongodb.log
    ```
- 在 MongoDB 的根目录下，创建配置文件：`mongodb.conf`
    配置文件内容如下：
    ```text
    # 端口号
    port=27017
    # 数据库文件位置
    dbpath=/Users/macbook/Downloads/mongodb-macos-x86_64-enterprise-5.0.5/data/db
    # 日志文件位置
    logpath=/Users/macbook/Downloads/mongodb-macos-x86_64-enterprise-5.0.5/data/logs/mongodb.log
    # 以追加日志的形式记录日志
    logappend=true
    # 过滤掉无用的日志信息，若需要调试请设置为false
    quiet=true
    # 以后台方式运行
    fork=true
    # 最大同时连接数
    maxConns=100
    # 不启用验证权限
    noauth=true
    # 启用用户账号权限
    # auth=true
    # 开启日志，默认为 true
    journal=true
    # 提供外网访问，不对ip 进行绑定，原理同 Redis 的 bindip
    bind_ip=0.0.0.0
    ```
- 安装 `net-snmp`
    - Mac OS 使用命令：`brew install net-snmp`
    - Linux 使用命令：`yum install net-snmp` 
- 以加载配置文件的形式启动 MongoDB：
    ```bash
    mongod -f mongodb.conf
    ```  
    当终端启动成功后，会显示如下的字样：
    ```text
    ➜  mongodb-macos-x86_64-enterprise-5.0.5 mongod -f mongodb.conf
    about to fork child process, waiting until server is ready for connections.
    forked process: 81678
    child process started successfully, parent exiting
    ```
- 创建数据库
    进入到 MongoDB 客户端，使用命令：
    ```bash
    mongo
    ```  
    创建数据库，使用命令：
    ```bash
    use imooc-news
    ```
- 停止 MongoDB 服务
    使用命令：
    ```bash
    kill -2 81678
    ```
    或
    ```bash
    kill -9 81678
    ```
    即可

#### 6. 启动 Redis

你可以选择使用 Docker 容器，也可以在本地启动 Redis 服务，本项目使用的方式为本地启动 Redis，使用 Redis 版本为 6.0.9。

进入到 Redis 目录下，执行命令：
```bash
redis-server
```
开启 Redis 服务。

#### 7. 关于阿里云与腾讯云服务

在本项目中，用到了腾讯云短信服务与阿里云 OSS 存储，实现这两项服务对应的工具类为 imooc-news-dev-common 模块下的 `/src/main/com/imooc/utils` 包中的 `SMSUtils` 与 `FileUploadUtils` ，需要用户自己去开通服务并进行配置。

#### 8. Swagger2 在线调试地址

Swagger2 是一个可以根据代码自动生成 API 文档的框架，用于生成，描述，调用可视化 RESTful 风格的 Web 服务。

在启用后端服务后，可以通过以下地址来进行文档查阅与在线调试：

- dev-service-user

    http://user.imoocnews.com:8003/doc.html
    
- dev-service-files

    http://files.imoocnews.com:8004/doc.html

- dev-service-admin

    http://admin.imoocnews.com:8005/doc.html

#### 9. Chrome 开启视频调试模式

本项目中，管理员登录涉及到人脸登录识别，需要对 Chrome 浏览器开始视频调试模式。

由于我们配置的域名地址并不是 `https`，所以需要对 Chrome 浏览器进行设置。

在 Chrome 浏览器中，输入网址：
```text
chrome://flags/#unsafely-treat-insecure-origin-as-secure
```
在 `Insecure origins treated as secure` 下方对输入框中输入：
```text
http://admin.imoocnews.com:9090,http://admin.imoocnews.com
```
这样便可以启用摄像头。
        
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

#### 2. 人脸识别登录

人脸入库流程：

- 勾选'人脸登录'
- 出现人脸捕捉画面
- 点击'获取人脸'
- 提交信息
    - 如果提交中有人脸信息，则进行人脸入库（GridFS），并保存 admin 信息
    - 如果提交中无人脸信息，则直接保存 admin 信息

人脸登录流程：

- 使用人脸识别登录
- 出现人脸捕捉画面
- 点击登录获取人脸
- 对人脸进行校验
    - 如果校验成功则登录成功
    - 如果校验失败则返回登录失败
    

### Bug Report

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
- 2021-12-30 

     创建文件服务模块时，运行启动类 FilesApplication 报错；报错内容为：Failed to configure a DataSource  
     原因在于：项目中，我们已经引入了数据源驱动的依赖（common 模块下），files 模块继承了 common 模块，但是 files 模块并没有使用数据源
     我们可以在 `@SpringBootApplication` 注解中使用 `exclude`参数排除掉 `XXXAutoConfiguration` 自动装配的类，只需要使用 exclude 将 `DataSourceAutoConfiguration.class` 这个数据源自动装配类排除即可。
 
 - 2022-01-09
    
    完成 `admin` 模块的用户名密码登录功能后，运行 `AdminApplication` 报错；报错内容为：Okhttp3 java.lang.NoSuchFieldError：Companion
    目前怀疑是项目引入的 okhttp3 与其他依赖中依赖的 okhttp3 有冲突，取消了项目 okhttp3 的依赖，运行正常。
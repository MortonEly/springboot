这是纯手写的springboot后端代码，没有使用mybatis-plus代码生成器，用作备份文件-2023年10月20日 青哥哥10p内容
首先，框架有 
  Spring Boot（org.springframework.boot:spring-boot-starter-web和org.springframework.boot:spring-boot-starter-test）：用于构建基于Spring Boot的Web应用程序，并提供测试支持。
  MyBatis和MyBatis-Spring-Boot-Starter（org.mybatis.spring.boot:mybatis-spring-boot-starter和org.mybatis.spring.boot:mybatis-spring-boot-starter-test）：用于集成MyBatis和Spring Boot，简化配置和集成过程。
  MySQL Connector/J（com.mysql:mysql-connector-j）：提供MySQL数据库的Java驱动程序，用于与MySQL数据库进行交互。
  Lombok（org.projectlombok:lombok）：一个Java库，通过注解来简化开发人员的实体类和DTO等的编写，减少冗余代码。
  MyBatis-Plus（com.baomidou:mybatis-plus-boot-starter、com.baomidou:mybatis-plus、com.baomidou:mybatis-plus-generator、com.baomidou:mybatis-plus-extension、com.baomidou:mybatis-plus-core）：基于MyBatis的增强工具，提供了更方便的ORM操作和高级查询功能。
  Druid（com.alibaba:druid-spring-boot-starter和com.alibaba:druid）：用于提供数据库连接池功能，管理数据库连接和性能监控。
  javax.persistence:persistence-api：Java持久化API，定义了一组用于对象持久化的接口和类。
  springfox-boot-starter（io.springfox:springfox-boot-starter）：用于生成和文档化RESTful API的框架。
  Velocity模板引擎（org.apache.velocity:velocity-engine-core）：用于生成代码或其他文本输出的模板引擎
  Knife4j-Spring-Boot-Starter（com.github.xiaoymin:knife4j-spring-boot-starter）：用于生成和管理API文档的工具。
  FreeMarker模板引擎（org.freemarker:freemarker）：另一个用于生成代码或其他文本输出的模板引擎。

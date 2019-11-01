# SpringBoot2 JPA Security JWT RESTful APIs Application

## 1. 架构
### 1.1 依赖
- springboot 2.16
- springboot JPA
- springBoot Security
- spirnbBoot MongoDB

### 1.2 数据库

- MySQL(没有使用JSON字段特性) (springboo2app 会自动更具entity 自动创建数据库)
- MongoDB(引入 未使用到业务逻辑)

### 1.3 功能(初学者可以借鉴的共)

1. API 级别的权限控制
2. JPA 分页接口封装
3. JPA count 字段(sql: left join group count) 获取
4. 获取自定义@Menu注解做权限控制
5. JPA repository @Query 写SQL语句
6. SpringBoot2 登陆及JWT认证



## 2.个人编程习惯和风格
1. 不喜欢太深的代码目录结构(2~3层目录结构最佳),当然有更好的目录结构,我也是乐意采纳的
2. 喜欢代码简洁命令,不喜欢过多的设计模式和过多的约定规则(不是很喜欢 interface->impl 这种结构,90%的项目都是小项目)
3. 讨厌那种小项目的代码都写不好,甚至连代码都没有写过,在一个常规的API项目BB设计模式,高可用,解耦,可扩展的嘴炮架构师和嘴炮开发者

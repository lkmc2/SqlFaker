# SqlFaker
#### 轻量级、易拓展的数据库智能填充开源库

## 开源库特性

+ 支持主流的MySQL、Oracle、SQL Server、H2等数据库
+ 支持8种常见数据库字段类型的智能填充，并支持自定义拓展
+ 支持事务
+ 支持JDK1.6

## 使用示范

``` java
// 创建数据库连接
DBTools.url("jdbc:mysql://localhost:3306/facker")
        .username("root")
        .password("123456")
        .connect();

// 给user表的四个字段填充5条数据
Faker.tableName("user")
      .param("name", DataType.USERNAME)
      .param("age", DataType.AGE)
      .param("sex", DataType.SEX)
      .param("birthday", DataType.TIME)
      .insertCount(5)
      .execute();
```

上述代码将生成如下SQL语句，并在数据库中执行：

```sql
insert into user(name,age,sex,address,birthday) values('武叹霜','21','0','山西省晋城市泽州县庆达路463号','2018-02-24 10:56:37')
insert into user(name,age,sex,address,birthday) values('顾什可','50','1','广西壮族自治区柳州市融水苗族自治县德堡路419号','2018-04-09 08:10:22')
insert into user(name,age,sex,address,birthday) values('蔡静随','46','0','河南省郑州市巩义市广延路240号','2018-06-11 23:02:19')
insert into user(name,age,sex,address,birthday) values('韦丸赤','27','0','河南省焦作市博爱县浦润路148号','2018-02-22 15:52:50')
insert into user(name,age,sex,address,birthday) values('任徐','54','1','河南省新乡市延津县汉源路14号','2018-07-07 03:48:51')
```

## 依赖添加

本开源库依赖commons-dbcp2、commons-dbutils，以及所需的数据库驱动包。

``` xml
<!--dbcp2 数据库连接池-->
<dependency>
  <groupId>org.apache.commons</groupId>
  <artifactId>commons-dbcp2</artifactId>
  <version>2.0.1</version>
</dependency>

<!--Apache Commons DbUtils-->
<dependency>
  <groupId>commons-dbutils</groupId>
  <artifactId>commons-dbutils</artifactId>
  <version>1.6</version>
</dependency>

<!--MySQL驱动（可换成其他数据库驱动，如：Oracle、SQL Server、H2等dbcp2连接池支持的数据库）-->
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>5.1.46</version>
  <scope>runtime</scope>
</dependency>
```

## 数据库连接

#### 一、连接属性设置

在进行插入数据之前需要设置数据库属性进行连接，如代码所示：

``` java
// 创建数据库连接
DBTools.url("jdbc:mysql://localhost:3306/facker")
        .username("root")
        .password("123456")
        .driverClassName("com.mysql.jdbc.Driver")
        .connect();
```

可设置的属性如下表：

|       属性名       |   说明    |                   默认值                    |
| :-------------: | :-----: | :--------------------------------------: |
|       url       | 数据库连接地址 | jdbc:mysql://localhost:3306/数据库名?useSSL=true&characterEncoding=utf8 |
|    username     | 数据库用户名  |                   root                   |
|       url       |  数据库密码  |                  123456                  |
| driverClassName | 数据库驱动名  |          com.mysql.jdbc.Driver           |
#### 二、特殊情况
1. 当数据库属性值等于默认值时，可省略该属性的设置(如用户名等于root、驱动名为com.mysql.jdbc.Driver时，可只设置url和密码)：

``` java
// 创建数据库连接
DBTools.url("jdbc:mysql://localhost:3306/facker")
        .password("OoOo00Oo00OoOo")
        .connect();
```
2. 当数据库属性值都等于默认值时，可只设置数据库名（此时的url自动拼接为：jdbc:mysql://localhost:3306/facker?useSSL=true&characterEncoding=utf8）：

``` java
DBTools.dbName("facker").connect();
```

## 数据插入

### 一、属性介绍

``` java
// 给user表的四个字段填充5条数据
Faker.tableName("user")
      .param("name", DataType.USERNAME)
      .param("age", DataType.AGE)
      .param("sex", DataType.SEX)
      .param("birthday", DataType.TIME)
      .insertCount(5)
      .execute();

// 给user表的两个字段生成5条SQL，并显示在控制台
Faker.tableName("user")
      .param("name", DataType.USERNAME)
      .param("age", DataType.AGE)
      .insertCount(5)
      .onlyShowSql();

// 不执行任何操作，不生成SQL，不显示在控制台
Faker.tableName("user")
      .param("name", DataType.USERNAME)
      .param("age", DataType.AGE)
      .insertCount(5)
      .ignored();
```
可设置的属性如下表：
|     属性名     |          说明           |              参数值类型               |
| :---------: | :-------------------: | :------------------------------: |
|  tableName  |         数据库表名         |              String              |
|    param    |        数据库字段名         | String，DataType枚举值或实现了Random接口的类 |
| insertCount |        插入数据条数         |             Integer              |
|   execute   | 生成SQL，显示在控制台，并在数据库中执行 |                无                 |
| onlyShowSql |     生成SQL，并显示在控制台     |                无                 |
|   ignored   |        不执行任何操作        |                无                 |



本开源库一共支持三种插入数据的方式。

#### 1. 使用DataType指定数据类型


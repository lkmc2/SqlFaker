# SqlFaker
#### 轻量级、易拓展的数据库智能填充Java开源库
[![Maven Central](https://img.shields.io/badge/maven--central-v1.0.1-blue.svg)](https://search.maven.org/search?q=g:%22com.github.lkmc2%22%20AND%20a:%22sql-faker%22)[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## 开源库特性

+ 支持主流的MySQL、Oracle、SQL Server、H2等数据库
+ 支持8种常见数据库字段类型的智能填充，并支持自定义拓展
+ 支持一次性插入百万级别的数据
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
insert into user(name,age,sex,address,birthday) values('武叹霜',21,'山西省晋城市泽州县庆达路463号','2018-02-24 10:56:37')
insert into user(name,age,sex,address,birthday) values('顾什可',50,'广西壮族自治区柳州市融水苗族自治县德堡路419号','2018-04-09 08:10:22')
insert into user(name,age,sex,address,birthday) values('蔡静随',46,'河南省郑州市巩义市广延路240号','2018-06-11 23:02:19')
insert into user(name,age,sex,address,birthday) values('韦丸赤',27,'河南省焦作市博爱县浦润路148号','2018-02-22 15:52:50')
insert into user(name,age,sex,address,birthday) values('任徐',54,'河南省新乡市延津县汉源路14号','2018-07-07 03:48:51')
```



## 依赖添加

本开源库已包含commons-dbcp2(2.0.1)、commons-dbutils(1.6)、junit(4.1.2)以及mysql-connector-java(5.1.46)的依赖。

``` xml
<!-- SqlFaker 数据库数据生成器 -->
<dependency>
  <groupId>com.github.lkmc2</groupId>
  <artifactId>sql-faker</artifactId>
  <version>1.0.1</version>
</dependency>
```

**注意**：默认使用MySQL数据库，如需更换成Oracle、SQL Server、H2等commons-dbcp2连接池支持的数据库，可另行添加依赖，并在DBTools的driverClassName(数据库驱动名)方法中指定数据库驱动名。



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

**注意** ：数据库连接只需要设置一次，之后可以多次调用Faker进行插入数据操作。



## 数据插入

### 一、属性介绍
可设置的属性如下表：

|         属性名          |          说明           |
| :------------------: | :-------------------: |
|   tableName(数据库表名)   |        设置数据库表名        |
| param(字段名, 数据生成器类型①) | 设置数据库字段名，以及对应的数据生成器类型 |
|  insertCount(插入条数)   |       设置插入数据条数        |
|      execute( )      | 生成SQL，显示在控制台，并在数据库中执行 |
|    onlyShowSql( )    |     生成SQL，并显示在控制台     |
|      ignored( )      |        不执行任何操作        |

注意：① 数据生成器类型，必须是DataType枚举值，或实现了Random接口的类。

使用示例：
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



### 二、插入数据的方式

本开源库一共支持三种插入数据的方式，可以混合使用。

#### 1. 使用DataType指定数据类型

DataType一共支持8种枚举类型，如下表所示：

|   属性名    |  说明  |         类型         |         示例值         |
| :------: | :--: | :----------------: | :-----------------: |
|    ID    | 用户ID |   19位的数字型UUID字符串   | 1049120504188764160 |
| USERNAME | 用户名  |    长度为2到4个字的中文名    |         武叹霜         |
|   TIME   |  时间  | 一年前到现在的时间范围内任意一个时刻 | 2018-03-01 12:41:00 |
|  PHONE   | 手机号  |       11位手机号       |     13192668109     |
| ADDRESS  |  地址  |    国内地址，详细到门牌号     |  四川省绵阳市盐亭县北利路738号   |
|   AGE    |  年龄  |     18到60岁的数字      |         19          |
|   SEX    |  性别  |     字符，0：男，1：女     |         '1'         |
|  EMAIL   |  邮箱  |      常见邮箱字符串       |  Alex705@gmail.com  |

使用示例：

```java
// 给user表的8个字段填充1条数据
Faker.tableName("user")
      .param("id", DataType.ID)
      .param("name", DataType.USERNAME)
      .param("birthday", DataType.TIME)
      .param("phone", DataType.PHONE)
      .param("address", DataType.ADDRESS)
      .param("age", DataType.AGE)
      .param("sex", DataType.SEX)
      .param("email", DataType.EMAIL)
      .insertCount(1)
      .execute();
```
对应生成的SQL语句如下：

```sql
insert into 
user(
  id, name, birthday,
  phone, address, age,
  sex, email
) 
values(
  '1049120504188764160', '武叹霜', '2018-03-01 12:41:00',
  '13192668109', '四川省绵阳市盐亭县北利路73号', 19,
   '1', 'Alex705@gmail.com'
)
```



#### 2. 使用 Values.of()系列方法生成取值范围

Values类共有以下8种生成取值范围方法，如下表：


|               方法名                |                  取值范围                  |         示例值         |
| :------------------------------: | :------------------------------------: | :-----------------: |
|         Values.of(可变长数组)         |             从可变长数组中任意抽取一个值             |  "优品", "良品", "次品"   |
|    Values.ofIntRange(起始值,结束值)    |          在[起始值, 结束值]的范围内取一个整数          |         33          |
|   Values.ofLongRange(起始值,结束值)    |         在[起始值, 结束值)的范围内取一个长整型数         |     777777777L      |
|   Values.ofFloatRange(起始值,结束值)   |  在[起始值, 结束值]的范围内取一个单精度浮点数，默认精确到小数点后2位  |       22.22f        |
| Values.ofFloatRange(起始值,结束值,精度)  | 在[起始值, 结束值]的范围内取一个单精度浮点数，精度根据参数设置，最多6位 |     123.333333f     |
|  Values.ofDoubleRange(起始值,结束值)   |  在[起始值, 结束值]的范围内取一个双精度浮点数，默认精确到小数点后2位  |       788.31d       |
| Values.ofDoubleRange(起始值,结束值,精度) | 在[起始值, 结束值]的范围内取一个双精度浮点数，精度根据参数设置，最多6位 |     1820.4231d      |
|  Values.ofTimeRange(开始时间，结束时间)   |      在[开始时间, 结束时间]的范围内取一个时间，精确到秒       | 2018-03-14 13:21:11 |

另外，Times类中还有用于设定时间的两个方法：

|          方法名          |     说明      |
| :-------------------: | :---------: |
|    Times.of(年,月,日)    | 用于生成时间，精确到日 |
| Times.of(年,月,日,时,分,秒) | 用于生成时间，精确到秒 |



使用示例：



```java
// 给product表的9个字段填充1条数据
Faker.tableName("product")
      .param("type", Values.of("优品", "良品", "次品"))
      .param("person_count", Values.ofIntRange(20, 50))
      .param("total_count", Values.ofLongRange(555555555L, 888888888L))
      .param("enter_price", Values.ofFloatRange(12.33f, 34.57f))
      .param("outcome_price", Values.ofFloatRange(100.004132f, 240.281424f, 6))
      .param("speed", Values.ofDoubleRange(750.34d, 800.27d))
      .param("salary", Values.ofDoubleRange(1980.3415d, 2700.2315d, 4))
      .param("firstTime", Values.ofTimeRange(Times.of(2018,3,22), Times.of(2018,10,22)))
      .param("secondTime", 
             Values.ofTimeRange(
                Times.of(2018,3,22,11,23,24), 
                Times.of(2018,10,22,22,15,17)
             )
       )
      .insertCount(1)
      .execute();
```

对应生成的SQL语句如下：

```sql
insert into 
product(
  type, person_count, total_count,
  enter_price, outcome_price, speed,
  salary, firstTime, secondTime
) 
values(
  '良品', 33, 777777777,
  22.22, 123.333333, 788.31,
  1820.4231, '2018-03-14 00:00:00', '2018-03-14 13:21:11'
)
```


#### 3. 实现RandomData接口，提供可随机生成的返回值

RandomData接口的代码如下：

```java
public interface RandomData<T> {
    T next();
}
```

实现该接口，并重写next( )方法提供一个返回值，该返回值就是数据库字段对应插入的值。

使用示例：

1. 创建一个自定义类EnglishNameRandom，实现RandomData<T>（泛型T可以是任意类型）接口，并提供一个随机生成的返回值。

``` java
// 英文名数据生成器
public class EnglishNameRandom implements RandomData<String> {
    // 候选值数组，从该数组中随机抽一个作为返回值
    private static final String[] names = {"Kim Lily", "Andy Wang", "July Six"};
    
    @Override
    public String next() {
        // 从数组中随机选取一个值
        return RandomUtils.selectOneInArray(names);
    }
}
```

2. 在Faker中给字段指定使用EnglishNameRandom.class类型的生成器。

```java
// 指定name字段使用EnglishNameRandom类进行随机值的生成
Faker.tableName("user")
       .param("name", EnglishNameRandom.class)
       .param("age", Values.ofIntRange(20, 50))
       .param("address", DataType.ADDRESS)
       .insertCount(5)
       .execute();
```

对应生成的SQL语句如下：
```sql
insert into user(name, age, address) 
values('Andy Wang', 23, '四川省绵阳市盐亭县北利路73号')
```



## 使用Faker表创建器快速生成Java文件

注意：目前仅针对mysql数据库设计了Faker表创建器。



###使用示范

如test数据库中含有如下两张表。

```sql
create table user
(
  id   int          null,
  name varchar(120) null,
  age  int          null
) comment '用户表';

create table product
(
  id         varchar(32) not null primary key,
  name       varchar(32) null,
  price      int         null,
  tenant_id  varchar(32) null,
  created_by varchar(32) null,
  updated_by varchar(32) null,
  created_at date        null,
  updated_at date        null,
  dr         int(1)      null
) comment '产品表';
```



使用如下代码即可使用Faker表创建器快速为test数据库中的所有表生成带有Faker结构的java文件。

```java
// 方式1：简单设置数据库名，使用默认url、用户名和密码，并创建Faker表结构
MysqlFakerCreator.dbName("test").build();

// 方式2：完整设置数据库信息，并创建Faker表结构
MysqlFakerCreator.url("jdbc:mysql://localhost:3306/test")
                .username("root")
                .password("123456")
                .build();
```



执行上述代码，生成的java文件内容如下：

``` java
package com.lin.creator;

import com.lin.datatype.DataType;
import com.lin.faker.Faker;
import com.lin.utils.DBTools;
import com.lin.value.Times;
import com.lin.value.Values;

/**
* Faker生成的表结构
*/
public class CreateFakerTable {

   public static void main(String[] args) {
      // 创建数据库连接
      DBTools.url("jdbc:mysql://localhost:3306/test")
            .username("root")
            .password("123456")
            .driverClassName("com.mysql.jdbc.Driver")
            .connect();

      // 用户表
      Faker.tableName("user")
            .param("id", Values.ofIntRange(1, 18))
            .param("name", Values.of("example1", "example2", "example3"))
            .param("age", Values.ofIntRange(1, 18))
            .insertCount(5)
            .onlyShowSql();
      
      // 产品表
      Faker.tableName("product")
            .param("id", DataType.ID)
            .param("name", Values.of("example1", "example2", "example3"))
            .param("price", Values.ofIntRange(1, 18))
            .param("tenant_id", Values.of("example1", "example2", "example3"))
            .param("created_by", Values.of("example1", "example2", "example3"))
            .param("updated_by", Values.of("example1", "example2", "example3"))
            .param("created_at", Values.ofTimeRange(Times.of(2019, 1, 1), Times.of(2019, 5, 1)))
            .param("updated_at", Values.ofTimeRange(Times.of(2019, 1, 1), Times.of(2019, 5, 1)))
            .param("dr", Values.ofIntRange(1, 18))
            .insertCount(5)
            .onlyShowSql();
   }

}
```

可以在此文件的基础上对进行开发。



PS：如果有任何建议，可以在Issues中提出，如添加DataType的默认类型等。



## License

The SqlFaker is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).

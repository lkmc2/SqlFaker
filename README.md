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



|         属性名          |          说明           |
| :------------------: | :-------------------: |
|   tableName(数据库表名)   |        设置数据库表名        |
| param(字段名, 数据生成器类型①) | 设置数据库字段名，以及对应的数据生成器类型 |
|  insertCount(插入条数)   |       设置插入数据条数        |
|      execute( )      | 生成SQL，显示在控制台，并在数据库中执行 |
|    onlyShowSql( )    |     生成SQL，并显示在控制台     |
|      ignored( )      |        不执行任何操作        |

注意：① 数据生成器类型，必须是DataType枚举值，或实现了Random接口的类。

### 二、插入数据的方式

本开源库一共支持三种插入数据的方式。

#### 1. 使用DataType指定数据类型

DataType一共支持8种枚举类型，如下表所示：

|   属性名    |  说明  |         类型         |         示例值         |
| :------: | :--: | :----------------: | :-----------------: |
|    ID    | 用户ID |   19位的数字型UUID字符串   | 1049120504188764160 |
| USERNAME | 用户名  |    长度为1到4个字的中文名    |         武叹霜         |
|   TIME   |  时间  | 一年前到现在的时间范围内任意一个时刻 | 2018-03-01 12:41:00 |
|  PHONE   | 手机号  |       11位手机号       |     13192668109     |
| ADDRESS  |  地址  |    国内地址，详细到门牌号     |  四川省绵阳市盐亭县北利路738号   |
|   AGE    |  年龄  |     18到60岁的数字      |         19          |
|   SEX    |  性别  |     字符，0：男，1：女     |         '1'         |
|  EMAIL   |  邮箱  |      常见邮箱字符串       |  Alex705@gmail.com  |

使用示例：

```java
// 给user表的8个字段填充5条数据
Faker.tableName("user")
  	  .param("id", DataType.ID)
      .param("name", DataType.USERNAME)
      .param("birthday", DataType.TIME)
      .param("phone", DataType.PHONE)
      .param("address", DataType.ADDRESS)
      .param("age", DataType.AGE)
      .param("sex", DataType.SEX)
  	  .param("email", DataType.EMAIL)
      .insertCount(5)
      .execute();
```
#### 2. 使用 Values.of()系列方法生成取值范围

Values类共有以下8种生成取值范围方法，如下表：


|               方法名               |               取值范围                |         示例值         |
| :-----------------------------: | :-------------------------------: | :-----------------: |
|        Values.of(可变长数组)         |            从数组中任意抽取一个值            |  "优品", "良品", "次品"   |
|   Values.ofIntRange(起始值,结束值)    |        在[起始值, 结束值]的范围内取一个值        |         33          |
|   Values.ofLongRange(起始值,结束值)   |        在[起始值, 结束值)的范围内取一个值        |     777777777L      |
|  Values.ofFloatRange(起始值,结束值)   |  在[起始值, 结束值]的范围内取一个值，默认精确到小数点后2位  |       22.22f        |
| Values.ofFloatRange(起始值,结束值,精度) | 在[起始值, 结束值]的范围内取一个值，精度根据参数设置，最多6位 |     123.333333f     |
|  Values.ofDoubleRange(起始值,结束值)  |  在[起始值, 结束值]的范围内取一个值，默认精确到小数点后2位  |       788.31d       |
| Values.ofFloatRange(起始值,结束值,精度) | 在[起始值, 结束值]的范围内取一个值，精度根据参数设置，最多6位 |     1820.4231d      |
|  Values.ofTimeRange(开始时间，结束时间]  |    在[开始时间, 结束时间]的范围内取一个时间，精确到秒    | 2018-03-14 13:21:11 |

另外，Times类中还有用于设定时间的两个方法：

|          方法名          |     说明      |
| :-------------------: | :---------: |
|    Times.of(年,月,日)    | 用于生成时间，精确到日 |
| Times.of(年,月,日,时,分,秒) | 用于生成时间，精确到秒 |



使用示例：



```java
// 给product表的8个字段填充5条数据
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
      .insertCount(5)
      .execute();
```

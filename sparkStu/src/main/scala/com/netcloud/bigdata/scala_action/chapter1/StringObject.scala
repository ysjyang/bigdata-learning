package com.netcloud.bigdata.scala_action.chapter1

import scala.util.matching.Regex

/**
  * 字符串的处理
  *
  * @author yangshaojun
  *         2018/12/15 9:33
  * @version 1.0
  */
object StringObject {
  def main(args: Array[String]): Unit = {
    //1、打印字符串的类型名称
    println("Hello World!".getClass.getName)
    //2、Scala的String类就是Java的String类，所以Scala可以直接调用Java里String的所有方法
    val s = "Hello,World!" //新建字符串变量
    println(s.length) //获取字符串长度
    val str = "Hello" + "world";
    println(str) //字符串拼接
    "hello".foreach(println) //字符串中字符的遍历
    for (i <- "hello") {
      println(i)
    }
    val result = "hello".filter(_ != 'l');
    println(result)

    val res1 = "scala".drop(2).take(2).capitalize;
    println(res1) // capitalize 返回字符串的第一个字符是大写


    /**
      * 比较字符串的相等性  == 或者 equals() 或者 eq()
      * 这里的 == 和 equals() 比较的内容信息 不像java中的equals比较的内存地址
      * 如果 scala中比较内存的地址 可以使用ne
      *
      */

    val s1 = "Hello"
    val s2 = "Hello"
    val s3 = "H" + "ello"
    println(s1 == s2);
    println(s2 == s3)
    println("s1.equals(s3)=" + s1.equals(s3))
    println("s1.eq(s3)=" + s1.ne(s3))
    val s4 = null
    println(s1 == s4) //使用== 方法好处就是即使比较的字符串为null 也不会抛出异常信息，但是在为null的字符串上调用其他方法就会出现异常
    //s4.toUpperCase
    val s5 = "hello"
    val res3 = s5.equalsIgnoreCase(s1);
    println(res3)

    /**
      * 创建多个字符串 """  """
      */
    val fool =
      """This
        |is
        |a
        |mmultiline
        |String""".stripMargin
    println("multiline=" + fool)

    /**
      * 分割字符串 split() 返回数组类型
      *
      */

    val res6 = "hello world".split(" ")
    res6.foreach(println) //hello world

    /**
      * 字符串中的变量代换
      * 1）字符串字面量使用表达式 {}
      * 2）s 是一个方法
      * 3）字符串插值 f(printf) 通过printf格式化内部字符串
      * 4）raw 插入符 不会对字符串中对的转义字符进行转义
      * Note：除了内置的 s、f、raw插入符之外，也可以自定义插入符
      * 在scala2.10之前scala并不支持字符串插入符功能 ，但是字符串的format方法可以同样实现上述功能。
      *
      */
    val name = "Fred"
    val age = 33
    val weight = 200.00
    println(s"$name is $age years old,and weights $weight pounds.")
    println(s"Age next year:${age + 1}")

    //打印对象的某个属性字段的时候，使用花括号 否则报错
    case class Student(name: String, score: Int)
    val hannah = Student("hannah", 95)
    println(s"${hannah.name} has a score of ${hannah.score}")
    //println(s"$hannah.name has a score of $hannah.score")

    //字符串插值 f printf
    println(f"$name is $age years old ,and weights $weight%.2f pounds.") // $weight%.2f 保留两位小数
    println(f"$name is $age years old ,and weights $weight%.0f pounds.") // $weight%.0f 不保留小数

    // raw 插入符
    val res7 = s"fool\nbar"
    println(res7) //换行展示

    val res8 = raw"fool\nbar"
    println(res8) //不会对字符串中的转义字符进行转义

    //format 同样实现插入符功能
    println("%s is a good man".format("ysj"))

    /**
      * 处理字符串中的每个字符
      * 遍历字符串中的每个字符，在遍历的时候同时要对字符做些操作
      * 可以使用map、foreach、for循环等方式
      * 需求：创建全部都是大写的字符串
      * 1） map实现
      * 2) for/yield循环实现
      * 3) foreach
      * Note:map 或者 for/yield 这种方式都是将一个集合转转换为另一个新的集合
      * foreach则是典型的将每个元素进行操作但是不返回结果的方法。这对于打印操作很有用。
      */
    val res9 = "hello world".filter(_ != 'l').map(c => c.toUpper)
    val res10 = "hello world".filter(_ != 'l').map(_.toUpper)
    println("res9=" + res9 + " res10=" + res10)
    val res11 = for (c <- "hello world" if c != 'l') yield c.toUpper
    println("res11=" + res11)
    "hello world".foreach(println)

    /**
      * 字符串中的查找模式
      * 判断一个字符串是否符合正则表达式
      * 1)在String对象上调用 .r 方法可以创建一个Regex对象
      * 2）查找是否含有一个匹配 findFirstIn  返回Option[String]类型
      * 3）查找是否完全匹配时用 findAllIn    返回迭代器 可以对结果进行遍历  如果希望返回 Array 在findAllIn后加 toArray方法
      */
    val numPattern = "[0-9]+".r //创建一个Regex对象
    val address = "123 Main Street Suit 101" //创建一个搜索的字符串
    val match1 = numPattern.findFirstIn(address) //findFirstIn方法:查找第一个匹配 返回Option[String] 类型
    println("first match1=" + match1) //some(123)
    val matches = numPattern.findAllIn(address)
    /*
     * findAllIn:查找多个匹配  返回一个迭代器 可以对结果进行遍历
     *           findAllIn没有任何结果；会返回一个空的迭代器，但是可以继续写代码，而不用考虑是否需要判断结果为null的情况。
     *
     */
    println("full match=" + matches)
    val matchesArr = numPattern.findAllIn(address).toArray //如果匹配失败，返回空的数组 其他的方法 toList、toSeq和toVector也可以这样使用
    println(matchesArr)

    /*创建Regex对象最简单的方法就是在一个String上调用 .r 方法。另一个方法就是引入Regex类，创建一个Regex实例 然后用这个实例做相同的事情
     */
    val numPattern2 = new Regex("[0-9]+")

    /**
      * 字符串中的替换
      * 用正则表达式匹配一段字符串然后替换他们
      */
    val address2 = "123 Main Street".replaceAll("[0-9]", "x")
    println(address2)
    val regex1 = "[0-9]".r
    val newAddress = regex1.replaceAllIn("123 Main Street", "x")
    println(newAddress)
    val regex = "H".r
    println("" + regex.replaceFirstIn("Hello World", "J"))

    /**
      * 返回字符串中的一个字符
      * 返回字符串中指定位置的字符
      */
    println("hello".charAt(0)) //java的charAt方法
    println("hello" (0)) //scala的Array符号

    /**
      * String中添加自定义方法
      */

  }

}

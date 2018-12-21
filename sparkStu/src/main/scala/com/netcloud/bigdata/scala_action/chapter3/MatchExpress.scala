package com.netcloud.bigdata.scala_action.chapter3

/**
  * @author yangshaojun
  * 2018/12/21 14:19
  * @version 1.0
  */
/**
  * 1) 像Switch语句一样使用匹配表达式
  * 2) 一条case语句匹配多个条件
  * 3) 将匹配表达式的结果赋值给变量
  * 4) 访问匹配表达式缺省的case值
  * 5) 在匹配表达式中使用模式匹配
  * 6) 在匹配表达式中使用Case类
  * 7) 给case语句添加if表达式(卫语句)
  * 8) 使用匹配表达式替换 isInstanceOf
  * 9) 在匹配表达式中使用List
  */
object MatchExpress {
  def main(args: Array[String]): Unit = {

    /**
      * 1) 像Java的switch语句一样 使用scala的匹配表达式
      * scala中使用match时 推荐使用 @switch注解
      */
    //i 是一个整数
    val i = 6
    i match {
      case 1 => println("One")
      case 2 => println("Two")
      case 3 => println("Three")
      case 4 => println("Four")
      case 5 => println("Five")
      case 6 => println("Six")
      case whoa => println("whoa") // case whoa 作为缺省值    最后打印出 Six
    }
    //匹配表达式中的返回值   case _ 处理缺省值
    val matchresult = i match {
      case 1 => "One"
      case 2 => "Two"
      case 3 => "Three"
      case 4 => "Four"
      case 5 => "Five"
      case 6 => "Six"
      case 7 => "Seven"
      case _ => "Invalid value"
    }
    println("matchresult=" + matchresult)
    //    import scala.annotation.switch
    //    val matchresult2 = (i:@switch) match {
    //      case 1 => "One"
    //      case 2 => "Two"
    //      case 3 => "Three"
    //      case 4 => "Four"
    //      case 5 => "Five"
    //      case 6 => "Six"
    //      case 7 => "Seven"
    //      case _ => "Invalid value"
    //    }
    //    println("matchresult=" + matchresult2)

    /**
      * 2)一条case语句匹配多个条件
      * 将触发相同逻辑的match条件 用 | (管道符)分割放在同一行
      */
    val j = 5
    j match {
      case 1 | 3 | 5 | 7 | 9 => println("odd")
      case 2 | 4 | 6 | 8 | 10 => println("even")
    }
    //对于字符串或者其他的数据类型 使用的语法是一样的
    val cmd = "stop"
    cmd match {
      case "start" | "go" => println("starting")
      case "stop" | "quit" => println("stoping")
      case _ => println("do nothing")
    }

    /**
      * 3) 将匹配表达式的结果赋值给一个变量
      * 将匹配表达式的返回值赋值给一个变量
      */
    val evenorodd = cmd match {
      case "start" | "go" => println("starting")
      case "stop" | "quit" => println("stoping")
      case _ => println("do nothing")
    }

    //通过这种方式创建短的方法或者函数
    def isevenOrOdd(a: Any) = a match {
      case 1 | 3 | 5 | 7 | 9 => "odd"
      case 2 | 4 | 6 | 8 | 10 => "even"
    }

    println(isevenOrOdd(9))

    /**
      * 4) 匹配表达式 缺省case值
      * 可以使用 case _ 或者 case default 或者 case 变量名称
      */

    /**
      * 5) 在匹配表达式中使用模式匹配
      * 在匹配表达式中使用一种或者多种模式匹配 可以是常量模式、变量模式、构造函数模式、序列模式、元组模式、或者类型模式
      * 下面为每个要匹配的模式定义一个case语句。 下面展示了在匹配表达式中使用多种不同的模式
      */
    def echoWhatYouGaveMe(x: Any) = x match {
      case 0 => "zero"
      case true => "true"
      case "Hello" => "you said hello"
      case Nil => "an empty list"
      case List(0, _, _) => "three-element list with  0 as the  first element"
      case List(1, _*) => "a list begining with 1 ,having any number of elemets"
      case Vector(1, _*) => "a Vector begining with 1 ,having any number of elemets"

      case (a, b) => s"got  $a and $b"
      case (a, b, c) => s"got $a , $b and $c"
    }

    /**
      * 6) 在匹配表达式中使用Case类
      */
    //      def determineType(a:Animal) =a match {
    //        case Dog(moniker) => "Got a dog,name="+moniker //这里的moniker 是变量的名称
    //      }

    /**
      * 7) 给Case语句添加条件表达式
      */
    val flag = 10
    flag match {
      case a if a == 10 => println(10)
    }

    /**
      * 8) 使用匹配表达式替换isInstanceOf
      */
    //    def isPerson(x: Any): Boolean = x match {
    //      case p: Person => true
    //      case _ => false
    //    }false
    /**
      * 9) 在匹配表达式中使用List
      * 实现递归
      */
    val fruits = List("apple", "banana", "orange")
    val fruits2 = "apple" :: "banana" :: "orange"::Nil

    //使用列表单元和Nil元素
    def listToString(list: List[String]): String = list match {
      case s :: rest => s + "," + listToString(rest)
      case Nil => ""
    }

    println(listToString(fruits))


  }

}

package com.netcloud.bigdata.scalalearning.day02

/**
  * @author yangshaojun
  * @version 1.0
  * date 20181211
  */
/*
 * 2) Scala中的if可以作为表达式使用，表达式的返回值就是if或者else的最后一句，可以直接赋值给变量。
 *    例如：val age=20; if (age>18) 1 else 0
 *    可以将if表达式赋值给一个变量，例如：val isAdult=if(age>18) 1 else 0
 *    另一种写法：
 *    val isAdult=-1
 *    if(age>18) isAdult=1 else isAdult=0
 *    通常情况使用第一种方式将结果赋值给变量。
 *
 */
object ConditionAndFor {
  def main(args: Array[String]): Unit = {
    /**
      * 创建一个从1到5的数值序列，包含区间终点5，步长为1
      */
    println(1 to 5) //Range(1, 2, 3, 4, 5)
    println(1.to(5))

    /**
      * 创建一个从1到5的数值序列，不包含区间终点5，步长为1
      */
    println(1 until 5) //Range(1, 2, 3, 4)

    /**
      * 创建一个从1到10的数值序列，包含区间终点10，步长为2
      */
    println(1 to 10 by 2) //Range(1, 3, 5, 7, 9)
    println(1.to(10, 2))

    /**
      * 创建一个Float类型的数值序列，从0.5f到5.9f，步长为0.3f
      */
    println(0.5f to 5.9f by 0.8f) //NumericRange(0.5, 1.3, 2.1, 2.8999999, 3.6999998, 4.5, 5.3)
    /**
      * if else语句
      */
    var isAdult = false
    val age: Int = 18
    if (age >= 18) {
      isAdult = true
    } else {
      isAdult = false
    }
    printf(s"isAdult is %s", isAdult)

    /**
      * scala中的if可以作为表达式使用 表达式的返回值就是if或者else的最后一句，可以直接赋值给某个变量
      */
    isAdult = if (age >= 18) true else false
    /**
      * 使用分号作为语句终止符
      */
    var a, b, c = 0;
    if (a < 10) {
      b = b + 1;
      c = c + 1
    }

    /**
      * while和do... while()也有返回值，只不过其返回值都为Unit。
      */
    val result = while (age >= 19) {
      println("This Person is Adult")
    }

    val result2 = do {
      println("This Person is Adult")
    } while (age >= 19)

    println("result=" + result + ": result2=" + result2)

    /**
      * <-  符号被称为生成器
      */
    for (i <- 1 to 5) {
      println("i=" + i)

    }

    /**
      * 引入scala.until.control.Breaks类。实现break功能
      */
    import scala.util.control.Breaks._
    breakable {
      for (i <- 1 to 5) {
        if (i > 2) break
        println("i=" + i)
      }
    }

    /**
      * 有过滤条件的for循环
      */
    for (i <- 1 to 5 if (i > 3)) {
      println("i=" + i)

    }

    /**
      * 多重for循环
      */
    for (i <- 1 to 5 if (i > 3)) {
      for (j <- 5 to 7 if (j == 6)) {
        println("i=" + i + "," + "j=" + j)
      }
    }
    /**
      * 作为表达式的for循环
      */
    val forResult = for (i <- 1 to 5) yield i
    println(forResult)

  }
}

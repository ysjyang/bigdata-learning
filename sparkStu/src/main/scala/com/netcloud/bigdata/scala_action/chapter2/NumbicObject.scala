package com.netcloud.bigdata.scala_action.chapter2

import org.datanucleus.util.MathUtils
import org.joda.time.DateTime

/**
  * @author yangshaojun
  * 2018/12/16 19:38
  * @version 1.0
  */
object NumbicObject {

  /**
    * 定义一个近似相等的方法进行float的比较
    * @param x
    * @param y
    * @param p
    * @return
    */
  def ~=(x: Double, y: Double, p: Double) = {
    if ((x-y).abs < p ) true else false
  }

  def main(args: Array[String]): Unit = {
    val currnetTIme = DateTime.now() //
    println(currnetTIme)

    /**
      * 类型转换
      * 1）数值字符串转为字符串 to* 使用to* 可能会出现NumberFormatException 原因就是转换的字符串不是数值格式的。
      * 2）数值类型转换 把一个数值类型转为另一个数值类型  如:把Int转Double 不同于java的强制类型，要使用 to*方法
      *
      */
    val res1 = "100".toInt // toDouble、toFloat、toLong、toShort、toByte
    println(res1)
    //println("fool".toInt) NumberFormatException
    val res2=19.45.toInt
    println(res2)
    //避免在类型转换的时候出现错误 在转换前需要进行isValid方法确认是否能进行类型的转换
    val a=100L
    a.isValidByte
    a.isValidShort

    /**
      * 浮点数比较
      * ~=(x:Double,y:Double,precision:Double)
      */
    val res3=0.1+0.2 //0.3000000000004
    val res4=0.1+0.1 //0.2
    println(~=(res3, 0.3, 0.0001))

    /**
      * 生出随机数
      */

    val r=scala.util.Random
    r.nextInt() //生出随机的整数
    r.nextInt(100) //随机生成0-99 不包括99 之间的整数
    r.nextFloat()
    r.nextDouble()
    //生成随机数的时候 设置种子
    r.setSeed(100)

    /**
      * 创建一个数值区间、列表、或者数组
      */
    val res5 = 1 to 10
    println(res5) //Range(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val res6 = (1 to 5).toArray //将序列转为数组
    val res7 = (1 to 5).toList //将序列转为集合


  }

}

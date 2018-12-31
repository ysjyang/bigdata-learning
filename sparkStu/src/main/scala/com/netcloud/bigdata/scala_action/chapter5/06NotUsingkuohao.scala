package com.netcloud.bigdata.scala_action.chapter5

/**
  * 类的方法定义的时候如果没有()那么在调用这个方法的时候 不能加括号
  * @author yangshaojun
  * 2018/12/31 15:21
  * @version 1.0
  */
object NotUsingkuohao {
  def main(args: Array[String]): Unit = {
   val pizza=new Pizza
//    println(pizza.crustSize() 编译报错
    println(pizza.crustSize)


  }
}
class Pizza{
  def crustSize=12
}

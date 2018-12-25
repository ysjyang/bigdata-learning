package com.netcloud.bigdata.scala_action.chapter4

/**
  * 辅助构造函数
  * @author yangshaojun
  * 2018/12/25 21:42
  * @version 1.0
  */

/**
  * 在类内部以this为名的方法定义辅助构造函数。可以定义多个辅助构造函数
  * 但是这些构造函数有不同的参数列表。
  * 每个构造函数必须调用一个之前已经定义好的构造函数(包括主构造函数或者辅助构造函数)。
  *
  * @param crustSize
  * @param crustType
  */
class Pizza(var crustSize: Int, var crustType: String) {

  def this(crustSize: Int) {
    this(crustSize, AssistConstrutor.DEFAULT_CRUST_TYPE)
  }

  def this(crustType: String) {
    this(AssistConstrutor.DEFAULT_CRUST_SIZE, crustType)
  }

  def this() {
    this(AssistConstrutor.DEFAULT_CRUST_SIZE, AssistConstrutor.DEFAULT_CRUST_TYPE)
  }
}

object AssistConstrutor {
  val DEFAULT_CRUST_SIZE = 12
  val DEFAULT_CRUST_TYPE = "THIN"

  def main(args: Array[String]): Unit = {
    //实例化Pizza
    val p1 = new Pizza(DEFAULT_CRUST_SIZE, DEFAULT_CRUST_TYPE) //调用主构造函数
    val p2 = new Pizza(DEFAULT_CRUST_SIZE)
    val p3 = new Pizza(DEFAULT_CRUST_TYPE)
    val p4 = new Pizza()
  }


}

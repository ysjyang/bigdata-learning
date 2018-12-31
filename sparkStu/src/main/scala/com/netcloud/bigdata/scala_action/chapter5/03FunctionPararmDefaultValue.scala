package com.netcloud.bigdata.scala_action.chapter5

/**
  * 方法参数默认值
  *
  * @author yangshaojun
  * 2018/12/31 15:19
  * @version 1.0
  */
object FunctionPararmDefaultValue {
  def main(args: Array[String]): Unit = {
    val c = new connection()
    /*方法的调用方式*/
    c.getConnection()
    c.getConnection(2000)
    c.getConnection(3000, "http")
    c.getConnection(protocol = "http", timeout = 3000)

  }
}
class connection(){

  def getConnection(timeout:Int=50000,protocol:String="https"): Unit ={
    println(s"timeout=$timeout,protocol:$protocol")
  }
}

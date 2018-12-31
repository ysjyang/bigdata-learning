package com.netcloud.bigdata.scala_action.chapter5

/**
  * 方法调用的时候使用参数名
  * @author yangshaojun
  * 2018/12/31 15:20
  * @version 1.0
  */
object UseingParamName {
  def main(args: Array[String]): Unit = {
    val c = new connection2()
    /*方法的调用方式*/
    c.getConnection(protocol = "http", timeout = 3000)

  }
}
class connection2(){

  def getConnection(timeout:Int=50000,protocol:String="https"): Unit ={
    println(s"timeout=$timeout,protocol:$protocol")
  }
}
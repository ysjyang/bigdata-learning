package com.netcloud.bigdata.scala_action.chapter4

/**
  * 设置构造函数参数默认值
  * @author yangshaojun
  * 2018/12/27 13:20
  * @version 1.0
  */
object ConstrutorDefaultValue {
  def main(args: Array[String]): Unit = {
    /*
     * 因为构造函数参数有默认值 可以在调用构造函数的时候不指定超时时间，
     * 这样就会使用默认值*/
    val s1 = new Socket
    println("default timeout=" + s1.timeout)
    /*创建新的Socket类 传入一个值覆盖默认值*/
    val s2 = new Socket(5000)
    println("overwride timeout=" + s2.timeout)
    /*通过指定参数的方式调用构造函数和方法*/
    val s3 = new Socket(timeout = 6000)
    println("point timeout=" + s3.timeout)

    //多个构造函数默认参数的类的实例化
    println(new Socket2())
    println(new Socket2(3000))
    println(new Socket2(3000, 4000))

    //创建对象的时候可以指定构造函数参数名字
    println(new Socket2(timeout = 5000, linger = 6000))
    println(new Socket2(linger = 6000, timeout = 5000))
    println(new Socket2(linger = 7000))
    println(new Socket2(timeout = 8000))
  }

}

//构造函数定义里给一个默认值
class Socket(val timeout: Int = 1000)

//给构造函数设置多个默认的参数值
class Socket2(val timeout: Int = 1000, val linger: Int = 2000) {
  override def toString: String = s"timeout:$timeout,linger:$linger"
}

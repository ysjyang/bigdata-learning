package com.netcloud.bigdata.scala_action.chapter6

/**
  * 用Object对象创建单例
  * 创建一个Singleton对象，来保证只有一个类的实例存在
  * 这种方式在创建工具方法时很常见。如 DataUtils对象
  * @author yangshaojun
  * 2019/1/1 17:43
  * @version 1.0
  */
object UseObjectCreateSingleton {
  def main(args: Array[String]): Unit = {
    //CashRegister只有一个实例 而且他调用方法的方式就像java类调用静态方法一样
    CashRegister.open
  }

  object CashRegister {
    def open: Unit = {
      println("opened")
    }

    def close: Unit = {
      println("closed")
    }
  }

}

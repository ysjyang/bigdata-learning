package com.netcloud.bigdata.scala_action.chapter5

/**
  * 定义一个返回多个值的方法
  *java中通常将多个值的结果封装到一个类中
  *但是scala可以使用tuples的方式从方法中返回多个值
  * @author yangshaojun
  * 2018/12/31 15:20
  * @version 1.0
  */
object ReturnMuiltValueWay {
  def main(args: Array[String]): Unit = {
    val stock = new StockInfo
    println(stock.getStockInfo)
  }

}

class StockInfo {
  def getStockInfo = {
    ("NFLX", 100,101)
  }
  val result = getStockInfo
}
package com.netcloud.bigdata.scala_action.chapter3

/**
  * @author yangshaojun
  * 2018/12/21 14:26
  * @version 1.0
  */
/**
  * scala中的try catch finally 和java的类似 唯一不同的就是scala中的 catch 可以使用模式匹配的方式
  * 如果需要捕获处理多个异常 只有添加异常类型作为不同的case语句
  */
object TryCatch {
  def main(args: Array[String]): Unit = {

     val s="Foo"
    try {
      val i=s.toInt
    }catch {
      case e:Exception => e.printStackTrace
      case e:IndexOutOfBoundsException => println("数组越界异常！")
    }

    //如果声明方法抛出的异常 在定义方法的时候添加@throws
    @throws(classOf[NumberFormatException])
    def toInt(s:String) :Int={
      try{
        s.toInt
      }catch {
        case e:NumberFormatException => throw e
      }
    }
  }

}

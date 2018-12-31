package com.netcloud.bigdata.scala_action.chapter5

/**
  * 方法的异常声明
  * 在方法的上面加上@throws注解 类似于java在其方法后面加 throws
  * @author yangshaojun
  * 2018/12/31 15:22
  * @version 1.0
  */
object ExceptionDeclare {
  def main(args: Array[String]): Unit = {

  }

  @throws(classOf[IndexOutOfBoundsException])
  def playSoundFIleWithJavaAudio(): Unit ={
//    exception code
  }
}


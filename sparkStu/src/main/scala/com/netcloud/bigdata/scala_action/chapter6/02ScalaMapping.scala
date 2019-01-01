package com.netcloud.bigdata.scala_action.chapter6


/**
  * Java.class 的scala等价类
  * 当一个API 要求传入一个类，在java中需要在对象上调用 .class
  * 但是在scala中不嫩这么做,scala中使用classOf方法来取代java.class
  * @author yangshaojun
  * 2019/1/1 16:37
  * @version 1.0
  */
object ScalaMapping {
  def main(args: Array[String]): Unit = {
       /* 下面的例子展示了如何将TargetDataLine类型的类传给DataLine.Info
        *val info=new DataLine.Info(calssOf[TargetDataLine],null)
        * info=new DataLine(TargetDataLine.class,null)
        *
        */
  }

}

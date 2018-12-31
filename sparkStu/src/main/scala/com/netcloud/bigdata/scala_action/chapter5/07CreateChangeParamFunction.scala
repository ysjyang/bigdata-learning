package com.netcloud.bigdata.scala_action.chapter5

/**
  * 创建接受变参的方法
  * 1) 在方法的参数类型后面加一个 *这个参数就会变为变参
  * 2) 使用 _*操作符 适配一个序列
  * 3) 定义的方法只能有一个变参，必须作为最后一个参数
  * 4) 调用只有一个变参的方法 可以不指定值，这样也不会报错
  * @author yangshaojun
  * 2018/12/31 15:22
  * @version 1.0
  */
object CreateChangeParamFunction {
  def main(args: Array[String]): Unit = {
    val c=new ChangeParam
    c.printAll("HDFS","MR","zk","hive")
    val fruits=List("apple","banana","cherry")
    c.printAll(fruits:_*)
    c.printAll()//不指定值
  }
}
class ChangeParam{

  def printAll(strings:String*): Unit ={
    strings.foreach(println)
  }

  //定义一个含有变参的方法时，这个变参必须放在方法参数中的最后 否则编译报错 同时一个方法只能有一个变参

/*  def printAll2(strings:String*,i:Int): Unit ={
    strings.foreach(println)
  }*/
}


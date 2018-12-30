package com.netcloud.bigdata.scala_action.chapter4

/**
  * 在继承类时处理构造函数参数
  * 1) 父类的主构造函数参数定义为 var 或者 val修饰的
  * 2) 子类在继承的时候 对于继承过来的父类字段 不需要继续使用var 或者val修饰 只需将新的字段进行var val 定义
  *
  * @author yangshaojun
  * 2018/12/30 20:29
  * @version 1.0
  */
object ExtendsFatcherClass {
  def main(args: Array[String]): Unit = {
    val employ = new Employee("ysj", Address("beijing", "AK", "99663"))
    println("employ's name:"+employ.name +"employ.address:"+employ.address)
  }

}
class FatherPerson(var name:String,var address:Address)

class Employee(name:String,address: Address) extends FatherPerson(name,address)

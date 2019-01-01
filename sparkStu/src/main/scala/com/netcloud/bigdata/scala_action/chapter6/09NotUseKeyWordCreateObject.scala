package com.netcloud.bigdata.scala_action.chapter6

/**
  * 不使用new 关键字创建对象的实例
  * 方式有两种
  * 1) 在伴生对象中定义apply方法(这个方法本质上是类的构造函数)
  * 2) 创建一个case类
  * @author yangshaojun
  * 2019/1/1 18:24
  * @version 1.0
  */
object NotUseKeyWordCreateObject {
  def main(args: Array[String]): Unit = {
    val dawn = PersonObject("dawn")
    //伴生对象创建对象实例
    val john = PersonObject("John", 23) //伴生对象创建对象实例
    println(john.age)

    //样例类创建对象实例
    val caseObject = CaseClassCreateObject("ysj")
    println(caseObject.name)
  }
}

class PersonObject {
  var name: String = _
  var age: Int = _
}
object PersonObject {
  def apply(name: String): PersonObject = {
    val p = new PersonObject
    p.name = name
    p
  }
  /*还可以为apply方法提供多个构造函数*/
  def apply(name: String, age: Int): PersonObject = {
    val p = new PersonObject
    p.name = name
    p.age = age
    p
  }
}
case class CaseClassCreateObject(name: String) {

}
package com.netcloud.bigdata.scala_action.chapter4

/**
  * 覆写scala自动生成的setter和getter方法
  * 1) 将变量声明为 private var name
  * 2) 在类的内部定义 SetName GetName 方法避免和scala的setter和getter命名冲突
  * 3) 如果继续使用scala的setter和getter命名风格，需要将构造函数变量加前缀避免和getter方法名冲突。
  * 4) 此方式适用构造函数字段 或者类内部的字段
  * @author yangshaojun
  * 2018/12/27 13:58
  * @version 1.0
  */
object OverrideDefaultSetGet {
  def main(args: Array[String]): Unit = {

    val p1 = new Person1("ysj")
    println(p1.getName())
    val p2 = new Person2("aaa")
    println(p2.name)

  }

}
/*不使用scala命名的方式去命名setter和getter可以使用javaBean的风格把方法命名为
* getName和setName*/
class Person1(private var name:String){
  //覆写方式1  name 的setter getter方法
  def setName(name:String): Unit ={
   this.name=name
  }
  def getName(): String ={
    name
  }

  /*
    *类内部的字段覆写setter和getter方法
    */
  private var _age = 30

  def age = _age

  def age_=(alieAge: Int): Unit = {
    this _age = alieAge
  }

  /*
  * 类内部的字段覆写setter和getter方法
  * */
  private var sex = "Man"

  def getSex() = sex

  def setSex(sex: String) = this.sex = sex

}
/* 如果覆写的setter和getter方法 还是按照scala的命名约定那么
 * 构造函数参数名称和getter方法的名称相同是不能覆写setter getter方法
 * 因此需要将构造函数字段的名字前面加一个下划线，避免与getter方法名称冲突
 */
class Person2(private var _name:String){
  //覆写方式2  scala风格的setter getter方法
  def name=_name
  //name_ 与 name_$eq是一样的,但是scala需要把 = 翻译为$eq JVM才能工作
  def name_=(aName:String): Unit ={
    _name=aName
  }
}
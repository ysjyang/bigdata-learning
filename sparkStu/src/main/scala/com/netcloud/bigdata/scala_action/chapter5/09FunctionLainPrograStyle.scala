package com.netcloud.bigdata.scala_action.chapter5

/**
  * 支持链式调用的编程风格
  * 返回的返回值 为 this 返回值类型为 this.type
  * @author yangshaojun
  * 2018/12/31 15:23
  * @version 1.0
  */
object FunctionLainPrograStyle {
  def main(args: Array[String]): Unit = {
    val p=new PersonLain
    p.setFirstName("yang").setLastName("shaojun").setAge(25)
    println(p)
  }

}
class PersonLain{
  var firstName=""
  var lastName=""
  var age=0
  def setFirstName(firstName:String) ={
    this.firstName=firstName
    this
  }
  def setLastName(lastName:String) ={
    this.lastName=lastName
    this
  }
  def setAge(age:Int) ={
    this.age=age
    this
  }

  override def toString: String = s"The person's firstname=$firstName,lastname=$lastName,age=$age"
}

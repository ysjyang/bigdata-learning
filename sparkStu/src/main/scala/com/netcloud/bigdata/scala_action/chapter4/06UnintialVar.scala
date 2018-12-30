package com.netcloud.bigdata.scala_action.chapter4

/**
  * 设置未初始化的var字段类型
  * 1)  把未初始化的address定义为一个Optition
  * 2)  当用户提供一个地址，可以赋给字段一个Some[Address]
  * 3)  不要使用null作为字段值
  * @author yangshaojun
  * 2018/12/30 18:41
  * @version 1.0
  */
object UnintialVar {
  def main(args: Array[String]): Unit = {
    val person = PersonTest("admin", "123456")
    person.address = Some(Address("beijing", "AK", "99663"))
    println("person's address is "+person.address)
  }

}

case class PersonTest(var userName: String, var passwd:String) {
  var age = 0
  var firstName = ""
  var lastName = ""
  var address = None: Option[Address] // 把未初始化的address定义为一个Optition
//  var sex = null

}

case class Address(city: String, state: String, zip: String) {
  override def toString: String = s"city：$city,state:$state,zip:$zip"

}

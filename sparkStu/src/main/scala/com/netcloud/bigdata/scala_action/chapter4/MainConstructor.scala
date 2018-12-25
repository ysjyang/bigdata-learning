package com.netcloud.bigdata.scala_action.chapter4

import com.netcloud.bigdata.scala_action.chapter4.until.FileUtils

/**
  * @author yangshaojun
  * 2018/12/21 17:11
  * @version 1.0
  */
/**
  * 主构造器函数
  * 1) scala的主构造器函数和java的不同 ; Scala的主构造器是整个类体，需要在类名称后面罗列出构造器所需的所有参数，这些参数被编译成类的字段属性，字段的值就是创建对象时传入的参数的值。
  * 2) 主构造器的参数中，没有被任何关键字(val 或者val)修饰的参数，会被标注为private[this] 这样的参数只能在本类中访问，其他地方无法访问。
  * 3) 不包含在类定义中任何方法中的代码，就是主构造器的代码。包括方法的调用也是主构造器代码。
  *
  * 类的实例化
  * 创建对象时代码的执行顺序一定是主构造器代码先执行。
  */
object MainConstructor {
  def main(args: Array[String]): Unit = {
    val person=new Person("Adam","Meyer",25,"man","play")

    /*被var修饰的变量既有setter方法也有getter方法*/
    println(person.firstname)//getter方法
    person.firstname="andy" //setter方法
    println(person.firstname)

    /*被val修饰的变量只有getter方法 没有setter方法*/
    //person.age=30 没有setter方法
    println(person.age)

    /*没有被var或者val修饰的变量 没有getter或setter方法*/
    // println(person.sex)

    //私有化主构造函数 不能实例化对象
    //val order=new Order("ysj")
    val order = Order.getInstance
    println(order.name)
    //通过工具类获取公用方法
    val currentTime=FileUtils.getCurrentTime()
    println("currentTime="+currentTime)

  }

}
class Person(var firstname:String,var lastname:String,val age:Int,sex:String,private var hoby:String){
  println("The main constructor begins")
  //定义类的属性
  private val HOME=System.getProperty("user.home")
  var isAdult=false

  //定义方法
  override def toString: String = s"$firstname $lastname is $age years old"
  def printHome(): Unit ={
    println(s"HOME=$HOME")
  }

  def printFullName(){println(this)}


}
//定义私有的主构造函数 应用单例模式
class Order private(var name:String){
  override def toString = s"$name"
}
/**在Order的伴生对象中定义个getInstance方法获取对象
  * 其他object可以使用 Order.getInstance 直接获取对象*/
object Order{
  val order=new Order("ysj")
  def getInstance=order
}

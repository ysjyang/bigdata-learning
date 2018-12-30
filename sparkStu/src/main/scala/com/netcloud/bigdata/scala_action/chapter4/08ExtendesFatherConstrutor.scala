package com.netcloud.bigdata.scala_action.chapter4

/**
  *1) 子类继承父类的时候，子类的主构造函数可以调用父类的任意一个构造函数
  *   但是子类的辅助构造函数 无法直接调用父类的任意构造函数
  *2) 子类调用父类的构造函数的时候 如果父类的构造函数指定了一个默认的值
  *   子类的主构造函数参数可以对这个默认值的声明进行省略
  * @author yangshaojun
  * 2018/12/30 21:58
  * @version 1.0
  */
object ExtendesFatherConstrutor {
  def main(args: Array[String]): Unit = {
    val dog=new Dog("旺财")
    val cat=new Cat("kitty")
    println("dog's name:"+dog.age)
    println("cat's name:"+cat.age)
  }
}

//父类的主构造函数
class Animal(var name: String, var age: Int) {
  //  父类的辅助构造函数
  def this(name: String) {
    this(name, 25)
  }

}
//子类继承父类 其主构造函数调用父类的一个参数的辅助构造函数
class Dog(name:String) extends Animal(name)
//子类继承父类 其主构造函数调用父类的两个参数的辅助构造函数
/*这里父类的age给了默认的值0 那么子类的主构造函数的参数中可以省略age的字段声明*/
class Cat(name:String) extends Animal(name,0)

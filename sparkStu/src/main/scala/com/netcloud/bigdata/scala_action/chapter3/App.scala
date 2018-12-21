package com.netcloud.bigdata.scala_action.chapter3

/**
  * @author yangshaojun
  *         #date  2018/12/21 15:39
  * @version 1.0
  */
object App {


}
trait Animal {
  case  class Dog(name:String)  extends Animal
  case  class Cat(name:String)  extends Animal
}


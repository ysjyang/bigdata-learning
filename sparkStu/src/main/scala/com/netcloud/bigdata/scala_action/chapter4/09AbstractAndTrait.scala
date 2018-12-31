package com.netcloud.bigdata.scala_action.chapter4


/**
  * 何时使用抽象类
  * 1) 需要创建一个有构造函数参数的基类
  * 2) 需要被java代码调用
  * 抽象类的特点
  * 1) 用关键字abstract声明的类就是抽象类
  * 2) 在抽象类中可以定义完整的实现方法，也可以没有实现的方法（抽象方法）
  *    可以定义抽象属性，也可以定义实现的属性
  * 3) 抽象方法(属性)不需要 abstract声明，只要没有实现就默认是抽象方法(属性)
  * 4) 子类在继承抽象类的时候必须实现父类的抽象方法(属性)，如果不实现那么需要把子类
  *     也定义为抽象类，否则抛出异常
  *
  * Note:
  * a)  到底是使用抽象类还是特质 区别在于基类是否需要构造函数参数
  *     当基类需要构造函数参数，那么就使用抽象类，否则使用特质（多实现的优势）；
  * b)  子类实现父类抽象的val或者var字段 那么子类中需要再次定义这些字段为val或者var
  * c)  子类重写父类具体的val字段 那么子类中需要再次定义这些字段为val 加上override关键字
  * d)  子类重写父类具体的var字段 那么子类中不需要再次定义这些字段为var，不需要加override关键字
  * e)  为了阻止抽象基类中具体的val字段被子类覆写，将字段声明为final val
  * @author yangshaojun
  * 2018/12/31 10:41
  * @version 1.0
  */
object AbstractAndTrait {
  def main(args: Array[String]): Unit = {
    val p = DetailClass("test")
    println(p.sex)

  }
}

abstract class BaseController(p: String) {
  /*抽象类中的具体实现方法*/
  def save: Unit = {
    println("This is  a detail function")
  }

  /*下面的方法都是抽象方法*/
  def connect

  def getStatus: String

  def getServerName(serverName: String)

  /*抽象类中定义属性 可以是抽象的也可以是有具体实现的*/
  val greeting: String
  var age: Int
  //抽象类具体实现的属性
  val sex: String = "Man" //抽象类中具体的val字段
  var name: String = "kitty" ////抽象类中具体的var字段
  // 不要使用null
  val address: Option[Address]
  var result: Option[Int] = None

  def sayHello(): Unit = {
    println(greeting)
  }

  override def toString: String = s"I say $greeting,and I'm $age"

}

/*子类继承抽象类 实现父类的抽象方法、属性*/
case class DetailClass(p: String) extends BaseController(p) {
  override def connect: Unit = {}

  override def getStatus: String = {
    "0"
  }

  override def getServerName(serverName: String): Unit = {}

  /*override不是必须的，val 或者 var 是必须的 要跟父类的一一对应*/
  val greeting: String = "Woof"
  var age: Int = 25
  /*重写具体的val字段override是必须的 为了阻止父类中具体的val字段被子类重写 将字段声明为final val*/
  override val sex: String = "Woman"
  /*重写具体的var字段 不需要将字段重新命名为val或者var*/
  name = "flypig"
  val address = Some(Address("beijing", "AK", "99663"))
  result = Some(2)

}


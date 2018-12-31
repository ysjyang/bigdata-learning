package com.netcloud.bigdata.scala_action.chapter4

/**
  * Case class 样例类 生产模块代码
  * 被关键字case 修饰的类就是样例类
  * 如果样例类中没有其他的方法可以省略 {}
  * 1) case class 创建类的实例不需要使用new关键字
  * 2) case class 构造函数参数默认是val、
  *   但是也可以指定为var，这样就违背了case类设计的本意,不建议这样使用。
  * 3) 定义一个类为case class 默认生成很多的模块代码
  *    apply方法、默认的toString方法、unapply方法、equals方法、hashCode方法
  *    copy方法等。
  * 4) case类比普通类多出很多的方法，如果使用那些方法可以定义一个case类，否则考虑定义一个普通类
  *
  * 特点：其实样例类和普通的类没有什么区别，
  *   a)唯一的区别就是样例类的实例化不需要通过关键字new的方式。当
  *   然也可以通过new的方式实例化。
  *   b)定义一个类为case class 默认生成很多的模块代码。
  *
  * @author yangshaojun
  * 2018/12/31 13:30
  * @version 1.0
  */
case class Family(name:String,relation:String)

class NormalClass(var name:String,var relation:String)
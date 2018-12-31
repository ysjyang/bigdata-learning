package com.netcloud.bigdata.scala_action.chapter4

/**
  * 内部类就是定义在对象或者类内部的类；内部对象就是定义在对象或者类内部的对象。
  * 下面的代码中定义了一个伴生类(Student)和伴生对象(Student)，
  *  在伴生类中定义了一个内部类Grade和一个内部对象Utils01，
  *  在伴生对象中则定义了一个内部类Printer 和内部对象Utils02.
  * 1）创建内部类Grade的时候使用  new 外部类对象.内部类名称(内部类构造方法参数)的方式创建内部类对象。可以看到访问内部类就像是内部类是其成员变量一样。
  *     外部类对象.内部对象的方式访问内部对象。
  * 2）伴生对象.内部类名称(内部类构造参数)的方式创建伴生对象中的内部类对象。
  *     伴生对象.内部对象的方式访问伴生对象中的内部对象。 
  *
  * @author yangshaojun
  * 2018/12/31 14:01
  * @version 1.0
  */
object InnerClass{
  def main(args: Array[String]): Unit = {

    //    创建伴生类对象
    val student = new Student("ysj", 25)
    //    创建伴生类中的内部类对象
    val grade=new student.Gradle("level1")
    println("访问伴生类中内部类中的属性" + grade.name)
    //    调用伴生类内部对象的方法
    student.Utils01.print("ysjloveqff")

    /*创建伴生对象的内部类对象*/
    val printer = new Student.printer
    printer.print("调用伴生对象的内部类方法！")
    Student.Untils2.print("调用伴生对象的内部对象方法！")

  }
}


/*伴生类*/
class Student(var name:String,var age:Int){
  //  伴生类的内部类
  class Gradle(var name: String) {
  }
  //伴生类的内部对象
  object Utils01 {
    def print(name: String) = {
      println(name)
    }
  }

}

/*伴生对象*/
object Student {
  //  伴生对象的内部类printer
  class printer {
    def print(name: String): Unit = {
    }
  }
  // 伴生对象的单例对象
  object Untils2 {
    def print(name: String) = {
      println(name)
    }
  }

}


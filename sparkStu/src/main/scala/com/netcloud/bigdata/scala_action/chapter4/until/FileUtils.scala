package com.netcloud.bigdata.scala_action.chapter4.until

import java.text.SimpleDateFormat
import java.util.Date

/**
  * 声明一个Object为工具类
  * 在工具类里面定义常用的方法
 *
  * @author yangshaojun
  * 2018/12/25 22:56
  * @version 1.0
  */
object FileUtils {

  def getCurrentTime(): String ={
    val now: Date = new Date()
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = dateFormat.format(now)
    return date
  }


}

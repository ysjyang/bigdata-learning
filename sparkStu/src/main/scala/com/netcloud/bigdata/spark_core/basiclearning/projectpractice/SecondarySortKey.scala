package com.netcloud.bigdata.spark_core.basiclearning.projectpractice

/**
  * 自定义二次排序的key
  * @author yangshaojun
  * #date  2019/3/14 22:17
  * @version 1.0
  */
case class SecondarySortKey(first:Int,second:Int) extends Ordered[SecondarySortKey] with Serializable{
  override def compare(that: SecondarySortKey): Int = {
    if(this.first - that.first !=0){
      this.first-that.first
    }else{
      this.second-that.second
    }
  }
}

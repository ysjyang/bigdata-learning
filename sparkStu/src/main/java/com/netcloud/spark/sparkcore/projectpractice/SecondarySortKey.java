package com.netcloud.spark.sparkcore.projectpractice;

import scala.math.Ordered;

import java.io.Serializable;
import java.util.Objects;

/**
 * 自定义的二次排序Key
 * 1）实现Ordered 、Serializable接口
 *
 * @author yangshaojun
 * #date  2019/3/14 20:54
 * @version 1.0
 */
public class SecondarySortKey implements Ordered<SecondarySortKey>, Serializable {


    //在自定义key里面，定义需要进行排序的列
    private int first;
    private int second;

    public SecondarySortKey(int first, int second) {
        this.first = first;
        this.second = second;
    }

    //重新大于方法
    @Override
    public boolean $greater(SecondarySortKey other) {
        if (this.first > other.getFirst()) {
            return true;
        } else if (this.first == other.getFirst() && this.second > other.getSecond()) {
            return true;
        }
        return false;
    }

    //重写大于等于方法
    @Override
    public boolean $greater$eq(SecondarySortKey other) {
        if (this.$greater(other)) {
            return true;
        } else if (this.first == other.first && this.second == other.getSecond()) {
            return true;
        }
        return false;
    }

    //重新小于的方法
    @Override
    public boolean $less(SecondarySortKey other) {
        if (this.first < other.getFirst()) {
            return true;
        } else if (this.first == other.getFirst() && this.second < other.getSecond()) {
            return true;
        }
        return false;
    }

    //重新小于等于方法
    @Override
    public boolean $less$eq(SecondarySortKey other) {

        if (this.$less(other)) {
            return true;
        } else if (this.first == other.first && this.second == other.getSecond()) {
            return true;
        }
        return false;
    }

    @Override
    public int compare(SecondarySortKey other) {
        if (this.first - other.getFirst() != 0) {
            return this.first - other.getFirst();
        } else {
            return this.second - other.getSecond();
        }
    }

    @Override
    public int compareTo(SecondarySortKey other) {
        if (this.first - other.getFirst() != 0) {
            return this.first - other.getFirst();
        } else {
            return this.second - other.getSecond();
        }
    }

    //为要进行排序的多个列，提供getter和setter方法 以及hashcode和equals方法
    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecondarySortKey that = (SecondarySortKey) o;
        return first == that.first &&
                second == that.second;
    }

    @Override
    public int hashCode() {

        return Objects.hash(first, second);
    }
}

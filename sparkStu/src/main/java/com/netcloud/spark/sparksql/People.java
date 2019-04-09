package com.netcloud.spark.sparksql;

import java.io.Serializable;

/**
 * 创建Person类 并实现序列化
 * Person类中仅支持简单数据类型，不能有复杂的数据类型 像List Array 或者自定类型
 * 对于scala的样例类时可以支持Array seq等类型的
 * @author yangshaojun
 * #date  2019/4/3 11:40
 * @version 1.0
 */
public class People implements Serializable {

    private static final long serialVersionUID = 555241500840980177L;
    private String name;
    private int age;
    public People() {

    }
    public People(String name,int age) {
        this.name = name;
        this.age=age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "name:"+name+" "+"age:"+age;
    }
}

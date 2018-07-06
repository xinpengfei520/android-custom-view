package com.xpf.contactquickindex;

/**
 * Created by xpf on 2016/9/24 14:43
 * Function:
 */
public class Person {
    private String name;
    private String pinyin;


    public Person(String name){
        this.name = name;
        this.pinyin = PinYinUtils.getPinYin(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}

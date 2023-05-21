package com.github.hioz.learn.data;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = 7648930056693530042L;

    private String name;

    private int age;

    private Pet pet;

    public Person() {

    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static String getAncestor(String who) {
        return who + "'s ancestor is Ape";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}

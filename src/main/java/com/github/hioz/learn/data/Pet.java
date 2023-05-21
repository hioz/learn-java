package com.github.hioz.learn.data;

import java.io.Serializable;

public class Pet implements Serializable {

    private static final long serialVersionUID = -3149355010660764990L;

    private String name;

    private Animal animal;

    public Pet() {

    }

    public Pet(String name, Animal animal) {
        this.name = name;
        this.animal = animal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}

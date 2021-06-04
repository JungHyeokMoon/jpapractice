package com.jpaexample.jpapratice.ch07;

import com.jpaexample.jpapratice.ch06.Item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
public class Book extends Item {

    private String author;
    private String isbn;

}

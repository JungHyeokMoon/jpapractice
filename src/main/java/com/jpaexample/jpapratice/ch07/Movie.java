package com.jpaexample.jpapratice.ch07;

import com.jpaexample.jpapratice.ch06.Item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
public class Movie extends Item {
    private String director;
    private String actor;
}

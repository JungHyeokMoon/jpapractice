package com.jpaexample.jpapratice.ch07;

import com.jpaexample.jpapratice.ch06.Item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Album extends Item {
    private String artist;
    private String etc;
}

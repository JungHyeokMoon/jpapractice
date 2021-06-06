package com.jpaexample.jpapratice.domain.ch06;

import com.jpaexample.jpapratice.domain.ch05.entity.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="CATEGORY")
@AttributeOverride(name = "id",column = @Column(name="CATEGORY_ID"))
@Getter
public class Category extends BaseEntity {

    private String name;

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    public void addChildCategory(Category category){
        this.child.add(category);
        category.setParent(this);
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void addItem(Item item){
        items.add(item);
    }
}

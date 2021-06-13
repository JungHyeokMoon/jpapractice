package com.jpaexample.jpapratice.domain.ch06;

import com.jpaexample.jpapratice.domain.ch05.entity.BaseEntity;
import com.jpaexample.jpapratice.exception.BusinessException;
import com.jpaexample.jpapratice.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ITEM")
@AttributeOverride(name = "id",column = @Column(name="ITEM_ID"))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@NoArgsConstructor
@Getter
public abstract class Item extends BaseEntity {

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    //==비즈니스로직==//
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock<0){
            throw new BusinessException(ErrorCode.QUANTITY_LACK);
        }
        this.stockQuantity=restStock;
    }
}

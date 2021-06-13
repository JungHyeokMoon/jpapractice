package com.jpaexample.jpapratice.repository;

import com.jpaexample.jpapratice.domain.ch06.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}

package com.jpaexample.jpapratice.service;

import com.jpaexample.jpapratice.domain.ch06.Item;
import com.jpaexample.jpapratice.exception.BusinessException;
import com.jpaexample.jpapratice.exception.ErrorCode;
import com.jpaexample.jpapratice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Item item){
        if(ObjectUtils.anyNull(item)){
            throw new BusinessException(ErrorCode.MISSING_REQUIRED_ITEMS);
        }
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findById(itemId).orElseThrow(
                () -> new BusinessException(ErrorCode.SELECT_EMPTY)
        );
    }

}

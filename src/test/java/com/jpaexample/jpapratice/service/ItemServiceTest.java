package com.jpaexample.jpapratice.service;

import com.jpaexample.jpapratice.domain.ch06.Item;
import com.jpaexample.jpapratice.domain.ch07.Album;
import com.jpaexample.jpapratice.domain.ch07.Book;
import com.jpaexample.jpapratice.domain.ch07.Movie;
import com.jpaexample.jpapratice.exception.BusinessException;
import com.jpaexample.jpapratice.exception.ErrorCode;
import com.jpaexample.jpapratice.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("ItemService - ")
class ItemServiceTest {

    @Nested
    @DisplayName("saveItem 메서드의")
    class ItemServiceSaveItem extends CommonConfig {

        @Nested
        @DisplayName("실패테스트")
        class ItemServiceSaveItemFail {

            @Test
            @DisplayName("- null이 인자로 전달되면 실패합니다.")
            void test() {
                BusinessException businessException = assertThrows(BusinessException.class, () -> itemService.saveItem(null), ErrorCode.MISSING_REQUIRED_ITEMS.getMessage());
                assertEquals(ErrorCode.MISSING_REQUIRED_ITEMS.getMessage(), businessException.getErrorCode().getMessage());
            }
        }

        @Nested
        @DisplayName("성공테스트")
        class ItemServiceSaveItemSuccess {
            @Test
            @DisplayName("- Item이 정상적으로 인자로 들어갈 경우, 성공합니다.")
            void test() {
                Movie movie = Movie.builder().name("영화1").actor("actor1").director("director")
                        .price(1000).stockQuantity(10).build();
                itemService.saveItem(movie);
            }
        }
    }

    @Nested
    @DisplayName("findItems 메서드의")
    class ItemServiceFindItems extends CommonConfig {

        @Nested
        @DisplayName("성공테스트")
        class ItemServiceFindItemsSuccess {

            @Test
            @DisplayName("- Item 추상클래스를 상속받은 movie, album, book을 생성해서 저장하면 성공합니다.")
            void test() {
                Movie movie = Movie.builder().name("영화1").actor("actor1").director("director")
                        .price(1000).stockQuantity(10).build();
                Album album = Album.builder().name("앨범1").artist("아티스트1").etc("etc").price(2000).stockQuantity(5).build();
                Book book = Book.builder().author("author").isbn("isbn").name("name").price(500).stockQuantity(7).build();
                itemRepository.save(movie);
                itemRepository.save(album);
                itemRepository.save(book);

                List<Item> items = itemService.findItems();
                assertEquals(3, items.size());
            }
        }

    }

    @Nested
    @DisplayName("findOne 메서드의")
    class ItemServiceFindOne extends CommonConfig {

        @Nested
        @DisplayName("성공테스트")
        class ItemServiceFindOneSuccess {

            @Test
            @DisplayName("- id 조회시 있을경우, 성공합니다.")
            void test() {
                Movie movie = Movie.builder().name("영화1").actor("actor1").director("director")
                        .price(1000).stockQuantity(10).build();

                Movie save = itemRepository.save(movie);
                Item one = itemService.findOne(save.getId());
                assertNotNull(one);
            }
        }

        @Nested
        @DisplayName("실패테스트")
        class ItemServiceFindOneFail {

            @Test
            @DisplayName("- id 조회시 없을경우 실패합니다.")
            void test() {
                BusinessException businessException = assertThrows(BusinessException.class, () -> itemService.findOne(-1L), ErrorCode.SELECT_EMPTY.getMessage());
                assertEquals(ErrorCode.SELECT_EMPTY.getMessage(),businessException.getErrorCode().getMessage());
            }
        }
    }
}

@SpringBootTest
@Transactional
class CommonConfig {
    @Autowired
    protected ItemService itemService;

    @Autowired
    protected ItemRepository itemRepository;
}
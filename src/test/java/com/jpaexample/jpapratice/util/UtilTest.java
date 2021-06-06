package com.jpaexample.jpapratice.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    @Test
    @DisplayName("해쉬맵 key 값 중복 테스트")
    void test(){
        Map<TestClass, Integer> map = new HashMap<>();
        TestClass test1 = new TestClass(1, 'c');
        TestClass test2 = new TestClass(1, 'c');
        map.put(test1, 1);
        map.put(test2, 1);
        map.put(null, 3); // null출력됨
        assertEquals(test2, test1);

        System.out.println(map);
    }


    static class TestClass{
        int a;
        char b;

        public TestClass(int a, char b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestClass testClass = (TestClass) o;
            return a == testClass.a && b == testClass.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    @Test
    @DisplayName("패스워드 Util test")
    void test2(){
        assertFalse(Regex.isValidPassword(" "));
        assertFalse(Regex.isValidPassword("abc123"));
        assertFalse(Regex.isValidPassword("abcd1234"));
        assertFalse(Regex.isValidPassword("abcd!1234!"));
        assertTrue(Regex.isValidPassword("abcd!1234!M"));
        assertFalse(Regex.isValidPassword("asdxc 123!M"));
    }
}

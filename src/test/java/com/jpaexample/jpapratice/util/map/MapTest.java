package com.jpaexample.jpapratice.util.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {

    @Test
    @DisplayName("Map 인터페이스 테스트 - clear")
    void test() {
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put(null, 2);
        map.put("2", 3);
        assertEquals(3, map.size());
        map.clear();
        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("Map 인터페이스 테스트 - put")
    void test2() {
        Map<String, Integer> map = new HashMap<>();
        map.put(null, null);
        assertNull(map.get(null));
        map.put(null, 1);
        assertEquals(map.get(null), 1);
        map.put("3", 2);
        assertEquals(2, map.size());
    }

    @Test
    @DisplayName("Map인터페이스 테스트 - containsKey, containsValue, putAll")
    void test3() {
        Map<Object, Object> map = new HashMap<>();

        Map<String, Integer> putAllMap = new HashMap<>();
        putAllMap.put("1", 1);
        putAllMap.put("2", 2);
        putAllMap.put("3", 3);
        map.putAll(putAllMap);

        assertEquals(3, map.size());
        assertTrue(map.containsKey("1"));
        assertTrue(map.containsKey("2"));
        assertTrue(map.containsKey("3"));
        assertTrue(map.containsValue(1));
        assertTrue(map.containsValue(2));
        assertTrue(map.containsValue(3));
    }

    @Test
    @DisplayName("Map 인터페이스 테스트 - entrySet(), keySet(), values()")
    void test4() {
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);

        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        Set<String> strings = map.keySet();
        Collection<Integer> values = map.values();

        System.out.println(entries);
        System.out.println(strings);
        System.out.println(values);
    }

    @Test
    @DisplayName("Map 인터페이스 테스트 - compute")
    void test5() {
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.compute("3", (s, integer) -> integer.intValue() + 4);
        assertEquals(7, map.get("3"));

        map.compute("2", (s, integer) -> null);
        System.out.println(map);
        assertThrows(NullPointerException.class, () -> map.compute("3", null));
    }

    @Test
    @DisplayName("Map 인터페이스 테스트 - computeIfAbsent")
    void test6() {
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.computeIfAbsent("4", Integer::parseInt);
        map.computeIfAbsent("2", s -> Integer.parseInt(s) + 10);
        assertEquals(4, map.get("4"));
        assertEquals(2, map.get("2"));
    }

    @Test
    @DisplayName("Map 인터페이스 테스트 - computeIfAbsent - value가 Collection일경우")
    void test7() {
        Map<String, List<Integer>> map = new HashMap<>();
        map.computeIfAbsent("1", s -> new ArrayList<>()).addAll(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> integers = map.get("1");
        assertTrue(integers.contains(1));
        assertTrue(integers.contains(2));
        assertTrue(integers.contains(3));
        assertTrue(integers.contains(4));
        assertTrue(integers.contains(5));
    }

    @Test
    @DisplayName("Map 인터페이스 테스트 - computeIfPresent")
    void test8(){
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);

        map.computeIfPresent("1", (string, integer) -> Integer.parseInt(string) + integer);
        assertEquals(2,map.get("1"));
    }

    @Test
    @DisplayName("Map 인터페이스 테스트 - get")
    void test9(){
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        assertEquals(1, map.get("1"));
        assertNull(map.get("2"));
    }

    @Test
    @DisplayName("Map 인터페이스 테스트 - getOrDefault")
    void test10(){
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        assertEquals(3, map.getOrDefault("2", 3));
    }

    @Test
    @DisplayName("Map 인터페이스 테스트 - putIfAbsent")
    void test11(){
        Map<String, Integer> map = new HashMap<>();
        map.putIfAbsent("1", 1);
        map.putIfAbsent("1", 2);
        assertEquals(1, map.size());
        assertEquals(1,map.get("1"));
    }

    @Test
    @DisplayName("Map 인터페이스 테스트 - forEach")
    void test12(){
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.forEach((k,v)-> System.out.println(k+" "+v));
    }

    @Test
    @DisplayName("Map 인터페이스 테스트 - merge - key값 없을때는 bifunction 무시")
    void test13(){
        Map<String, Integer> map = new HashMap<>();
        map.merge("1", 100,(oldValue,newValue)->oldValue+newValue);
        assertEquals(100,map.get("1"));
    }

    @Test
    @DisplayName("Map 인터페이스 테스트 - merge key값 존재할떄")
    void test15(){
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 100);
        map.merge("1", 200, (oldValue, newValue) -> {
            System.out.println(oldValue + " " + newValue);
            return oldValue + newValue;
        });
        assertEquals(300,map.get("1"));
    }

    @Test
    @DisplayName("Map 인터페이스 테스트 - replaceAll")
    void test14(){
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");
        map.put(4, "4");
        map.put(5, "5");
        map.put(6, "6");
        map.replaceAll((integer, s) -> integer % 2 == 0 ? "Changed" : s);
        System.out.println(map);
    }

}


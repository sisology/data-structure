package com.collection.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapAndArrayList {

    public static void main(String[] args) {

        // Map 여러 개를 List를 사용해서 감싸기
        Map<String, Integer> stringMap = new HashMap<>();
        stringMap.put("a", 1);
        stringMap.put("b", 2);
        stringMap.put("c", 3);

        Map<Integer, String> IntegerMap = new HashMap<>();
        IntegerMap.put(1, "a");
        IntegerMap.put(2, "b");
        IntegerMap.put(3, "c");

        List<Map<String, Integer>> list = new ArrayList<>();
        list.add(stringMap);
        // List 안의 Map 키와 값이 IntegerMap과 달라서 들어갈 수 없음
        // list.add(map1); -> 불가

        Map<String, Integer> stringMap2 = new HashMap<>();
        stringMap2.put("a", 1);
        stringMap2.put("e", 2);
        stringMap2.put("f", 3);
        // map의 key값은 중복될 수 없음
        // 하지만, 지금의 경우에는 키 값 중복 가능
        // : [{a=1, b=2, c=3}, {a=1, e=2, f=3}]와 같은 형태로 삽입되기 때문

        list.add(stringMap2);
        System.out.println(list);

    }
}

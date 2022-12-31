package com.streampractice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FruitOps {
        public void groupingByFruitName(List<Fruit> fruitList){
            Map<String, List<Fruit>>map = fruitList.stream().collect(
                Collectors.groupingBy(Fruit::getName));
            System.out.println("Map<String, List<Fruit>>map :" + map);
        }

        public void groupingByFruitNameWithCounting(List<Fruit> fruitList){
            System.out.println("groupingByFruitNameWithCounting");
            // Map<String, Long> map = fruitList.stream().map(Fruit::getName).
            // collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            Map<String, Long> map = fruitList.stream().collect(
                Collectors.groupingBy(
                    Fruit::getName,
                    Collectors.counting()
                ));
            System.out.println("Map<String, Long> : " + map);
        }

        public void groupingByFruitNameAndMapping(List<Fruit> fruitList){
            Map<String, List<Integer>> map = fruitList.stream().collect(
                Collectors.groupingBy(
                    Fruit::getName, 
                    Collectors.mapping(
                        Fruit::getPrice, 
                        Collectors.toList())
                    )
            );
            System.out.println("Map<String, List<Integer>> : " + map );
        }

}

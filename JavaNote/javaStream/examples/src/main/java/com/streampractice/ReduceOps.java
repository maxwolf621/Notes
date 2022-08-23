package com.streampractice;

import java.util.List;


public class ReduceOps {

        private static <T extends Integer> void printResut(T v){
            System.out.println("result :" + v);
        }

        public void reduce(List<Integer> l){
            Integer v = l.stream().reduce((x1, x2) -> x1 + x2).orElse(0);
            ReduceOps.printResut(v);
        }

        public void reduceWithInitializer(List<Integer> l,int initializer){
            Integer v = l.stream().reduce(initializer, (x1, x2) -> x1 + x2);
            ReduceOps.printResut(v);

        }
        public void reduceSequentialWithInitialize(List<Integer> l, int initializer){
            Integer v = l.stream().reduce(initializer,
            (x1, x2) -> {
                System.out.println("stream accumulator, x1: " + x1 + " x2: " + x2);
                return x1 - x2;
            },(x1, x2) -> {
                System.out.println("stream combiner, x1: " + x1 + " x2: " + x2);
                return x1 * x2;
            });
            ReduceOps.printResut(v);
        }
        
        public void reduceMultipleThreadsWithInitializer(List<Integer> l, int initializer){
            System.out.println("Multiple Threads ");
            Integer v = l.parallelStream().reduce(initializer,
            (x1, x2) -> {
                System.out.println("parallelStream accumulator: x1:" + x1 + " x2:" + x2);
                return x1 - x2;
            },
            (x1, x2) -> {
                System.out.println("parallelStream combiner: x1:" + x1 + " x2:" + x2);
                return x1 * x2;
            });
            ReduceOps.printResut(v);
        }
}

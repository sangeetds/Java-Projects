package com.cognitree.sangeet.interview;

import java.util.*;

public class FourthQuestion {
    public static void main(String[] args) {
        int[] input = { 60, 10, 50, 20, 40 };
        Queue<Integer> seq = new LinkedList<>();
        ArrayDeque<Integer> ans = new ArrayDeque<>();
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < input.length; i++) {
            map.put(input[i], i);
        }

        for (int i = input.length - 1; i >=0; i--) {
            while (!seq.isEmpty() && seq.peek() < input[i]) {
                seq.poll();
            }

            if (seq.isEmpty()) ans.addLast(i);
            else ans.addFirst(i);

            seq.add(input[i]);
        }

        while(!ans.isEmpty()) {
            System.out.println(ans.poll());
        }
    }
}


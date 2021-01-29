package com.cognitree.sangeet;

//import com.sun.tools.javac.util.Pair;

import java.util.Arrays;

public class ThirdQuestion {
    public static void main(String[] args) {
//        HashMap<String, Pair<Integer, String>> map = new HashMap<>();
        String[][] input = { { "0", "k1", "v1" }, { "1", "k2", "v2" }, { "2", "k1", "v3" },
                { "3", "k2", "v4" }, { "4", "k3", "v5" }, { "5", "k2", "v6" }, { "6", "k4", "v7" },
                {"7", "k5", "v8",}, { "8", "k5", "v9" }, { "9", "k1", "v10" }, { "10", "k6", "v11" } };

        for (String[] s: input) {
//            map.put(s[1], new Pair<Integer, String>(Integer.parseInt(s[0]), s[2]));
        }

        for (String[] s: input) {
//            if (map.containsKey(s[1]) && map.get(s[1]).fst == Integer.parseInt(s[0])) {
                System.out.println(Arrays.toString(s));
//            }
        }
    }
}





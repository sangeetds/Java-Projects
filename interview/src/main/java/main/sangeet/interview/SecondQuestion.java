package main.sangeet.interview;

public class SecondQuestion {
    public static void main(String[] args) {
        String str = "abababa";

        int position = recur(str, 0);

        if (position >= 0) {
            System.out.println("Non repeating first string is " + str.charAt(position));
        }
        else
            System.out.println("No non repeating character found");
    }

    private static int recur(String str, int i) {
        if (i >= str.length()) {
            return -1;
        }

        if (innerRecur(str, 0, str.charAt(i), i)) {
            return i;
        }
        else return recur(str, i + 1);
    }

    private static boolean innerRecur(String str, int index, char check, int initialIndex) {
        if (index >= str.length()) {
            return true;
        }

        if (str.charAt(index) == check && index != initialIndex) {
            return false;
        }

        return innerRecur(str, index + 1, check, initialIndex);
    }
}




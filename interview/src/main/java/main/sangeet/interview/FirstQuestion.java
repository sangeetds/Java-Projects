package main.sangeet.interview;

public class FirstQuestion {
    public static void main(String[] args) {
        int[] arr = { 3, 2, 1 };

        System.out.println(modifiedCheckSorted(arr));
        System.out.println(checkSorted(arr));
    }

    private static String modifiedCheckSorted(int[] arr) {
        if (arr.length <= 1) {
            return "Array is sorted in ascending order";
        }

        int desc = 1;
        int asc = 1;
        int i = 0;
        int len = arr.length;

        while ((asc == 1 || desc == 1) && i < len - 1) {
            if (arr[i] < arr[i+1]) {
                desc = 0;
            }
            else if (arr[i] > arr[i+1]) {
                asc = 0;
            }
            i++;
        }

        if (asc == 1) {
            return "Array is sorted in ascending order";
        }
        else if (desc == 1) {
            return "Array is sorted in descending order";
        }
        else
            return "Array is not sorted";
    }

    public static String checkSorted(int[] arr) {
        if (arr.length <= 1) {
            return "Array sorted in ascending";
        }

        int i = 0;
        int len = arr.length;
        int dir = Integer.compare(0, arr[0] - arr[len - 1]);

        while (i < len - 1) {
            if (dir == 0 && arr[i] == arr[i + 1]) {
                i++;
            }
            else if (dir == 1 && arr[i] <= arr[i + 1]) {
                i++;
            }
            else if (dir == -1 && arr[i] >= arr[i + 1]) {
                i++;
            }
            else return "Array is not sorted";
        }

        return "Array is sorted in " + ((dir == 1 || dir == 0) ? "ascending" : "descending") + " order";
    }
}

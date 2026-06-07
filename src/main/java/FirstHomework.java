import java.util.Arrays;

public class FirstHomework {
    public static void main(String[] args) {
        printThreeWords();
        checkSumSign();
        printColor();
        compareNumbers();
        checkSum (5, 100);
        numType (16);
        isPositiveOrNot (67);
        repeatString("String text", 10);
        isLeapYear (2020);
        invertArray();
        fillArray(22);
        doubleSmallNums();
        drawDiagonal (4);
        printArray (4, 9);
    }

    public static void printThreeWords() {
        System.out.println("Orange");
        System.out.println("Banana");
        System.out.println("Apple");
    }

    public static void checkSumSign() {
        int a = 5;
        int b = -9;
        if ((a + b) >= 0) {
            System.out.println("Сумма положительная");
        } else {
            System.out.println("Сумма отрицательная");
        }
    }

    public static void printColor() {
        int value = 22;
        if (value <= 0) {
            System.out.println("Красный");
        } else if (value > 0 && value <= 100) {
            System.out.println("Желтый");
        } else {
            System.out.println("Зеленый");
        }
    }

    public static void compareNumbers() {
         int a = 9;
         int b = 40;
         if ((a >= b)) {
             System.out.println("a >= b");
         } else {
             System.out.println("a < b");
         }
    }

    public static String checkSum (int a, int b) {
        int sum = a + b;
        if (sum >= 10 && sum <= 20) {
            return "Сумма в диапазоне";
        } else {
            return "Сумма вне диапазона";
        }
    }

    public static void numType (int number) {
        if (number >= 0) {
        System.out.println("Это положительное число");
        } else {
        System.out.println("Это отрицательное число");
        }
    }

    public static boolean isPositiveOrNot (int number) {
        if (number < 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void repeatString(String text, int times) {
        for (int i = 0; i < times; i++) {
            System.out.println(text);
        }
    }

    public static boolean isLeapYear (int year) {
        if (year % 4 != 0) {
            return false;
        }
        if (year % 100 == 0) {
            if (year % 400 == 0) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static void invertArray() {
        int[] numbers = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == 1) {
                numbers[i] = 0;
            } else {
                numbers[i] = 1;
            }
        }
        System.out.println("Новый массив: ");
        for (int n : numbers) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    public static void fillArray(int i) {
        int[] arr = new int[100];
        for (i = 0; i < 100; i++) {
            arr[i] = i + 1;
        }
        System.out.println("Полный массив: ");
        for (int number : arr) {
            System.out.print(number + " ");
        }
    }
    public static void doubleSmallNums() {
        int[] nums = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 6) {
                nums[i] *= 2;
            }
        }
        System.out.println("Итоговый массив: ");
        for (int n : nums) {
            System.out.print(n + " ");
        }
        System.out.println();
    }
    public static void drawDiagonal (int counter) {
        int [][] table = new int[counter][counter];
        for (int i = 0; i < counter; i++) {
            table [i][i] = 1;
        }
        for (int r = 0; r < counter; r ++) {
            for (int c = 0; c < counter; c++) {
                System.out.print(table[r][c] + " ");
            }
            System.out.println();
        }
    }
    public static void printArray (int len, int value) {
        int [] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr [i] = value;
        }
        System.out.println(Arrays.toString(arr));
    }
}

package homework_2_5;

public class ArrayProcessor {
    public static int calculateSum(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        final int REQUIRED_SIZE = 4;


        if (arr.length != REQUIRED_SIZE) {
            throw new MyArraySizeException("Неверный размер массива: кол-во строк должно быть равно " + REQUIRED_SIZE);
        }
        for (String[] row : arr) {
            if (row.length != REQUIRED_SIZE) {
                throw new MyArraySizeException("Неверный размер массива: кол-во столбцов в строке должно быть равно " + REQUIRED_SIZE);
            }
        }

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e) {

                    throw new MyArrayDataException(i, j);
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {

        String[][] validArray = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };


        String[][] invalidArray = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "abc", "12"}, // будет ошибка
                {"13", "14", "15", "16"}
        };

        System.out.println("Обработка валидного массива");
        try {
            int result = calculateSum(validArray);
            System.out.println("Сумма элементов: " + result);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        System.out.println("Обработка массива с неверными данными");
        try {
            int result = calculateSum(invalidArray);
            System.out.println("Сумма элементов: " + result);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        System.out.println("Пример с ArrayIndexOutOfBoundsException");
        try {
            int[] simpleArr = {1, 2, 3};
            // если обратиться к несуществующему индексу
            int value = simpleArr[5];
            System.out.println("Значение: " + value);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Поймано исключение ArrayIndexOutOfBoundsException: индекс за границами массива");
        }
    }
}

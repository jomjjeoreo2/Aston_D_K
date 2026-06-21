package homework_2_5;

public class MyArrayDataException extends Exception {
    private final int row;
    private final int col;

    public MyArrayDataException(int row, int col) {
        super(String.format("Недопустимые данные в ячейке [%d][%d]", row, col));
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
}
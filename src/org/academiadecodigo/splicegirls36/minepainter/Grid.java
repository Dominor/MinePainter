package org.academiadecodigo.splicegirls36.minepainter;

import org.academiadecodigo.simplegraphics.graphics.Canvas;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.io.*;


public class Grid implements Serializable {

    public static final int PADDING = 10;

    private Cell[][] matrix;
    private Rectangle rectangle;
    private int cellSize;
    private int columns;
    private int rows;
    private Painter painter;

    Grid (int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        cellSize = Grid.PADDING;
        matrix = new Cell[columns][rows];
        rectangle = new Rectangle(PADDING, PADDING, columnsToXPixels(columns), rowsToYPixels(rows));
        init();
    }

    private void initMatrix() {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = new Cell(i, j, this);
                matrix[i][j].show();
            }
        }
    }

    void init() {

        rectangle.draw();
        initMatrix();
        this.painter = new Painter(matrix[0][0], Color.GREEN);
    }

    void clear() {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j].hide();
                matrix[i][j].show();
            }
        }
    }

    void load() {
        FileInputStream file = null;
        DataInputStream in = null;
        String fileName = "./resources/gameState";
        try {
            file = new FileInputStream(fileName);
            in = new DataInputStream(file);

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {

                    if (in.readBoolean()) {
                        matrix[i][j].fill(Color.BLACK);
                    }
                }
            }

            int column = in.readInt();
            int row = in.readInt();

            painter.redraw(column, row, Color.GREEN);
            painter.setCurrentCell(getCell(column, row));
            System.out.println("Game loaded!!");
        } catch (IOException  e) {
            System.err.println(e.getMessage());
        } finally {
            cleanUp(in);
        }
    }

    void save() {
        FileOutputStream file = null;
        DataOutputStream out = null;
        String fileName = "./resources/gameState";
        try {
            file = new FileOutputStream(fileName);
            out = new DataOutputStream(file);

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {

                    out.writeBoolean(matrix[i][j].isPainted());
                    System.out.println(matrix[i][j].isPainted());
                }
            }

            out.writeInt(painter.getColumn());
            out.writeInt(painter.getRow());

            out.flush();

            System.out.println("Game saved!!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            cleanUp(out);
        }
    }

    private void cleanUp(InputStream file) {

        try {
            file.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void cleanUp(OutputStream file) {

        try {
            file.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /* Auxiliary methods
    ----------------------------------------------
     */

    public int rowToY (int row) {

        int result = PADDING + (row * cellSize);
        //System.out.println("Row: " + result);
        return PADDING + (row * cellSize);
    }

    public int columnToX (int column) {

        int result = PADDING + (column * cellSize);
        //System.out.println("Column: " + result);
        return PADDING + (column * cellSize);
    }

    public int columnsToXPixels(int columns) {
        return columns * cellSize;
    }

    public int rowsToYPixels(int rows) {

        return rows * cellSize;
    }

    /* ---------------------------------------- */

    public int getCellSize() {
        return cellSize;
    }

    public Cell getCell (int column, int row) {

        return matrix[column][row];
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
}

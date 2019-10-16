package org.academiadecodigo.splicegirls36.minepainter;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.io.Serializable;

public class Cell implements Serializable {

    private Grid parentGrid;
    private int column;
    private int row;
    private Rectangle rectangle;
    private boolean painted;

    Cell(int column, int row, Grid parentGrid) {
        this.parentGrid = parentGrid;
        this.column = column;
        this.row = row;
        this.rectangle = new Rectangle(parentGrid.columnToX(column), parentGrid.rowToY(row), parentGrid.getCellSize(), parentGrid.getCellSize());
        this.painted = false;
    }

    public void show () {

        rectangle.draw();
    }

    public void hide() {
        rectangle.delete();
        this.painted = false;
    }

    public void fill(Color color) {

        rectangle.setColor(color);
        rectangle.fill();
        this.painted = true;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Grid getParentGrid() {
        return parentGrid;
    }

    public boolean isPainted() {
        return painted;
    }

    public void setPainted(boolean painted) {
        this.painted = painted;
    }
}

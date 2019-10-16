package org.academiadecodigo.splicegirls36.minepainter;


import org.academiadecodigo.simplegraphics.graphics.*;
import org.academiadecodigo.simplegraphics.keyboard.*;

import java.io.Serializable;

public class Painter implements KeyboardHandler, Serializable {

    private Cell currentCell;
    private Rectangle rectangle;
    private Keyboard keyboard;

    Painter(Cell cell, Color color) {

        this.currentCell = cell;
        int column = cell.getColumn();
        int row = cell.getRow();
        Grid grid = cell.getParentGrid();
        rectangle = new Rectangle(grid.columnToX(column), grid.rowToY(row), grid.getCellSize(), grid.getCellSize());
        show(color);
        this.keyboard = new Keyboard(this);
        initKeyHandling();
    }

    private void initKeyHandling() {

        KeyboardEvent eventUP = new KeyboardEvent();
        KeyboardEvent eventDOWN = new KeyboardEvent();
        KeyboardEvent eventLEFT = new KeyboardEvent();
        KeyboardEvent eventRIGHT = new KeyboardEvent();
        KeyboardEvent eventSPACE = new KeyboardEvent();
        KeyboardEvent eventE = new KeyboardEvent();
        KeyboardEvent eventC = new KeyboardEvent();
        KeyboardEvent eventS = new KeyboardEvent();
        KeyboardEvent eventL = new KeyboardEvent();


        eventUP.setKey(KeyboardEvent.KEY_UP);
        eventDOWN.setKey(KeyboardEvent.KEY_DOWN);
        eventLEFT.setKey(KeyboardEvent.KEY_LEFT);
        eventRIGHT.setKey(KeyboardEvent.KEY_RIGHT);
        eventSPACE.setKey(KeyboardEvent.KEY_SPACE);
        eventE.setKey(KeyboardEvent.KEY_E);
        eventC.setKey(KeyboardEvent.KEY_C);
        eventS.setKey(KeyboardEvent.KEY_S);
        eventL.setKey(KeyboardEvent.KEY_L);

        eventUP.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        eventDOWN.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        eventLEFT.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        eventRIGHT.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        eventSPACE.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        eventE.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        eventC.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        eventS.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        eventL.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        keyboard.addEventListener(eventUP);
        keyboard.addEventListener(eventDOWN);
        keyboard.addEventListener(eventLEFT);
        keyboard.addEventListener(eventRIGHT);
        keyboard.addEventListener(eventSPACE);
        keyboard.addEventListener(eventE);
        keyboard.addEventListener(eventC);
        keyboard.addEventListener(eventS);
        keyboard.addEventListener(eventL);
    }

    public void show(Color color) {
        rectangle.setColor(color);
        rectangle.draw();
        rectangle.fill();
    }

    public void paint(Color color) {

        currentCell.fill(color);
    }

    public void decolorize() {

        currentCell.hide();
        currentCell.show();
    }

    public void redraw(int column, int row, Color color) {
        Grid grid = currentCell.getParentGrid();

        rectangle.translate(grid.columnToX(column - getColumn()), grid.rowToY(row - getRow()));
        //rectangle.delete();
        //rectangle = new Rectangle(grid.columnToX(column), grid.rowToY(row), grid.getCellSize(), grid.getCellSize());
        rectangle.setColor(color);
        rectangle.fill();
    }

    public Color getColor() {
        return rectangle.getColor();
    }

    public void moveIn(Direction direction) {

        int column = currentCell.getColumn();
        int row = currentCell.getRow();
        Grid grid = currentCell.getParentGrid();

        switch (direction) {

            case UP:
                if (row - 1 < 0) {
                    return;
                }
                currentCell = grid.getCell(column, row - 1);
                rectangle.translate(0, grid.rowsToYPixels(-1));
                break;
            case LEFT:
                if (column - 1 < 0) {
                    return;
                }
                currentCell = grid.getCell(column - 1, row);
                rectangle.translate(grid.columnsToXPixels(-1), 0);
                break;
            case RIGHT:
                if (column + 1 > grid.getColumns() - 1) {
                    return;
                }
                currentCell = grid.getCell(column + 1, row);
                rectangle.translate(grid.columnsToXPixels(1), 0);
                break;
            case DOWN:
                if (row + 1 > grid.getRows() - 1) {
                    return;
                }
                currentCell = grid.getCell(column, row + 1);
                rectangle.translate(0, grid.rowsToYPixels(1));
                break;
            default:
                throw new IllegalArgumentException("Illegal Direction.");
        }

    }


    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch(keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_UP:
                moveIn(Direction.UP);
                break;
            case KeyboardEvent.KEY_DOWN:
                moveIn(Direction.DOWN);
                break;
            case KeyboardEvent.KEY_LEFT:
                moveIn(Direction.LEFT);
                break;
            case KeyboardEvent.KEY_RIGHT:
                moveIn(Direction.RIGHT);
                break;
            case KeyboardEvent.KEY_SPACE:
                paint(Color.BLACK);
                break;
            case KeyboardEvent.KEY_E:
                decolorize();
                break;
            case KeyboardEvent.KEY_C:
                currentCell.getParentGrid().clear();
                break;
            case KeyboardEvent.KEY_S:
                currentCell.getParentGrid().save();
                break;
            case KeyboardEvent.KEY_L:
                System.out.println("L registered");
                currentCell.getParentGrid().load();
                break;
            default:
                throw new IllegalArgumentException("Illegal Direction");
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }

    public int getColumn() {
        return currentCell.getColumn();
    }

    public int getRow() {
        return currentCell.getRow();
    }

    public void setCurrentCell(Cell cell) {
        this.currentCell = cell;
    }
}
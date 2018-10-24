package com.thisis.adrianw.bingogame.Model;

import android.util.Log;

public class BingoBoard {
    //Current max board size
    private int boardSize = 5;
    //Parameters for min and max board size
    private static final int MAX_BOARD_SIZE = 5;
    private static final int MIN_BOARD_SIZE = 0;
    private BingoCell[][] cells = new BingoCell[boardSize][boardSize];

    //Public constructor taking param that will determine size of board (default is 5)
    public BingoBoard(int boardSize) {
        if (!checkForValidBordSize(boardSize)) {
            Log.e("BingoBoard", "Out of bound board size " + boardSize);
        } else {
            this.boardSize = boardSize;
            clearBingoBoard(boardSize);
        }
    }

    //Clearing board one cell at the time
    public void clearBingoBoard(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new BingoCell();
                Log.v("BingoBoard", "Value of this is ... " + cells[i][j].getValue());
            }
        }
    }

    private boolean checkForValidBordSize(int boardSize) {
        if (boardSize > MAX_BOARD_SIZE || boardSize < MIN_BOARD_SIZE) {
            return false;
        }
        return true;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    //Setting value for single cell in 2d array
    public Bingo mark(int x, int y) {
        Bingo bingo = null;
        if (cells[x][y].getValue() == Bingo.notBingo) {
            cells[x][y].setValue(Bingo.Bingo);
            bingo = Bingo.Bingo;
        } else if (cells[x][y].getValue() == Bingo.Bingo) {
            cells[x][y].setValue(Bingo.notBingo);
            bingo = Bingo.notBingo;
        } else {
            cells[x][y].setValue(Bingo.Bingo);
            bingo = Bingo.Bingo;
        }
        Log.v("BingoBoard", "Current value of bingo is ... " + String.valueOf(bingo));
        return bingo;
    }

    public Bingo getValue(int x, int y) {
        return cells[x][y].getValue();
    }

    public boolean isItBingoTable3x3(int currentRow, int currentCol) {
        Bingo bingo = Bingo.Bingo;
        return (cells[currentRow][0].getValue() == bingo
                && cells[currentRow][1].getValue() == bingo
                && cells[currentRow][2].getValue() == bingo
                || cells[0][currentCol].getValue() == bingo      // 3-in-the-column
                && cells[1][currentCol].getValue() == bingo
                && cells[2][currentCol].getValue() == bingo
                || currentRow == currentCol            // 3-in-the-diagonal
                && cells[0][0].getValue() == bingo
                && cells[1][1].getValue() == bingo
                && cells[2][2].getValue() == bingo
                || currentRow + currentCol == 2    // 3-in-the-opposite-diagonal
                && cells[0][2].getValue() == bingo
                && cells[1][1].getValue() == bingo
                && cells[2][0].getValue() == bingo);
    }
    public boolean isItBingoTable5x5(int currentRow, int currentCol) {
        Bingo bingo = Bingo.Bingo;
        return (cells[currentRow][0].getValue() == bingo
                && cells[currentRow][0].getValue() == bingo
                && cells[currentRow][1].getValue() == bingo
                && cells[currentRow][2].getValue() == bingo
                && cells[currentRow][3].getValue() == bingo
                && cells[currentRow][4].getValue() == bingo
                || cells[0][currentCol].getValue() == bingo      // 3-in-the-column
                && cells[1][currentCol].getValue() == bingo
                && cells[2][currentCol].getValue() == bingo
                && cells[3][currentCol].getValue() == bingo
                && cells[4][currentCol].getValue() == bingo
                || currentRow == currentCol            // 3-in-the-diagonal
                && cells[0][0].getValue() == bingo
                && cells[1][1].getValue() == bingo
                && cells[2][2].getValue() == bingo
                && cells[3][3].getValue() == bingo
                && cells[4][4].getValue() == bingo
                ||  // 3-in-the-opposite-diagonal
                cells[0][4].getValue() == bingo
                && cells[1][3].getValue() == bingo
                && cells[2][2].getValue() == bingo
                && cells[3][1].getValue() == bingo
                && cells[4][0].getValue() == bingo);
    }
}

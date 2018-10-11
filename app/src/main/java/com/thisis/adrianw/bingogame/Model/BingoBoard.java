package com.thisis.adrianw.bingogame.Model;

import android.util.Log;

public class BingoBoard {
    //Current max board size
    private int boardSize=5;
    //Parameters for min and max board size
    private static final int MAX_BOARD_SIZE = 5;
    private static final int MIN_BOARD_SIZE = 0;
    private BingoCell[][] cells = new BingoCell[boardSize][boardSize];

    //Public constructor taking param that will determine size of board (default is 5)
    public BingoBoard (int boardSize) {
        if (!checkForValidBordSize(boardSize)) {
            Log.e("BingoBoard", "Out of bound board size " + boardSize);
        }
        else {
            this.boardSize = boardSize;
            clearBingoBoard(boardSize);
        }
    }

    //Clearing board one cell at the time
    private void clearBingoBoard(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new BingoCell();
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

}

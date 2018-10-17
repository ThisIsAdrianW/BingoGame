package com.thisis.adrianw.bingogame;
import android.app.Application;
import android.util.Log;

import com.thisis.adrianw.bingogame.Bingodata.BingoRepository;
import com.thisis.adrianw.bingogame.Model.Bingo;
import com.thisis.adrianw.bingogame.Model.BingoBoard;
import androidx.databinding.ObservableArrayMap;
import androidx.lifecycle.AndroidViewModel;


public class GameViewModel extends AndroidViewModel{
    String testString = "Default text for tests";
    public final ObservableArrayMap<String, String> cells = new ObservableArrayMap<>();
    private BingoBoard model;
    private int boardSize=5;
    private BingoRepository bingoRepository;

    public GameViewModel (Application application) {
        super(application);
        bingoRepository = new BingoRepository(application);
        model = new BingoBoard(boardSize);
    }
    public void markBingo(int row, int col) {
        Bingo cell = model.mark(row, col);
        cells.put("" + row + col, cell == null ? null : cell.toString());
        Boolean winner = model.isItBingoTable3x3(row, col);
        Log.v("ViewBoard", "This is ... " + winner);
    }
    public void clearCells() {
        cells.clear();
    }
}

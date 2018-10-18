package com.thisis.adrianw.bingogame;
import android.app.Application;
import android.util.Log;

import com.thisis.adrianw.bingogame.Bingodata.BingoRepository;
import com.thisis.adrianw.bingogame.Bingodata.IndexWord;
import com.thisis.adrianw.bingogame.Bingodata.Words;
import com.thisis.adrianw.bingogame.Model.Bingo;
import com.thisis.adrianw.bingogame.Model.BingoBoard;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.databinding.ObservableArrayMap;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class GameViewModel extends AndroidViewModel{
    String testString = "Default text for tests";
    public final ObservableArrayMap<String, String> cells = new ObservableArrayMap<>();
    private BingoBoard model;
    private int boardSize=5;
    List<IndexWord> myIndexWords;
    private List<Words> words;
    private LiveData<List<IndexWord>> liveIndex;
    private BingoRepository bingoRepository;

    public GameViewModel (Application application) {
        super(application);
        bingoRepository = new BingoRepository(application);
        model = new BingoBoard(boardSize);
        myIndexWords = bingoRepository.getAllIndexWords();
        liveIndex = bingoRepository.returnLiveIndex();

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

    public void inserBingoWord (Words word) {
        bingoRepository.insertBingoWord(word);
    }

    public void insertBingoIndex (IndexWord indexWord) {
        bingoRepository.insertIndex(indexWord);
    }

    public List<IndexWord> returnIndexWords() {
        return myIndexWords;
    }

    public List<IndexWord> returnIndexAsync() {
        List<IndexWord> myIndexList = bingoRepository.getAllIndexWordsAsync();
        return myIndexList;
    }

    public List<Words> returnBingoWords(String indexWord) {
        List<Words> bingoList = null;
        try {
             bingoList = bingoRepository.getAllWords(indexWord);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bingoList;
    }
}

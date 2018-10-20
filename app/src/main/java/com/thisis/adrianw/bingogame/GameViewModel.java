package com.thisis.adrianw.bingogame;
import android.app.Application;
import android.util.Log;

import com.thisis.adrianw.bingogame.Bingodata.BingoRepository;
import com.thisis.adrianw.bingogame.Bingodata.IndexWord;
import com.thisis.adrianw.bingogame.Bingodata.Words;
import com.thisis.adrianw.bingogame.Model.Bingo;
import com.thisis.adrianw.bingogame.Model.BingoBoard;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayMap;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class GameViewModel extends AndroidViewModel{
    String testString = "Default text for tests";
    public final ObservableArrayMap<String, String> cells = new ObservableArrayMap<>();
    private BingoBoard model;
    private int boardSize=5;
    private int currentListSize;
    private static final int MIN_REQ_FIELDS = 9;
    private List<IndexWord> myIndexWords;
    private List<Words> words;
    private List<String> stringList = new ArrayList<String>();
    private LiveData<List<IndexWord>> liveIndex;
    private BingoRepository bingoRepository;
    private MutableLiveData<String> stringMutableLiveData = new MutableLiveData<String>();

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
        return bingoRepository.getAllIndexWordsAsync();
    }

    public List<Words> returnBingoWords(String indexWord) {
            return bingoRepository.getAllWords(indexWord);
    }

    public LiveData<List<IndexWord>> getLiveIndex() {
        return liveIndex;
    }

    public MutableLiveData<String> getStringMutableLiveData() {
        return stringMutableLiveData;
    }

    public void setMutableLiveDataString(String string) {
        stringMutableLiveData.postValue(string);
    }

    public String getMutableIndex() {
        return stringMutableLiveData.getValue();
    }

    public List<Words> getBingoWords(String string) {
        return bingoRepository.getAllWords(string);
    }

    public int wordListSize(String string) {
        return bingoRepository.indexNumber(string);
    }
    public void updateWordList() {
        if (stringMutableLiveData.getValue()!=null && !stringMutableLiveData.getValue().isEmpty())
        {
            words = getBingoWords(stringMutableLiveData.getValue());
            currentListSize = wordListSize(stringMutableLiveData.getValue());
            if (currentListSize >= MIN_REQ_FIELDS) {
                updateStringList(stringList, words);
            }
            Log.v("GameViewModel", "Values updated " + currentListSize);
        }
    }

    public void updateStringList(List<String> listOfStrings, List<Words> listOfWords) {
        for (int i = 0; i < listOfWords.size(); i++) {
            String newValue = listOfWords.get(i).getWordForBingo();
            Log.v("updatingStringList", "ListString number" + i +" value is " + newValue + "and word list its " + listOfWords.get(i).getWordForBingo());
            listOfStrings.add(i, newValue);
        }
    }

    public List<String> returnStringList () {
        Log.v("returning this bullshit", stringList.get(0));
        return stringList;
    }


}

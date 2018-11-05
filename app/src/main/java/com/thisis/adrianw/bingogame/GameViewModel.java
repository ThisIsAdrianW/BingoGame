package com.thisis.adrianw.bingogame;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thisis.adrianw.bingogame.Bingodata.BingoRepository;
import com.thisis.adrianw.bingogame.Bingodata.IndexWord;
import com.thisis.adrianw.bingogame.Bingodata.Words;
import com.thisis.adrianw.bingogame.Model.Bingo;
import com.thisis.adrianw.bingogame.Model.BingoBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.databinding.ObservableArrayMap;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class GameViewModel extends AndroidViewModel {
    String testString = "Default text for tests";
    public final ObservableArrayMap<String, String> cells = new ObservableArrayMap<>();
    private BingoBoard model;
    private int boardSize = 5;
    private int currentListSize;
    private int currentBoardModel;
    private Uri imageUri;
    private List<Words> randomWordList;
    private int randomBingoInt;
    private static final int MIN_REQ_FIELDS = 9;
    private List<IndexWord> myIndexWords;
    private List<Words> words;
    private List<String> stringList = new ArrayList<String>();
    private LiveData<List<IndexWord>> liveIndex;
    private BingoRepository bingoRepository;
    private MutableLiveData<String> stringMutableLiveData = new MutableLiveData<String>();
    private MutableLiveData<Bingo> bingoScore = new MutableLiveData<Bingo>();
    Toast toast;

    public GameViewModel(Application application) {
        super(application);
        bingoRepository = new BingoRepository(application);
        model = new BingoBoard(boardSize);
        myIndexWords = bingoRepository.getAllIndexWords();
        liveIndex = bingoRepository.returnLiveIndex();
        toast = null;

    }

    public void markBingo(int row, int col) {
        Bingo cell = model.mark(row, col);
        cells.put("" + row + col, cell == null ? null : cell.toString());
        if (currentBoardModel == 9) {
            if (model.isItBingoTable3x3(row, col)) {
                bingoScore.postValue(Bingo.Bingo);
            }
        } else if (currentBoardModel == 24) {
            if (model.getValue(2, 2) != Bingo.Bingo) {
                markBingo(2, 2);
            }
            if (model.isItBingoTable5x5(row, col)) {
                bingoScore.setValue(Bingo.Bingo);
            }
        }
    }

    public void clearCells() {
        cells.clear();
        cells.size();
    }

    public void inserBingoWord(Words word) {
        bingoRepository.insertBingoWord(word);
    }

    public void insertBingoIndex(IndexWord indexWord) {
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
        if (stringMutableLiveData.getValue() != null && !stringMutableLiveData.getValue().isEmpty()) {
            words = getBingoWords(stringMutableLiveData.getValue());
            currentListSize = wordListSize(stringMutableLiveData.getValue());
            if (currentListSize >= MIN_REQ_FIELDS) {
                updateStringList(stringList, words);
            }
        }
    }

    public void updateStringList(List<String> listOfStrings, List<Words> listOfWords) {
        listOfStrings.clear();
        for (int i = 0; i < listOfWords.size(); i++) {
            String newValue = listOfWords.get(i).getWordForBingo();
            listOfStrings.add(i, newValue);
        }
    }

    public List<String> returnStringList() {
        return stringList;
    }

    public void deleteIndex(IndexWord indexWord) {
        bingoRepository.deleteIndex(indexWord);
    }

    public int getCurrentBoardModel() {
        return currentBoardModel;
    }

    public void setCurrentBoardModel(int currentBoardModel) {
        this.currentBoardModel = currentBoardModel;
    }

    public List<Words> getAllWordsRandom(String index) {
        return bingoRepository.getAllWordsRandom(index);

    }

    public MutableLiveData<Bingo> getBingoScore() {
        return bingoScore;
    }

    public void setBingoScore(Bingo bingo) {
        this.bingoScore.postValue(bingo);
    }

    public void cleanBingoBoard() {
        model.clearBingoBoard(boardSize);
        clearCells();
        bingoScore.postValue(Bingo.notBingo);
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public void updateIndexWord(String indexWord, String newTitle) {
        bingoRepository.updateIndexWord(indexWord, newTitle);
    }

    public void updateWord(Words words) {
        bingoRepository.updateWord(words);
    }

    public void deleteWord(Words words) {
        bingoRepository.deleteWord(words);
    }

    public List<Words> returnAllWordsStandard(String string) {
        return bingoRepository.getAllWordsStandard(string);
    }

    public void showToast(Activity activity, String message, Drawable drawable) {
        if (message.equals(String.valueOf(Bingo.Bingo))) {
            toast = null;
        }
        if (toast != null) {
            toast.cancel();
            toast = null;
        } else {
            LayoutInflater inflater = activity.getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast, (ViewGroup) activity.findViewById(R.id.toast_root));
            ImageView imageView = layout.findViewById(R.id.image_fot_toast);
            if (drawable == null) {
                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_action_save));
            } else {
                imageView.setImageDrawable(drawable);
            }
            toast = new Toast(activity.getApplicationContext());
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            TextView text = (TextView) layout.findViewById(R.id.text_for_toast);
            text.setText(String.valueOf(message));
            toast.show();
        }
    }

    public void cancelToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }

    public void prepareBingoNumbers () {
        List<Words> listForGame = null;
        if (getMutableIndex()!=null && !getMutableIndex().trim().isEmpty()) {
            String title = getMutableIndex().trim();
             randomWordList = getAllWordsRandom(title);
        }
    }

    public int getRandomBingoInt() {
        return randomBingoInt;
    }

    public void setRandomBingoInt(int randomBingoInt) {
        this.randomBingoInt = randomBingoInt;
    }

    public String prepareRandomWord() {
        String toReturn = null;
        if (randomWordList!=null && getRandomBingoInt()<=randomWordList.size()) {
            int current = getRandomBingoInt();
            toReturn = randomWordList.get(current).getWordForBingo();
            current++;
            setRandomBingoInt(current);
        }
        else if (randomWordList==null || getRandomBingoInt()>randomWordList.size()) {
            setRandomBingoInt(0);
        }
        return toReturn;
    }

}

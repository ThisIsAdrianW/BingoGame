package com.thisis.adrianw.bingogame.Bingodata;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BingoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIndex(IndexWord indexWord);

    @Insert
    void inserWord(Words words);

    @Insert
    void insertWords(Words words);

    @Insert
    void insertList(List<Words> listOfWords);

    @Delete
    void delete(IndexWord indexWord);

    @Query("SELECT * FROM words WHERE indexforword=:indexWord ORDER BY RANDOM() LIMIT 24")
    List<Words> getWordsList(final String indexWord);

    @Query("SELECT count(*) from words WHERE indexforword=:numOfWords")
    int numberOfWordsForIndex(final String numOfWords);

    @Query("SELECT * FROM indexword ORDER BY indexforwords COLLATE UNICODE")
    List<IndexWord> selectAllIndexWords();

    @Query("SELECT * FROM indexword ORDER BY indexforwords COLLATE UNICODE")
    LiveData<List<IndexWord>> liveIndex();

    @Update
    void updateWord(Words words);

    @Update
    void updateIndexWord(IndexWord indexWord);


}

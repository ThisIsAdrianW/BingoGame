package com.thisis.adrianw.bingogame.Bingodata;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BingoDao {
    @Insert
    void insertIndex(IndexWord indexWord);

    @Insert
    void inserWord(Words words);

    @Insert
    void insertWords(Words... words);

    @Insert
    void insertList(List<Words> listOfWords);

    @Delete
    void delete(IndexWord indexWord);

    @Query("SELECT * FROM words WHERE indexforword=:indexWord ORDER BY RANDOM() LIMIT 1")
    List<Words> getWordsList(final String indexWord);

    @Query("SELECT count(*) from words WHERE indexforword=:numOfWords")
    int numberOfWordsForIndex(final String numOfWords);

    @Query("Select * from indexword ORDER BY indexforwords COLLATE UNICODE")
    List<IndexWord> selectAllIndexWords();

}

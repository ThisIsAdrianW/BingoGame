package com.thisis.adrianw.bingogame.Bingodata;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = IndexWord.class,
        parentColumns = "indexforwords",
        childColumns = "indexforword",
        onDelete = CASCADE), indices = @Index(value = {"indexforword"}))
public class Words {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String indexforword;
    private String wordForBingo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIndexforword() {
        return indexforword;
    }

    public void setIndexforword(String indexforword) {
        this.indexforword = indexforword;
    }

    public String getWordForBingo() {
        return wordForBingo;
    }

    public void setWordForBingo(String wordForBingo) {
        this.wordForBingo = wordForBingo;
    }
}

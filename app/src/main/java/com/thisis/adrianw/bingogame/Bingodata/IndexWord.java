package com.thisis.adrianw.bingogame.Bingodata;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"indexforwords"}, unique = true)})
public class IndexWord {
    @PrimaryKey
    @NonNull
    private String indexforwords;

    @NonNull
    public String getIndexforwords() {
        return indexforwords;
    }

    public void setIndexforwords(@NonNull String indexforwords) {
        this.indexforwords = indexforwords;
    }

    @Override
    public String toString() {
        return indexforwords;
    }
}

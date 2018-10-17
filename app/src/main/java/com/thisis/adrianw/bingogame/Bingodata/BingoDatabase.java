package com.thisis.adrianw.bingogame.Bingodata;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Words.class, IndexWord.class}, version = 1, exportSchema = false)
public abstract class BingoDatabase extends RoomDatabase {
    private static volatile BingoDatabase INSTANCE;
    public abstract BingoDao bingoDao();
    static BingoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BingoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BingoDatabase.class, "bingo_database")
                            //.allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

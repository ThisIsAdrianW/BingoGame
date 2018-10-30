package com.thisis.adrianw.bingogame.Bingodata;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class BingoRepository {
    private BingoDao bingoDao;
    private List<Words> allWords;
    private List<IndexWord> allIndexWords;
    private LiveData<List<IndexWord>> liveIndex;

    public BingoRepository(Application application) {
        BingoDatabase db = BingoDatabase.getDatabase(application);
        bingoDao = db.bingoDao();
        allIndexWords = getAllIndexWordsAsync();
        liveIndex = bingoDao.liveIndex();
    }

    public LiveData<List<IndexWord>> returnLiveIndex() {
        return liveIndex;
    }

    public List<IndexWord> getAllIndexWords() {
        return allIndexWords;
    }

    public void insertIndex(IndexWord indexWord) {
        new insertIndexAsyncTask(bingoDao).execute(indexWord);
    }

    //Async task for inserting index word
    private static class insertIndexAsyncTask extends AsyncTask<IndexWord, Void, Void> {
        private BingoDao AsyncTaskDao;

        insertIndexAsyncTask(BingoDao dao) {
            AsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final IndexWord... params) {
            AsyncTaskDao.insertIndex(params[0]);
            return null;
        }
    }

    //Async task for inserting word for Bingo
    public void insertBingoWord(Words words) {
        new insertBingoWordAsyncTask(bingoDao).execute(words);
    }

    private static class insertBingoWordAsyncTask extends AsyncTask<Words, Void, Void> {
        private BingoDao AsyncTaskDao;

        insertBingoWordAsyncTask(BingoDao dao) {
            AsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Words... words) {
            AsyncTaskDao.insertWords(words[0]);
            return null;
        }
    }

    //Insert list of words for game
    public void insertListOfWords(List<Words> list) {
        new insertListOfWordsAsyncTask(bingoDao).execute(list);

    }

    private static class insertListOfWordsAsyncTask extends AsyncTask<List<Words>, Void, Void> {
        private BingoDao AsyncTaskDao;

        insertListOfWordsAsyncTask(BingoDao dao) {
            AsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(List<Words>... lists) {
            AsyncTaskDao.insertList(lists[0]);
            return null;
        }
    }

    //Delete index word and all of it`s words
    public void deleteIndex(IndexWord indexWord) {
        new deleteIndexAsyncTask(bingoDao).execute(indexWord);
    }

    private static class deleteIndexAsyncTask extends AsyncTask<IndexWord, Void, Void> {
        private BingoDao AsyncTaskDao;

        deleteIndexAsyncTask(BingoDao dao) {
            AsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(IndexWord... indexWords) {
            AsyncTaskDao.delete(indexWords[0]);
            return null;
        }
    }

    //Method for checking how many Words are assigned to Index
    public int indexNumber(String title) {
        try {
            return new indexNumberAsyncTask(bingoDao).execute(title).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static class indexNumberAsyncTask extends AsyncTask<String, Void, Integer> {
        private BingoDao AsyncTaskDao;

        indexNumberAsyncTask(BingoDao dao) {
            AsyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final String... params) {
            return AsyncTaskDao.numberOfWordsForIndex(params[0]);
        }
    }

    public List<Words> getAllWords(String indexword) {
        try {
            return new getAllWordsAsyncTask(bingoDao).execute(indexword).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class getAllWordsAsyncTask extends AsyncTask<String, Void, List<Words>> {
        private BingoDao AsyncTaskDao;

        getAllWordsAsyncTask(BingoDao dao) {
            AsyncTaskDao = dao;
        }

        @Override
        protected List<Words> doInBackground(final String... params) {
            return AsyncTaskDao.getWordsList(params[0]);
        }
    }

    public List<IndexWord> getAllIndexWordsAsync() {
        try {
            return new getAllIndexAsyncTask(bingoDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class getAllIndexAsyncTask extends AsyncTask<Void, Void, List<IndexWord>> {
        private BingoDao AsyncTaskDao;

        getAllIndexAsyncTask(BingoDao dao) {
            AsyncTaskDao = dao;
        }

        @Override
        protected List<IndexWord> doInBackground(Void... voids) {
            List<IndexWord> allIndex = AsyncTaskDao.selectAllIndexWords();
            return allIndex;
        }
    }

    public void updateWord(Words words) {
        new updateWordAsyncTask(bingoDao).execute(words);
    }
    private static class updateWordAsyncTask extends AsyncTask<Words, Void, Void> {
        private BingoDao AsyncTaskDao;

        updateWordAsyncTask(BingoDao dao) {
            AsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Words... words) {
            AsyncTaskDao.updateWord(words[0]);
            return null;
        }
    }

    public void updateIndexWord(String indexWord, String newIndex) {
        new updateIndexWordAsyncTask(bingoDao).execute(indexWord, newIndex);
    }
    private static class updateIndexWordAsyncTask extends AsyncTask<String, Void, Void> {
        private BingoDao AsyncTaskDao;

        updateIndexWordAsyncTask(BingoDao dao) {
            AsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final String... strings) {
            String indexWord = (String) strings[0];
            String string = (String) strings[1];
            AsyncTaskDao.updateIndexWords(indexWord, string);
            return null;
        }
    }


}

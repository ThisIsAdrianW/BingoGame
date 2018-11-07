package com.thisis.adrianw.bingogame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.thisis.adrianw.bingogame.Model.Bingo;
import com.thisis.adrianw.bingogame.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    GameViewModel gameViewModel;
    Fragment fragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        final ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        binding.setLifecycleOwner(MainActivity.this);
        gameViewModel = ViewModelProviders.of(MainActivity.this).get(GameViewModel.class);
        if (savedInstanceState == null) {
            listFragment();
        }
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fragmentManager.getBackStackEntryCount() == 0) finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameViewModel = ViewModelProviders.of(MainActivity.this).get(GameViewModel.class);
        gameViewModel.cancelToast();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem clearItem = menu.findItem(R.id.clearBoard);
        MenuItem changeItem = menu.findItem(R.id.changeBoard);
        MenuItem randomValue = menu.findItem(R.id.menuBingoGenerator);
        clearItem.setVisible(false);
        changeItem.setVisible(false);
        randomValue.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_words:
                addWords();
                return true;
            case R.id.chooseList:
                gameViewModel.cleanBingoBoard();
                listFragment();
                return true;
            case R.id.clearBoard:
                gameViewModel.cleanBingoBoard();
                return true;
            case R.id.changeBoard:
                changeBoards();
                gameViewModel.cleanBingoBoard();
                return true;
            case R.id.helpandAbout:
                helpAndAbout();
                return true;
            case R.id.menuBingoGenerator:
                gameViewModel.setVisibilityFab(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void changeBoards() {
        if (gameViewModel.getCurrentBoardModel() == 9) {
            fragment = new MapFive();
            gameViewModel.setBingoScore(Bingo.notBingo);
        } else {
            fragment = new MapThree();
            gameViewModel.setBingoScore(Bingo.notBingo);
        }
        changeFragments();
    }

    public void addWords() {
        fragment = new AddWords();
        changeFragments();
    }

    public void listFragment() {
        fragment = new ListFragment();
        changeFragments();
    }

    public void helpAndAbout() {
        fragment = new HelpAndAbout();
        changeFragments();
    }

    private void changeFragments() {
        if (fragment!=null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout,fragment);
            if (fragment instanceof MapThree || fragment instanceof MapFive) {
                transaction.addToBackStack(null);
            }
            transaction.commit();
        }
    }

}

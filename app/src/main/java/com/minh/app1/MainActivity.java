package com.minh.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.book_menu:
                Intent intent_book = new Intent(MainActivity.this,BookActivity.class);
                startActivity(intent_book);
                return true;
            case R.id.author_menu:
                Intent intent_author = new Intent(MainActivity.this,AuthorActivity.class);
                startActivity(intent_author);
                return true;
            case R.id.searchBookByIdAuthor:
                Intent intent_find = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent_find);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

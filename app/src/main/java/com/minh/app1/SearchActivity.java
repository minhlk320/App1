package com.minh.app1;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    Button btn_exit, btn_find;
    EditText edt_IDauthor;
    GridView gridView;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Map();

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String []id ={edt_IDauthor.getText().toString()};
                ArrayList<String> list_string = new ArrayList<>();
                String uri = "content://book/bookdata";
                Uri book = Uri.parse(uri);
                Cursor cursor = getContentResolver().query(book, null,"id_author=?",id, "title");
                if (cursor != null) {
                    cursor.moveToFirst();
                    do {
                        try{
                            list_string.add(cursor.getInt(0) + "");
                            list_string.add(cursor.getString(1) + "");

                        }catch(Exception e){
                            list_string.add("Không có dữ liệu");
                        }

                    } while (cursor.moveToNext());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this,
                            android.R.layout.simple_list_item_1, list_string);
                    gridView.setAdapter(adapter);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Không có kết quả !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Map(){
        btn_exit = findViewById(R.id.button_exit);
        btn_find = findViewById(R.id.button_find);

        edt_IDauthor = findViewById(R.id.editText_maso);
        gridView = findViewById(R.id.gridView_display);
    }
}

package com.minh.app1;

import android.content.ContentValues;
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

public class BookActivity extends AppCompatActivity {
    EditText editText_maso, editText_tieude, editText_masotacgia;
    Button button_select, button_save, button_update, button_delete, button_exit;
    GridView gridView_display;
    DBHelper dbHelper;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        mapview();

        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] id ={editText_maso.getText().toString()};
                ArrayList<String> list_string = new ArrayList<>();
                String uri = "content://book/bookdata";
                Uri book = Uri.parse(uri);

                if(!editText_maso.getText().toString().isEmpty()){
                    Cursor cursor = getContentResolver().query(book, null,"id_book=?",id, "title");
                    if (cursor != null) {
                        cursor.moveToFirst();
                        do {
                            try{
                                list_string.add(cursor.getInt(0) + "");
                                list_string.add(cursor.getString(1) + "");
                                list_string.add(cursor.getInt(2) + "");
                            }catch(Exception e){
                                list_string.add("Không có dữ liệu");
                            }
                        } while (cursor.moveToNext());
                        adapter = new ArrayAdapter<String>(BookActivity.this,
                                android.R.layout.simple_list_item_1, list_string);
                        gridView_display.setAdapter(adapter);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Không có kết quả !", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (editText_maso.getText().toString().isEmpty()){
                    Cursor cursor = getContentResolver().query(book, null,null, null, "title");
                    if (cursor != null) {
                        cursor.moveToFirst();
                        do {
                            try{
                                list_string.add(cursor.getInt(0) + "");
                                list_string.add(cursor.getString(1) + "");
                                list_string.add(cursor.getInt(2) + "");
                            }catch(Exception e){
                                list_string.add("Không có dữ liệu");
                            }

                        } while (cursor.moveToNext());
                        adapter = new ArrayAdapter<String>(BookActivity.this,
                                android.R.layout.simple_list_item_1, list_string);
                        gridView_display.setAdapter(adapter);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Không có kết quả !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("id_book", editText_maso.getText().toString());
                values.put("title", editText_tieude.getText().toString());
                values.put("id_author", editText_masotacgia.getText().toString());
                String uri = "content://book/bookdata";
                Uri book = Uri.parse(uri);
                Uri insert_uri = getContentResolver().insert(book, values);
                Toast.makeText(getApplicationContext(), "Lưu thành công !", Toast.LENGTH_SHORT).show();
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String []id = {editText_maso.getText().toString()};
                String uri = "content://book/bookdata";
                Uri book = Uri.parse(uri);
                int i = getContentResolver().delete(book,"id_book=?",id);
                if(i>0) {
                    Toast.makeText(getApplicationContext(), "Xoa thành công !", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Xoa that bai !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("id_book", editText_maso.getText().toString());
                values.put("title", editText_tieude.getText().toString());
                values.put("id_author", editText_masotacgia.getText().toString());
                String uri = "content://book/bookdata";
                Uri book = Uri.parse(uri);
                int update_uri = getContentResolver().update(book,values,"id_book=?",new String[]{editText_maso.getText().toString()});
                if(update_uri>0)
                    Toast.makeText(getApplicationContext(), "Cap nhat thành công !", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Cap nhat that bai!", Toast.LENGTH_SHORT).show();
            }
        });




    }

    public void mapview(){
        editText_maso = (EditText) findViewById(R.id.editText_maso);
        editText_tieude = (EditText) findViewById(R.id.editText_tieude);
        editText_masotacgia = (EditText) findViewById(R.id.editText_masotacgia);

        button_select = (Button) findViewById(R.id.button_select);
        button_save = (Button) findViewById(R.id.button_save);
        button_update = (Button) findViewById(R.id.button_update);
        button_delete = (Button) findViewById(R.id.button_delete);
        button_exit = (Button) findViewById(R.id.button_exit);

        gridView_display = (GridView) findViewById(R.id.gridView_display);
        dbHelper = new DBHelper(BookActivity.this);
    }
}

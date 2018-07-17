package com.blogspot.tiaotone.simplenotepad2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private SQLiteDatabase db = null;

    final static String table_name = "table01";
    final static String create_table = "CREATE TABLE " + table_name + "(_id INTEGER PRIMARY KEY, title TEXT, text TEXT ,bgColor TEXT)";
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById (R.id.listView);

        db = openOrCreateDatabase("db1.db",MODE_PRIVATE,null);
        try{
            db.execSQL(create_table);
        }catch (Exception e){
            e.printStackTrace();
        }
        cursor = getAll();
        UpdateAdapter(cursor);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setItems(new String[]{"刪除", "修改", "取消"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0){
                                    cursor.moveToPosition(position);
                                    cursor.getInt(0);
                                    db.delete("table01","_id="+cursor.getInt(0),null);
                                    cursor = getAll();
                                    UpdateAdapter(cursor);
                                }
                                else if (which ==1){
                                    cursor.moveToPosition(position);
                                    int c = cursor.getInt(0);
                                    Intent intent = new Intent(MainActivity.this,UpdateActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("key1",c);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                                else {

                                }
                            }
                        }).show();
            }
        });
    }

    private void UpdateAdapter(Cursor cursor) {
        if(cursor != null && cursor.getCount() >= 0){
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.item_view,cursor,new String[]{"title","text"},new int[]{android.R.id.text1,android.R.id.text2});
            listView.setAdapter(adapter);
        }
    }

    private Cursor getAll() {
        Cursor cursor = db.rawQuery("SELECT * FROM table01",null);
        return cursor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.add:
                Intent intent = new Intent(MainActivity.this,InsertActivity.class);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}

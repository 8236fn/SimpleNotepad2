package com.blogspot.tiaotone.simplenotepad2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class InsertActivity extends AppCompatActivity {
    private Button btn_ok;
    private EditText edt_title;
    private EditText edt_content;

    SQLiteDatabase db = null;
    final static String db_name = "db1.db";
    final static String table_name = "table01";
    final static String create_table = "CREATE TABLE " + table_name + "(_id INTEGER PRIMARY KEY, title TEXT, text TEXT)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        edt_title = (EditText) findViewById (R.id.edt_title);
        edt_content = (EditText) findViewById (R.id.edt_content);
        btn_ok = (Button) findViewById (R.id.btn_ok);

        db = openOrCreateDatabase("db1.db",MODE_PRIVATE, null);
        try{
            db.execSQL(create_table);
        }catch (Exception e){
            e.printStackTrace();
        }
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_title.getText().toString().equals("")){
                    Calendar calendar = Calendar.getInstance();
                    String now = ""+calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DATE)+" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
                    edt_title.setText(now);
                }
                if(edt_content.getText().toString().equals("")){
                    edt_content.setText("無輸入");
                }
                ContentValues cv = new ContentValues();
                cv.put("title", edt_title.getText().toString());
                cv.put("text", edt_content.getText().toString());
                db.insert("table01",null,cv);
                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.insert,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case  R.id.clear:
                edt_title.setText("");
                edt_content.setText("");
                break;
            case R.id.back:
                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}

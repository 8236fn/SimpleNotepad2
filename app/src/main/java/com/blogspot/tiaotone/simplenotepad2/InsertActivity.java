package com.blogspot.tiaotone.simplenotepad2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class InsertActivity extends AppCompatActivity {
    private Button btn_ok;
    private EditText edt_title;
    private EditText edt_content;
    Spinner sp_color;
    ColorAdapter adapter;
    String selected_color;



    SQLiteDatabase db = null;
    final static String table_name = "table01";
    final static String create_table = "CREATE TABLE " + table_name + "(_id INTEGER PRIMARY KEY, title TEXT, text TEXT, bgColor TEXT)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        initView();

        edt_title = findViewById (R.id.edt_title);
        edt_content = findViewById (R.id.edt_content);
        btn_ok = findViewById (R.id.btn_ok);


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
                cv.put("bgColor",selected_color.getBytes().toString());
                db.insert("table01",null,cv);
                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        sp_color = findViewById(R.id.sp_color);
        final ArrayList<ItemData> color_list = new ArrayList<ItemData>();
        color_list.add(new ItemData("bright_pink","#FF007F"));
        color_list.add(new ItemData("red","#FF0000"));
        color_list.add(new ItemData("orange","#FF7F00"));
        color_list.add(new ItemData("yellow","#FFFF00"));
        color_list.add(new ItemData("chartreuse","#7FFF00"));
        color_list.add(new ItemData("green","#00FF00"));
        color_list.add(new ItemData("spring_green","#00FF7F"));
        color_list.add(new ItemData("cyan","#00FFFF"));
        color_list.add(new ItemData("azure","#007FFF"));
        color_list.add(new ItemData("blue","#0000FF"));
        color_list.add(new ItemData("violet","#7F00FF"));
        color_list.add(new ItemData("magenta","#FF00FF"));
        adapter = new ColorAdapter(this,color_list);
        sp_color.setAdapter(adapter);
        sp_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tvColor = ((view.findViewById(R.id.tv_color)));
                ColorDrawable drawable = (ColorDrawable) tvColor.getBackground();
                selected_color = Integer.toHexString(drawable.getColor()).substring(2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

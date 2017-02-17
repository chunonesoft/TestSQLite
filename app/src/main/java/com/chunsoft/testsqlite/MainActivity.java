package com.chunsoft.testsqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button createBtn;
    private Button insertBtn;
    private Button updateBtn;
    private Button queryBtn;
    private Button deleteBtn;
    private Button ModifyBtn;

    private SQLiteDBHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FindView();
        setListenner();
    }

    private void FindView() {
        createBtn = (Button)findViewById(R.id.createDatabase);
        updateBtn = (Button)findViewById(R.id.updateDatabase);
        insertBtn = (Button)findViewById(R.id.insert);
        ModifyBtn = (Button)findViewById(R.id.update);
        queryBtn = (Button)findViewById(R.id.query);
        deleteBtn = (Button)findViewById(R.id.delete);
    }

    private void setListenner() {
        createBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        insertBtn.setOnClickListener(this);
        ModifyBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createDatabase:
                //实例化SQLiteDBHelper对象
                dbHelper = new SQLiteDBHelper(MainActivity.this);
                //得到一个可读的SQLiteDatabase对象
                db = dbHelper.getReadableDatabase();
                break;

            case R.id.updateDatabase:
                dbHelper = new SQLiteDBHelper(MainActivity.this);
                db = dbHelper.getReadableDatabase();
                String sql = "DROP TABLE IF EXISTS " + " test.db";
                db.execSQL(sql);
                dbHelper = new SQLiteDBHelper(MainActivity.this);
                db = dbHelper.getReadableDatabase();
                break;

            case R.id.insert:
                dbHelper = new SQLiteDBHelper(MainActivity.this);
                db =dbHelper.getWritableDatabase();

                cv = new ContentValues();
                cv.put("id", 1);
                cv.put("sname", "xiaoming");
                cv.put("sage", 21);
                cv.put("ssex", "male");
                db.insert("students",null,cv);
                //关闭数据库
                db.close();
                break;

            case R.id.update:
                dbHelper = new SQLiteDBHelper(MainActivity.this);
                db = dbHelper.getWritableDatabase();
                cv = new ContentValues();

                cv.put("sage","23");
                //where 子句 "?"是占位符号，对应后面的"1",
                String whereClause="id=?";
                String [] whereArgs = {String.valueOf(1)};
                //参数1 是要更新的表名
                //参数2 是一个ContentValeus对象
                //参数3 是where子句
                db.update("students", cv, whereClause, whereArgs);
                db.close();
                break;
            case R.id.query:
                dbHelper = new SQLiteDBHelper(MainActivity.this);
                db = dbHelper.getReadableDatabase();
                //参数1：表名, 参数2：要想显示的列, 参数3：where子句, 参数4：where子句对应的条件值
                //参数5：分组方式, 参数6：having条件, 参数7：排序方式
                Cursor cursor = db.query("students",new String[]{"id","sname","sage","ssex"},
                        "id=?",new String[]{"1"}, null, null, null);
                while(cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("sname"));
                    String age = cursor.getString(cursor.getColumnIndex("sage"));
                    String sex = cursor.getString(cursor.getColumnIndex("ssex"));
                    Toast.makeText(MainActivity.this, "姓名：" + name + " " + "年龄：" + age + " " + "性别：" + sex,
                            Toast.LENGTH_SHORT).show();
                }
                db.close();
                break;
            case R.id.delete:
                dbHelper = new SQLiteDBHelper(MainActivity.this);
                //得到一个可写的数据库
                db =dbHelper.getWritableDatabase();
                String whereClauses = "id=?";
                String [] whereArgs1 = {"1"};
                //调用delete方法，删除数据
                db.delete("students", whereClauses, whereArgs1);

                db.close();
                break;
        }
    }
}

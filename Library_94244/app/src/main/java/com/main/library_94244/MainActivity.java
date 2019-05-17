package com.main.library_94244;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText numET = null;
    private EditText nameET = null;
    private EditText authorET = null;
    private EditText priceET = null;
    private EditText pageET = null;

    private Button add = null;
    private Button delete = null;
    private Button ask = null;
    private Button modify = null;

    private LinearLayout titleLL = null;
    private ListView resultLV = null;

    private MyDBHelper myDBHelper = null;

    private MyListener myListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        numET = (EditText)findViewById ( R.id.num );
        nameET = (EditText)findViewById ( R.id.name );
        authorET = (EditText)findViewById ( R.id.author );
        priceET = (EditText)findViewById ( R.id.price );
        pageET = (EditText)findViewById ( R.id.page );

        add = (Button)findViewById ( R.id.add );
        delete = (Button)findViewById ( R.id.delete );
        modify = (Button)findViewById ( R.id.modify );
        ask = (Button)findViewById ( R.id.ask );

        titleLL= (LinearLayout) findViewById ( R.id.titleLL );
        resultLV= (ListView) findViewById ( R.id.resultLV );


        myListener = new MyListener ();

        add.setOnClickListener ( myListener );

        ask.setOnClickListener ( myListener );

        delete.setOnClickListener ( myListener );

        modify.setOnClickListener ( myListener );


    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        if(myDBHelper!=null){
            myDBHelper.close ();
        }
    }

    private class  MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            myDBHelper = new MyDBHelper ( MainActivity.this,
                    "library.db",null,1);//￥￥￥￥￥￥

            SQLiteDatabase database = myDBHelper.getReadableDatabase ();

            String numStr = numET.getText ().toString ().trim ();
            String nameStr = nameET.getText ().toString ().trim ();
            String authorStr = authorET.getText ().toString ().trim ();
            String priceStr = priceET.getText ().toString ().trim ();
            String pageStr = pageET.getText ().toString ().trim ();

            switch (v.getId ()){
                case  R.id.add:
                    titleLL.setVisibility ( View.INVISIBLE );
                    addData(database,numStr,nameStr,authorStr,priceStr,pageStr);
                    Toast.makeText ( MainActivity.this,"添加成功",Toast.LENGTH_LONG ).show ();
                    resultLV.setAdapter ( null );
                    break;
                case  R.id.delete:
                    deleteData(database,numStr);
                    Toast.makeText ( MainActivity.this,"删除成功",Toast.LENGTH_LONG ).show ();
                    break;
                case  R.id.ask:
                    titleLL.setVisibility ( View.VISIBLE );
                    Cursor cursor = askData(database,numStr,nameStr,authorStr,priceStr,pageStr);
                    SimpleCursorAdapter resultAdapter = new SimpleCursorAdapter (
                            MainActivity.this,
                            R.layout.listviewlayout,
                            cursor,
                            new String[]{"_id","num","name","author","price","page"},
                            new int[]{ R.id.result_id,R.id.result_num, R.id.result_name,R.id.result_author,R.id.result_price,R.id.result_page }
                    );
                    resultLV.setAdapter ( resultAdapter );
                    break;
                case  R.id.modify:
                    modifyData(database,numStr,nameStr,authorStr,priceStr,pageStr);
                    Toast.makeText ( MainActivity.this,"修改成功",Toast.LENGTH_LONG ).show ();
                    break;
                    default:
                        break;
            }
        }
    }

    private void addData(SQLiteDatabase db ,String num ,String name ,String author,String price,String page) {
        db.execSQL( "insert into library_tb values(null,?,?,?,?,?);" ,new String[]{num,name,author,price,page} );
        numET.setText("");
        nameET.setText("");
        authorET.setText("");
        priceET.setText("");
        pageET.setText("");

    }
    private void deleteData(SQLiteDatabase db ,String num ) {
        db.execSQL ( "delete  from library_tb where num = ?;",new String[]{num} );
        numET.setText("");
    }

    private Cursor askData(SQLiteDatabase db ,String num , String name ,String author,String price,String page) {
        Cursor cursor = db.rawQuery( "select * from library_tb where num like ? and name like ? and author like ? and price like ? and page like ?"
                , new String[]{"%"+num+"%","%"+name+"%","%"+author+"%","%"+price+"%","%"+page+"%"} );
        return cursor;
    }

    private void modifyData(SQLiteDatabase db ,String num ,String name ,String author,String price,String page) {
        db.execSQL( "update library_tb  set num = ?,name = ?,author = ?,price = ?,page = ? where num = ?;",new String[]{num,name,author,price,page,num} );

    }
}

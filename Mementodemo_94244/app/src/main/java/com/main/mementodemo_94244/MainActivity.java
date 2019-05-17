package com.main.mementodemo_94244;

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

    private EditText themeET = null;
    private EditText contentET = null;
    private EditText timeET = null;

    private Button ctime = null;
    private Button add = null;
    private Button ask = null;

    private LinearLayout titleLL = null;
    private ListView resultLV = null;

    private MyDBHelper myDBHelper = null;

    private MyListener myListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        themeET = (EditText)findViewById ( R.id.theme );
        contentET = (EditText)findViewById ( R.id.content );
        timeET = (EditText)findViewById ( R.id.resulttime );

        ctime = (Button)findViewById ( R.id.ctime );
        add = (Button)findViewById ( R.id.add );
        ask= (Button)findViewById ( R.id.ask );

        titleLL= (LinearLayout) findViewById ( R.id.titleLL );
        resultLV= (ListView) findViewById ( R.id.resultLV );

        myListener = new MyListener ();

        ctime.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance ();
                new DatePickerDialog ( MainActivity.this,
                        new DatePickerDialog.OnDateSetListener () {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                timeET.setText ( year + "-" + (month+1) + "-" + dayOfMonth );
                            }
                        },
                        calendar.get ( calendar.YEAR ),
                        calendar.get ( calendar.MONTH ),
                        calendar.get ( calendar.DAY_OF_MONTH )
                ).show ();
            }
        } );

        add.setOnClickListener ( myListener );

        ask.setOnClickListener ( myListener );

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
                    "notepad.db",null,1);//￥￥￥￥￥￥

            SQLiteDatabase database = myDBHelper.getReadableDatabase ();

            String themeStr = themeET.getText ().toString ().trim ();
            String contentStr = contentET.getText ().toString ().trim ();
            String timeStr = timeET.getText ().toString ().trim ();

            switch (v.getId ()){
                case  R.id.add:
                    titleLL.setVisibility ( View.INVISIBLE );
                    addData(database,themeStr,contentStr,timeStr);
                    Toast.makeText ( MainActivity.this,"添加成功",Toast.LENGTH_LONG ).show ();
                    resultLV.setAdapter ( null );
                    break;
                case  R.id.ask:
                    titleLL.setVisibility ( View.VISIBLE );
                    Cursor cursor = askData(database,themeStr,contentStr,timeStr);
                    SimpleCursorAdapter resultAdapter = new SimpleCursorAdapter (
                            MainActivity.this,
                            R.layout.listviewlayout,
                            cursor,
                            new String[]{"_id","theme","content","date"},
                            new int[]{ R.id.result_id, R.id.result_th,R.id.result_co,R.id.result_ti }
                    );
                    resultLV.setAdapter ( resultAdapter );
                    break;
                    default:
                        break;
            }
        }
    }

    private void addData(SQLiteDatabase db , String theme ,String content,String date) {
        db.execSQL( "insert into notepad_tb values(null,?,?,?);" ,new String[]{theme,content,date} );
        themeET.setText("");
        contentET.setText("");
        timeET.setText("");
    }

    private Cursor askData(SQLiteDatabase db , String theme ,String content,String date) {
        Cursor cursor = db.rawQuery( "select * from notepad_tb where theme like ? and content like ? and date like ?"
                , new String[]{"%"+theme+"%","%"+content+"%","%"+date+"%"} );
        return cursor;
    }
}

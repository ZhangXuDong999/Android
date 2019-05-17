package com.main.accesscontent2;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText name = null;
    private EditText tel = null;

    private Button add = null;
    private Button show = null;

    private LinearLayout titleLL = null;
    private ListView resultLV = null;

    private ContentResolver resolver = null;

    private MyListener myListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        name = (EditText) findViewById ( R.id.name );
        tel = (EditText) findViewById ( R.id.tel );

        add = (Button) findViewById ( R.id.add );
        show = (Button) findViewById ( R.id.show );

        titleLL= (LinearLayout) findViewById ( R.id.titleLL );
        resultLV= (ListView) findViewById ( R.id.resultLV );

        resolver = getContentResolver ();
        myListener = new MyListener ();

        add.setOnClickListener ( myListener );
        show.setOnClickListener ( myListener );


    }
    private class  MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            switch (v.getId ()) {
                case R.id.add:
                    titleLL.setVisibility ( View.INVISIBLE );
                    if (ContextCompat.checkSelfPermission ( MainActivity.this,
                            Manifest.permission.WRITE_CONTACTS ) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions ( MainActivity.this,new String[]{
                            Manifest.permission.WRITE_CONTACTS},1);
                    }
                    addContent ();
                    Toast.makeText ( MainActivity.this,"添加成功",Toast.LENGTH_LONG ).show ();
                    break;
                case R.id.show:
                    if (ContextCompat.checkSelfPermission ( MainActivity.this,
                            Manifest.permission.READ_CONTACTS ) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions ( MainActivity.this,new String[]{ Manifest.permission.READ_CONTACTS},2);
                    }
                    titleLL.setVisibility ( View.VISIBLE );
                    ArrayList<Map<String, String>> person = showContent ();
                    SimpleAdapter adapter = new SimpleAdapter (
                            MainActivity.this,
                            person,
                            R.layout.result,
                            new String[]{"_id","name","tel"},
                            new int[]{ R.id.result_id, R.id.result_name,R.id.result_tel } );
                    resultLV.setAdapter ( adapter );
                    Toast.makeText ( MainActivity.this,"已显示全部",Toast.LENGTH_LONG ).show ();
                    break;
                    default:break;
            }
        }
    }

    private void addContent() {

        String nameStr = name.getText ().toString ().trim ();
        String telStr = tel.getText ().toString ().trim ();

        ContentValues values = new ContentValues (  );

        Uri rawContentUri = resolver.insert ( ContactsContract.RawContacts.CONTENT_URI,values );
        long contactId = ContentUris.parseId ( rawContentUri );
        values.clear ();

        values.put ( ContactsContract.Data.RAW_CONTACT_ID,contactId );
        values.put ( ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE );
        values.put ( ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,nameStr );
        resolver.insert ( ContactsContract.Data.CONTENT_URI,values );
        values.clear ();

        values.put ( ContactsContract.Data.RAW_CONTACT_ID,contactId );
        values.put ( ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE );
        values.put (  ContactsContract.CommonDataKinds.Phone.NUMBER,telStr );
        values.put ( ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE );
        resolver.insert ( ContactsContract.Data.CONTENT_URI,values );
    }

    private ArrayList<Map<String, String>> showContent() {
        ArrayList<Map<String,String>> details = new ArrayList<Map<String,String>> (  );
        Cursor cursor = resolver.query ( ContactsContract.Contacts.CONTENT_URI,null,null,null,null );
        while (cursor.moveToNext ()){
            Map<String, String> person = new HashMap<String, String> (  );
            String personId = cursor.getString ( cursor.getColumnIndex ( ContactsContract.Contacts._ID ) );
            String personName = cursor.getString ( cursor.getColumnIndex ( ContactsContract.Contacts.DISPLAY_NAME ) );
            person.put ( "id",personId );
            person.put ( "name",personName );
            //String personNumber = cursor.getString ( cursor.getColumnIndex ( ContactsContract.Contacts.HAS_PHONE_NUMBER ) );
            Cursor cursor1 = resolver.query ( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +"="+personId,null,null );
            if (cursor1.moveToNext ())
            {
                String personNumber = cursor1.getString ( cursor1.getColumnIndex ( ContactsContract.CommonDataKinds.Phone.NUMBER ) );
                person.put ( "tel",personNumber );
            }
            cursor1.close ();
            details.add ( person );
        }
        cursor.close ();
        return details;
    }
}

package com.main.offlinedemo_94244;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.preference.TwoStatePreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

public class LoginActivity extends BaseActivity {
    private EditText id = null;
    private EditText pwd = null;
    private CheckBox checkbox = null;
    private Button login = null;

    private SharedPreferences sharedPreferences = null;//共享数据
    private SharedPreferences.Editor editor = null;//编辑器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );
        id = (EditText) findViewById ( R.id.id );
        pwd = (EditText) findViewById ( R.id.pwd );
        checkbox = (CheckBox) findViewById ( R.id.checkbox );
        login = (Button) findViewById ( R.id.login );

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences ( this );
        editor = sharedPreferences.edit ();

        boolean isRemember = sharedPreferences.getBoolean ( "checkbox",false );
        
        if(isRemember){
            String usernameStr = sharedPreferences.getString ( "id",null );
            String passwordStr = sharedPreferences.getString ( "pwd",null );
            id.setText ( usernameStr );
            pwd.setText ( passwordStr );
            checkbox.setChecked ( true );
        }

        login.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String usernameStr = id.getText ().toString ().trim ();
                String passwordStr = pwd.getText ().toString ().trim ();
                if(usernameStr.equals ( "admin" )&&passwordStr.equals ( "123456" )){
                    if(checkbox.isChecked()){
                        editor.putString ( "id" ,usernameStr);
                        editor.putString ( "pwd" ,passwordStr);
                        editor.putBoolean ( "checkbox",checkbox.isChecked());
                    }else{

                        editor.clear ();
                    }

                    editor.apply ();
                    Intent intent = new Intent ( LoginActivity.this,MainActivity.class );
                    startActivity ( intent );
                    finish ();
                }
                else {
                    Toast.makeText ( LoginActivity.this,"用户名或密码错误！",Toast.LENGTH_LONG ).show ();
                    id.setText ( "" );
                    pwd.setText ( "" );
                }
            }
        } );
    }
}

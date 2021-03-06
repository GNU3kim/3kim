package com.example.minkyu.taxi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;

public class ListaddActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "192.168.0.195"; //서버 ip주소로 변경해줘야함
    private static String TAG = "phptest";

    private EditText mEditTextDep;
    private EditText mEditTextDes;
    private EditText mEditTextadd;
    private EditText mEditTextSay;
    private EditText mEditTextPhone;
    ArrayAdapter<CharSequence> adspin1, adspin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadd);

        ActionBar ab = getSupportActionBar() ;

        ab.setIcon(R.drawable.typing) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true);

        adspin1 = ArrayAdapter.createFromResource(ListaddActivity.this,R.array.time,R.layout.list_item);
        adspin2 = ArrayAdapter.createFromResource(ListaddActivity.this, R.array.min,R.layout.list_item);

        final TextView tv = (TextView)findViewById(R.id.textView_spinner);
        final TextView tv2 = (TextView)findViewById(R.id.textView_minspinner);
        final Spinner s = (Spinner)findViewById(R.id.spinner);
        final Spinner s2 = (Spinner)findViewById(R.id.min_spinner);


        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                tv.setText(adspin1.getItem(position)+"시");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    tv2.setText(adspin2.getItem(position)+"분");
                }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

       mEditTextDep = (EditText)findViewById(R.id.editText_main_dep);
       mEditTextDes = (EditText)findViewById(R.id.editText_main_des);
       mEditTextadd = (EditText)findViewById(R.id.addr_editText);
       mEditTextSay = (EditText)findViewById(R.id.SayeditText);
       mEditTextPhone = (EditText)findViewById(R.id.PhoneeditText);


        Button buttonInsert = (Button)findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dep = mEditTextDep.getText().toString();
                String des = mEditTextDes.getText().toString();
                String time = tv.getText().toString();
                String min = tv2.getText().toString();
                String addr = mEditTextadd.getText().toString();
                String phone = mEditTextPhone.getText().toString();
                String say = mEditTextSay.getText().toString();
                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert.php", dep,des,time,min,addr,phone,say);

                if(dep.length() == 0){
                    Toast.makeText(getApplicationContext(),"출발지를 입력해주세요!!"
                            ,Toast.LENGTH_LONG).show();
                }
                else if(des.length() == 0){
                    Toast.makeText(getApplicationContext(),"목적지를 입력해주세요!!"
                            ,Toast.LENGTH_LONG).show();
                }
                else if(addr.length() == 0 && phone.length() == 0){
                    Toast.makeText(getApplicationContext(),"카카오톡 ID / 연락처 중 하나라도 " +
                                    "입력해주세요", Toast.LENGTH_LONG).show();
                }

                if(dep.length() != 0  && des.length() != 0 &&
                        (addr.length() != 0 || phone.length() !=0)) {
                    mEditTextDep.setText("");
                    mEditTextDes.setText("");
                    mEditTextadd.setText("");
                    mEditTextSay.setText("");
                    mEditTextPhone.setText("");
                    Intent intent = new Intent(ListaddActivity.this, ListActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"추가되었습니다 !!"
                            ,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

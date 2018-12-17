package com.example.minkyu.taxi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {
    static String myJSON;
    static String TAG_RESULTS = "result";
    static String TAG_DEP = "dep";
    static String TAG_DES = "des";
    static String TAG_TIME = "time";
    static String TAG_min = "min";
    static String TAG_ADDR = "addr";
    static String TAG_PHONE = "phone";
    static String TAG_SAY = "say";

    static JSONArray Taxi = null;

    static ArrayList<HashMap<String, String>> taxiList;
    static ArrayList<HashMap<String, String>> saveList;
    static ListView list;
    static SimpleAdapter adapter;
    GetDataJSON getDataJSON;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ActionBar ab = getSupportActionBar() ;
        ab.setIcon(R.drawable.typing) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true);

        final EditText depText = (EditText) findViewById(R.id.depText);
        final EditText desText = (EditText) findViewById(R.id.desText);
        list = (ListView) findViewById(R.id.listView);
        taxiList = new ArrayList<HashMap<String, String>>();
        saveList = new ArrayList<HashMap<String, String>>();
        adapter = new SimpleAdapter(
                ListActivity.this, taxiList, R.layout.list_item,
                new String[]{TAG_DEP,TAG_DES,TAG_TIME,TAG_min},
                new int[]{R.id.dep1_text,R.id.des1_text,R.id.time1_text,R.id.min1_text}
        );

        getDataJSON = new GetDataJSON();
        getDataJSON.execute("http://192.168.0.195/PHP_connection.php"); //서버 ip주소로 변경해줘야함
        depText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        desText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter2(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ShareActivity.class);
                intent.putExtra("Dep",taxiList.get(position).get(TAG_DEP));
                intent.putExtra("Des",taxiList.get(position).get(TAG_DES));
                intent.putExtra("Time",taxiList.get(position).get(TAG_TIME));
                intent.putExtra("Min",taxiList.get(position).get(TAG_min));
                intent.putExtra("Addr",taxiList.get(position).get(TAG_ADDR));
                intent.putExtra("Phone",taxiList.get(position).get(TAG_PHONE));
                intent.putExtra("Say",taxiList.get(position).get(TAG_SAY));
                startActivity(intent);
            }
        });
        Button button = (Button) findViewById(R.id.rfbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taxiList.clear();
                saveList.clear();
                depText.setText("");
                desText.setText("");
                getDataJSON.showList();
            }
        });
    }

    private void filter(CharSequence s) {
        taxiList.clear();
        if(s.length() == 0){
            taxiList.addAll(saveList);
        }
        else{
            for(int i = 0; i < saveList.size(); i++){
                if(saveList.get(i).get(TAG_DEP).contains(s)){
                    taxiList.add(saveList.get(i));
                }
            }
        }adapter.notifyDataSetChanged();
    }

    private void filter2(CharSequence s) {
        EditText depText = (EditText) findViewById(R.id.depText);
        String dep = depText.getText().toString();
        taxiList.clear();
        for(int i = 0; i < saveList.size(); i++){
            if(saveList.get(i).get(TAG_DES).contains(s)){
                if(saveList.get(i).get(TAG_DEP).contains(dep)){
                    taxiList.add(saveList.get(i));
                }
            }
        }adapter.notifyDataSetChanged();
    }

    public void backClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void searchClick(View view) {
        Intent intent = new Intent(this, ListaddActivity.class);
        startActivity(intent);
    }
}

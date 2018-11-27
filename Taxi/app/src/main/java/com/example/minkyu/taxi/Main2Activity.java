package com.example.minkyu.taxi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class Main2Activity extends AppCompatActivity {
    String myJSON;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_DEP = "dep";
    private static final String TAG_DES = "des";
    private static final String TAG_TIME = "time";
    private static final String TAG_min = "min";
    private  static final String TAG_ADDR = "addr";
    private static final String TAG_PHONE = "phone";
    private static final  String TAG_SAY = "say";

    JSONArray Taxi = null;

    ArrayList<HashMap<String, String>> taxiList;
    ArrayList<HashMap<String, String>> saveList;
    ListView list;
    SimpleAdapter adapter;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final EditText depText = (EditText) findViewById(R.id.depText);
        final EditText desText = (EditText) findViewById(R.id.desText);
        list = (ListView) findViewById(R.id.listView);
        taxiList = new ArrayList<HashMap<String, String>>();
        saveList = new ArrayList<HashMap<String, String>>();
        adapter = new SimpleAdapter(
                Main2Activity.this, taxiList, R.layout.list_item,
                new String[]{TAG_DEP,TAG_DES,TAG_TIME,TAG_min},
                new int[]{R.id.dep1_text,R.id.des1_text,R.id.time1_text,R.id.min1_text}
        );
        getData("http://172.17.125.44/PHP_connection.php"); //서버 ip주소로 변경해줘야함


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
                showList();
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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void searchClick(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void showList(){
        try{
            JSONObject jsonObj = new JSONObject(myJSON);
            Taxi = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i = 0; i < Taxi.length(); i++){
                JSONObject c = Taxi.getJSONObject(i);
                String dep = c.getString(TAG_DEP);
                String des = c.getString(TAG_DES);
                String time = c.getString(TAG_TIME);
                String min = c.getString(TAG_min);
                String id = c.getString(TAG_ADDR);
                String phone = c.getString(TAG_PHONE);
                String say = c.getString(TAG_SAY);
                HashMap<String, String> lists = new HashMap<String, String>();

                lists.put(TAG_DEP, dep);
                lists.put(TAG_DES, des);
                lists.put(TAG_TIME, time);
                lists.put(TAG_min, min);
                lists.put(TAG_ADDR, id);
                lists.put(TAG_PHONE, phone);
                lists.put(TAG_SAY, say);
                taxiList.add(lists);
                saveList.add(lists);
            }
            list.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}

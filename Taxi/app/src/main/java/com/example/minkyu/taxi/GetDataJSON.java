package com.example.minkyu.taxi;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class GetDataJSON extends AsyncTask<String, Void, String> {

    ListActivity listActivity = new ListActivity();

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
        listActivity.myJSON = result;
        showList();
    }

    public void showList(){
        try{
            JSONObject jsonObj = new JSONObject(listActivity.myJSON);
            listActivity.Taxi = jsonObj.getJSONArray(listActivity.TAG_RESULTS);

            for(int i = 0; i < listActivity.Taxi.length(); i++){
                JSONObject c = listActivity.Taxi.getJSONObject(i);
                String dep = c.getString(listActivity.TAG_DEP);
                String des = c.getString(listActivity.TAG_DES);
                String time = c.getString(listActivity.TAG_TIME);
                String min = c.getString(listActivity.TAG_min);
                String id = c.getString(listActivity.TAG_ADDR);
                String phone = c.getString(listActivity.TAG_PHONE);
                String say = c.getString(listActivity.TAG_SAY);
                HashMap<String, String> lists = new HashMap<String, String>();

                lists.put(listActivity.TAG_DEP, dep);
                lists.put(listActivity.TAG_DES, des);
                lists.put(listActivity.TAG_TIME, time);
                lists.put(listActivity.TAG_min, min);
                lists.put(listActivity.TAG_ADDR, id);
                lists.put(listActivity.TAG_PHONE, phone);
                lists.put(listActivity.TAG_SAY, say);
                listActivity.taxiList.add(lists);
                listActivity.saveList.add(lists);
            }
            listActivity.list.setAdapter(listActivity.adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}



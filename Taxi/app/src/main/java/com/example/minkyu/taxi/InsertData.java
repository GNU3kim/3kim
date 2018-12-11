package com.example.minkyu.taxi;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InsertData extends AsyncTask<String, Void, String > {
    private static String TAG = "phptest";

    protected void onPreExecute(){
        super.onPreExecute();
        ListaddActivity listaddActivity;
    }

    protected void onPostExecute(String result){
        super.onPostExecute(result);
        Log.d(TAG,"POST response -"+result);
    }

    @Override
    protected String doInBackground(String... params) {
        String dep = (String)params[1];
        String des = (String)params[2];
        String time = (String)params[3];
        String min = (String)params[4];
        String addr = (String)params[5];
        String phone = (String)params[6];
        String say = (String)params[7];

        String serverURL = (String)params[0];
        String postParameters = "&dep=" + dep + "&des=" + des + "&time=" + time + "&min=" +
                min+ "&addr=" + addr + "&phone="+ phone + "&say=" + say;
        try {

            URL url = new URL(serverURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(postParameters.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();


            int responseStatusCode = httpURLConnection.getResponseCode();
            Log.d(TAG, "POST response code - " + responseStatusCode);

            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
            else{
                inputStream = httpURLConnection.getErrorStream();
            }


            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }
            bufferedReader.close();
            return sb.toString();
        } catch (Exception e) {

            Log.d(TAG, "InsertData: Error ", e);

            return new String("Error: " + e.getMessage());
        }
    }
}
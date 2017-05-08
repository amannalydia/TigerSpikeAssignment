package com.tigerspike.assignment.view;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.tigerspike.assignment.R;
import com.tigerspike.assignment.model.ImageData;
import com.tigerspike.assignment.utility.RetrieveBitmapImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FlickrFeedActivity extends AppCompatActivity {
    public static final String TAG = "FlickrFeedActivity";
    private ArrayList<ImageData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(isNetworkAvailable()) {
            //Flickr Data Source url to retrieve response in JSON format without function wrapper
            String strURL = "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1";
            new fetchFlickrData().execute(strURL);
        } else
            Toast.makeText(this,"No network connectivity",Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class fetchFlickrData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(FlickrFeedActivity.this);
            progressDialog.setMessage("Loading");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                result = retrieveData(params[0]);
                Log.d(TAG,"result = "+result);
            } catch (IOException e) {
                Log.d(TAG,"Exception in Background Task: "+e.getMessage());
            }
            return result;
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            list = parseJSONResponse(response);
            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.img_recycler_view);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(FlickrFeedActivity.this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            RecyclerView.Adapter mAdapter = new CardViewAdapter(FlickrFeedActivity.this,list);
            mRecyclerView.setAdapter(mAdapter);
        }

        private ArrayList<ImageData> parseJSONResponse(String result){
            ArrayList<ImageData> imgList = new ArrayList<>();
            JSONObject responseObj = null;
            try {
                responseObj = new JSONObject(result);
                //When a failure response is received
                if(responseObj.has("stat") && responseObj.getString("stat").equalsIgnoreCase("fail")){
                    Toast.makeText(FlickrFeedActivity.this,"Fetching JSON response failed",Toast.LENGTH_SHORT).show();
                } else{
                    JSONArray responseArray = responseObj.getJSONArray("items");
                    ImageData img;
                    for(int i=0;i<responseArray.length();i++){
                        JSONObject obj = responseArray.getJSONObject(i);
                        img = new ImageData();
                        img.setTitle(obj.optString("title").toString());
                        img.setDescription(obj.optString("description").toString());
                        img.setAuthor(obj.optString("author").toString());
                        img.setDate_taken(obj.optString("date_taken").toString());
                        img.setTags(obj.optString("tags").toString());
                        JSONObject mediaObj = obj.getJSONObject("media");
                        img.setImgUrl(mediaObj.optString("m").toString());
                        imgList.add(i,img);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return imgList;
        }

        //method to create http connection to fetch data from the url
        private String retrieveData(String strUrl) throws IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try{
                URL url = new URL(strUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                iStream = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
                StringBuffer sb  = new StringBuffer();
                String line = "";
                while( ( line = br.readLine())  != null){
                    sb.append(line);
                }
                data = sb.toString();
                br.close();

            } catch(Exception e){
                Log.d("Exception", e.toString());
            } finally{
                iStream.close();
                urlConnection.disconnect();
            }

            return data;
        }
    }

}

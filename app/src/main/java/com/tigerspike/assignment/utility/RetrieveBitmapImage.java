package com.tigerspike.assignment.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.tigerspike.assignment.view.CardViewAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lydiaamanna on 07/05/17.
 * AsyncTask to retrieve bitmap content of the String image Urls in the JSON response. These bitmap images
 * are to be displayed in the recycler view.
 */

public class RetrieveBitmapImage extends AsyncTask<String, Void, Bitmap> {
    private CardViewAdapter.ViewHolderImage holder;
    private AsyncTaskListener listener;
    private Context context;
    
    public RetrieveBitmapImage(Context context, AsyncTaskListener listener,CardViewAdapter.ViewHolderImage holder){
        this.context = context;
        this.listener = listener;
        this.holder = holder;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(params[0]);
            bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        listener.onActionComplete(bitmap,holder);

    }
}

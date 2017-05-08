package com.tigerspike.assignment.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tigerspike.assignment.R;
import com.tigerspike.assignment.model.ImageData;
import com.tigerspike.assignment.utility.RetrieveBitmapImage;
import com.tigerspike.assignment.utility.AsyncTaskListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lydiaamanna on 07/05/17.
 * Recycler View Adapter to inflate each row in the card layout.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolderImage> implements AsyncTaskListener {
    private Context context;
    private ArrayList<ImageData> imgDataList;
    public static final int REQUEST_PERMISSION = 99;

    public CardViewAdapter(Context context,ArrayList<ImageData> imgDataList){
        this.context = context;
        this.imgDataList = imgDataList;
    }

    @Override
    public ViewHolderImage onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        ViewHolderImage viewHolder = new ViewHolderImage(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderImage holder, final int position) {
        ImageData img = imgDataList.get(position);
        holder.imgTitle.setText(img.getTitle());
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ImageInfoActivity.class);
                intent.putExtra("position",position);
                intent.putParcelableArrayListExtra("ImageInformation",imgDataList);
                view.getContext().startActivity(intent);

            }
        });

        String imageStrURL = img.getImgUrl();
        //retrieve bitmap content from the string url of images
        new RetrieveBitmapImage(context, this, holder).execute(imageStrURL);
    }

    @Override
    public int getItemCount() {
        return imgDataList.size();
    }


    public static class ViewHolderImage extends RecyclerView.ViewHolder {
        public TextView imgTitle;
        public ImageView picture,share,info;

        public ViewHolderImage(View itemView) {
            super(itemView);
            imgTitle = (TextView) itemView.findViewById(R.id.title);
            picture = (ImageView) itemView.findViewById(R.id.thumbnail);
            share = (ImageView) itemView.findViewById(R.id.share);
            info = (ImageView) itemView.findViewById(R.id.info);

        }
    }

    @Override
    public void onActionComplete(final Bitmap bitmap, CardViewAdapter.ViewHolderImage holder) {
        holder.picture.setImageBitmap(bitmap);
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            (Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                } else {
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setType("image/jpeg");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                            bitmap, "Title", null);
                    Uri imageUri =  Uri.parse(path);
                    intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    context.startActivity(Intent.createChooser(intent, "Select"));
                }

            }
        });


    }
    
}




package com.tigerspike.assignment.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lydiaamanna on 07/05/17.
 * Recycler View Adapter to inflate each row in the card layout.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolderImage> implements AsyncTaskListener {
    private Context context;
    private ArrayList<ImageData> imgDataList;

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
        String imageStrURL = img.getImgUrl();
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ImageInfoActivity.class);
                intent.putExtra("position",position);
                intent.putParcelableArrayListExtra("ImageInformation",imgDataList);
                view.getContext().startActivity(intent);

            }
        });
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
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
    }

    @Override
    public void onActionComplete(Bitmap bitmap, CardViewAdapter.ViewHolderImage holder) {
        holder.picture.setImageBitmap(bitmap);
    }

}




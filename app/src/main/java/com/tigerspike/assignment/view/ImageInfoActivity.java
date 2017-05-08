package com.tigerspike.assignment.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tigerspike.assignment.R;
import com.tigerspike.assignment.model.ImageData;

import java.util.ArrayList;

public class ImageInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_info);
        TextView title = (TextView) findViewById(R.id.titleValue);
        TextView description = (TextView) findViewById(R.id.descriptionValue);
        TextView author = (TextView) findViewById(R.id.authorValue);
        TextView dateTaken = (TextView) findViewById(R.id.dateValue);
        TextView tags = (TextView) findViewById(R.id.tagsValue);

        ArrayList<ImageData> imgList = new ArrayList<>();
        int position = 0;
        if(getIntent().getExtras() != null){
            position = getIntent().getIntExtra("position",0);
            imgList = getIntent().getExtras().getParcelableArrayList("ImageInformation");

        }
        title.setText(imgList.get(position).getTitle());
        description.setText(imgList.get(position).getDescription());
        author.setText(imgList.get(position).getAuthor().split("\"")[1]);
        dateTaken.setText(imgList.get(position).getDate_taken());
        tags.setText(imgList.get(position).getTags());
    }
}

package com.tigerspike.assignment.utility;

import android.graphics.Bitmap;

import com.tigerspike.assignment.view.CardViewAdapter;

/**
 * Created by lydiaamanna on 07/05/17.
 */

public interface AsyncTaskListener {
    public void onActionComplete(Bitmap bitmap, CardViewAdapter.ViewHolderImage holder);
}

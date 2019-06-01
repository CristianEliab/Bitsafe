package com.appmoviles.proyecto.util;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.appmoviles.proyecto.R;
import com.bumptech.glide.Glide;


public class BaseActivity extends AppCompatActivity {

    @VisibleForTesting
    public ProgressDialog mProgressDialog;
    Dialog dialog;


    public void showProgressDialog(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_loading);

        ImageView gifImageView = dialog.findViewById(R.id.custom_loading_imageView);

        Glide.with(this)
                .load(R.drawable.loading_4)
                .centerCrop()
                .into(gifImageView);

        dialog.show();
    }

    public void hideProgressDialog() {
        if(dialog != null){
            dialog.dismiss();
        }
    }

    public void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}

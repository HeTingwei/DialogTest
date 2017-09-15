package com.xingyi.demo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

/*
* 将事件传递回对话框的宿主
*
*
* */

public class MainActivity extends FragmentActivity
        implements NoticeDialogFragment.NoticeDialogListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View v){
        showNoticeDialog();
    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new  NoticeDialogFragment();
        dialog.show(getFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Toast.makeText(this, "PositiveClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "NegativeClick", Toast.LENGTH_SHORT).show();

    }



}
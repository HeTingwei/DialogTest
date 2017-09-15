package com.xingyi.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by HeTingwei on 2017/9/15.
 */

public class MyDialog2 extends DialogFragment {

    EditText username;
    EditText password;
    View view;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
         view=inflater.inflate(R.layout.my_dialog2, null);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("signin", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                         username=view.findViewById(R.id.username);
                        password=view.findViewById(R.id.password);
                        Toast.makeText(getActivity(), username.getText()+"\n"+password.getText(), Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MyDialog2.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}

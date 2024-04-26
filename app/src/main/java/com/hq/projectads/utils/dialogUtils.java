package com.hq.projectads.utils;

import android.app.Dialog;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.hq.projectads.R;
import com.hq.projectads.SignIn;

public class dialogUtils {
    public static AlertDialog.Builder dialogErrorNetwork(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error server !")
                .setMessage("Error server, please reload app!")
                .setIcon(R.drawable.err_loading)
                .setPositiveButton("OK", (d, e1) -> {
                });
        return builder;

    }

}

package com.pappaya.prms.utils;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by yasar on 10/12/16.
 */
public class ProgressUtil {

    public static ProgressDialog mProgressDialog;

    public static void showProgressbar(Activity context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Loading..........");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }

    public static void hideProgressbar() {
        mProgressDialog.hide();
    }

}

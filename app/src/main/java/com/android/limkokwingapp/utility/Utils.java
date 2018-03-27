package com.android.limkokwingapp.utility;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by gautam on 26/03/18.
 */

public class Utils {

    /**
     * Method to display SnackBar with short duration
     * @param view - View type (Eg: TextView, Button, EditText)
     * @param text - The text that should be shown on the Snackbar
     */
    public static void showSnackShort(View view, String text){
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }


    /**
     * Method to show toast message
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

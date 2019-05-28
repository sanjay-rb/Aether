package sih.project.airwater;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DatabaseError;

public abstract class BaseActvity extends AppCompatActivity {

    public ProgressDialog showProgression(Context context, String message, String title) {
        ProgressDialog progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage(message);
        progressDoalog.setTitle(title);
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return progressDoalog;
    }

    public void showAlertDialog(Context context, String message, String title, String button_text) {
        new AlertDialog.Builder(
                context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(button_text, null)
                .show();

    }

    public void openFragement(Fragment fragment){
        @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_view, fragment);
        fragmentTransaction.commit();

    }

    public void displayError(String TAG, Exception e){
        String er = (e.getMessage() == null)? "Some Unknown exception" : e.getMessage();
        Log.e(TAG, er);
    }

    public void displayDBError(String TAG, DatabaseError databaseError){
        Log.e(TAG, databaseError.getMessage() + ":" + databaseError.getDetails());
    }
}

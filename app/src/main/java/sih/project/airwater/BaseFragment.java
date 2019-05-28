package sih.project.airwater;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.firebase.database.DatabaseError;


public abstract class BaseFragment extends Fragment {

    public ProgressDialog showProgression(Context context, String message, String title) {
        ProgressDialog progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage(message);
        progressDoalog.setTitle(title);
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return progressDoalog;
    }

    public void displayError(String TAG, Exception e){
        String er = (e.getMessage() == null)? "Some Unknown exception" : e.getMessage();
        Log.e(TAG, er);
    }

    public void displayDBError(String TAG, DatabaseError databaseError){
        Log.e(TAG, databaseError.getMessage() + ":" + databaseError.getDetails());
    }
}

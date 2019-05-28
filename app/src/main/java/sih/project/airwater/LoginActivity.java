package sih.project.airwater;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.WindowManager;

import java.util.Objects;

public class LoginActivity extends BaseActvity {
    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try{
            Objects.requireNonNull(getSupportActionBar()).hide();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }catch (Exception e){
            displayError(TAG, e);
        }

    }

    public void onClick(View v){
        try{
            switch (v.getId()){
                case R.id.btn_login:
                    TextInputEditText email = findViewById(R.id.et_email_loign);
                    TextInputEditText pass = findViewById(R.id.et_email_pass);
                    if (!Objects.requireNonNull(email.getText()).toString().equals("") || !email.getText().toString().equals("")){
                        if(email.getText().toString().equals("sanjaybabu618@gmail.com") && Objects.requireNonNull(pass.getText()).toString().equals("airinout")){
                            startActivity(new Intent(LoginActivity.this, TabActivity.class));
                            finish();
                        }else {
                            showAlertDialog(LoginActivity.this, "Password miss match!", "Login error!", "Try again");
                        }
                    }else {
                        showAlertDialog(LoginActivity.this, "Please enter the email and password", "Login error!", "Try again");
                    }
                    break;
                case R.id.btn_skip:
                    startActivity(new Intent(LoginActivity.this, TabActivity.class));
                    finish();
                    break;

            }
        }catch (Exception e){
            displayError(TAG, e);
        }
    }
}

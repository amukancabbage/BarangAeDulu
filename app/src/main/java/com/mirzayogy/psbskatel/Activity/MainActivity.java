package com.mirzayogy.psbskatel.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mirzayogy.psbskatel.Common;
import com.mirzayogy.psbskatel.Config;
import com.mirzayogy.psbskatel.MVP.SignUpResponse;
import com.mirzayogy.psbskatel.R;
import com.mirzayogy.psbskatel.Retrofit.Api;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    @BindViews({R.id.email, R.id.password})
    List<EditText> editTexts;
    @BindView(R.id.loginLinearLayout)
    LinearLayout loginLinearLayout;
    @BindView(R.id.appIcon)
    ImageView appIcon;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loginLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);

            }
        });

    }
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @OnClick({R.id.txtSignUp, R.id.txtForgotPassword, R.id.skipLoginLayout, R.id.signIn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSignUp:
                Config.moveTo(MainActivity.this, Signup.class);
                break;
            case R.id.txtForgotPassword:
                Config.moveTo(MainActivity.this, ForgotPassword.class);
                break;
            case R.id.skipLoginLayout:
//                Intent intent = new Intent(Login.this, MainActivity.class);
//                intent.putExtra("from", "skip");
//                startActivity(intent);
//                finishAffinity();
                break;

            case R.id.signIn:
                if (Config.validateEmail(editTexts.get(0),MainActivity.this) && validatePassword(editTexts.get(1))) {
                    login();
                }
                break;
        }
    }

    private boolean validatePassword(EditText editText) {
        if (editText.getText().toString().trim().length() > 5) {
            return true;
        } else if (editText.getText().toString().trim().length() > 0) {
            editText.setError("Password must be of 6 characters");
            editText.requestFocus();
            return false;
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    private void login() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        // sending gcm token to server
        Api.getClient().login(editTexts.get(0).getText().toString().trim(),
                editTexts.get(1).getText().toString().trim(),
                "email",
                new Callback<SignUpResponse>() {
                    @Override
                    public void success(SignUpResponse signUpResponse, Response response) {
                        pDialog.dismiss();
                        Log.d("signUpResponse", signUpResponse.getUserid() + "");
                        Toast.makeText(MainActivity.this, signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (signUpResponse.getSuccess().equalsIgnoreCase("true")) {
                            Common.saveUserData(MainActivity.this, "email", editTexts.get(1).getText().toString());
                            Common.saveUserData(MainActivity.this, "userId", signUpResponse.getUserid() + "");
                            Config.moveTo(MainActivity.this, MainActivity.class);
                            finishAffinity();
                        } else if (signUpResponse.getSuccess().equalsIgnoreCase("notactive")) {
                            Config.moveTo(MainActivity.this, AccountVerification.class);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        pDialog.dismiss();

                        Log.e("error", error.toString());
                    }
                });
    }
}

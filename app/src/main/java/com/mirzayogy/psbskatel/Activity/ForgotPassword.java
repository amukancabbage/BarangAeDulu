package com.mirzayogy.psbskatel.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mirzayogy.psbskatel.Config;
import com.mirzayogy.psbskatel.MVP.SignUpResponse;
import com.mirzayogy.psbskatel.R;
import com.mirzayogy.psbskatel.Retrofit.Api;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ForgotPassword extends AppCompatActivity {
    @BindViews({R.id.emailId})
    List<EditText> editTexts;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.back,R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                if (Config.validateEmail(editTexts.get(0),ForgotPassword.this)) {
                    forgotPassword();
                }
                break;
        }
    }

    private void forgotPassword() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ForgotPassword.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        // sending gcm token to server
        Api.getClient().forgotPassword(editTexts.get(0).getText().toString().trim(),
                new Callback<SignUpResponse>() {
                    @Override
                    public void success(SignUpResponse signUpResponse, Response response) {
                        pDialog.dismiss();
                        Log.d("signUpResponse", signUpResponse.getMessage());
                        Toast.makeText(ForgotPassword.this, signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (signUpResponse.getSuccess().equalsIgnoreCase("true")) {
                            Config.moveTo(ForgotPassword.this, MainActivity.class);
                            finishAffinity();
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

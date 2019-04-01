package com.asic45.neardeal28;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.asic45.neardeal28.api_responses.LoginResponse;
import com.asic45.neardeal28.api_services.ApiClient;
import com.asic45.neardeal28.api_services.ApiEndPoint;
import com.asic45.neardeal28.utils.PopupUtil;
import com.asic45.neardeal28.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.username)
    EditText usernameEditText;

    @BindView(R.id.password)
    EditText passwordEditText;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        sharedPrefManager = new SharedPrefManager(this);

        if(sharedPrefManager.getIsLoggedIn()){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }



        /*SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getKey(mKey), state);
        editor.commit();*/
    }

    @OnClick({R.id.btn_login})
    public void onClick(Button button) {
        PopupUtil.showLoading( this, "", "Please wait....");
        final String username = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        ApiEndPoint apiEndPoint = ApiClient.getClient(this).create(ApiEndPoint.class);
        Call<LoginResponse> call = apiEndPoint.login(username, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                PopupUtil.dismissDialog();
                final LoginResponse loginResponse = response.body();
                sharedPrefManager.setIsLoggedIn(sharedPrefManager.IS_LOGGED_IN,true);
                sharedPrefManager.setUserId(SharedPrefManager.USER_ID, String.valueOf(loginResponse.getUser().getIds()));
                if (loginResponse.getUser().getIds() != 0){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else {
                    PopupUtil.showMsg(LoginActivity.this, "User dan password salah", PopupUtil.SHORT);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                PopupUtil.dismissDialog();
                t.printStackTrace();
            }
        });
    }
}



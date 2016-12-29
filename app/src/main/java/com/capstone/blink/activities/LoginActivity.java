package com.capstone.blink.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.capstone.blink.R;
import com.capstone.blink.dialog.Dialog;
import com.capstone.blink.forms.Form;
import com.capstone.blink.forms.formTypes.LoginForm;
import com.capstone.blink.forms.formTypes.SignUpForm;
import com.capstone.blink.models.User;
import com.capstone.blink.network.APIInterface;
import com.capstone.blink.network.ApiManager;
import com.capstone.blink.network.NetworkCallback;
import com.capstone.blink.network.NetworkState;
import com.capstone.blink.network.requestData.AuthData;
import com.capstone.blink.network.responses.FailedRequestResponse;
import com.capstone.blink.network.responses.UserResponse;
import com.capstone.blink.sessionManager.SessionManager;
import com.capstone.blink.sharedPreferences.PreferenceKeys;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {


    private Intent intent;
    private Context mContext;
    private TextView signUpText;
    private ProgressBar progressBar;
    private String username, password;
    private Button loginButton, signUpButton;
    private EditText usernameField, passwordField;

    private boolean signIn = true;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();
        intent = new Intent(mContext, MainActivity.class);
        // Redirect to home page if logged in;
        if (SessionManager.isLoggedIn(mContext)) {
            startActivity(intent);
            finish();
        }
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        loginButton = (Button) findViewById(R.id.sign_in_button);
        signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpText = (TextView) findViewById(R.id.sign_up_text_view);
        usernameField = (EditText) findViewById(R.id.username_text_view);
        usernameField.setText(getIntent().getStringExtra(PreferenceKeys.USERNAME_INTENT_KEY));
        passwordField = (EditText) findViewById(R.id.login_password_et);

        handler.post(new Runnable() {
            @Override
            public void run() {
                setupLoginListener();
                setupSignUpListener();
                setupSignUpTextListener();
            }
        });
    }

    private void setupSignUpTextListener() {
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode();
            }
        });
    }

    private void changeMode() {
        if (signIn) {
            loginButton.setVisibility(View.GONE);
            signUpButton.setVisibility(View.VISIBLE);
            signUpText.setText(R.string.have_account_login);
        } else {
            loginButton.setVisibility(View.VISIBLE);
            signUpButton.setVisibility(View.GONE);
            signUpText.setText(R.string.no_account_question);
        }
        signIn ^= true;
    }

    private void setupSignUpListener() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameField.getText().toString().trim();
                password = passwordField.getText().toString().trim();
                if (NetworkState.isConnected()) {
                    Form form = new SignUpForm(username, password).validate();
                    if (form.isValid()) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.VISIBLE);
                                signUpButton.setVisibility(View.GONE);
                            }
                        }, 500);
                        APIInterface apiManager = ApiManager.getApiService();
                        Call<UserResponse> call = apiManager.createUser(
                                new AuthData(username, password, "")
                        );
                        ApiManager.request(call, new NetworkCallback<UserResponse>() {
                            @Override
                            public void onSuccess(UserResponse response) {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        signUpButton.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }, 500);
                                loginUser(response, true);
                            }

                            @Override
                            public void onFail(FailedRequestResponse t) {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        signUpButton.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }, 500);
                                final int responseCode = t.getCode();
                                switch (responseCode) {
                                    case 400:
                                        Dialog.makeToast(mContext, getString(R.string.username_taken));
                                        break;
                                }
                            }
                        });
                    } else {
                        Dialog.makeToast(mContext, getString(Form.getSimpleMessage(form.getReason())));
                    }

                } else {
                    Dialog.makeToast(mContext, getString(R.string.no_network));
                }
            }
        });
    }

    private void setupLoginListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameField.getText().toString().trim();
                password = passwordField.getText().toString().trim();
                if (NetworkState.isConnected()) {
                    Form form = new LoginForm(username, password).validate();
                    if (form.isValid()) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.VISIBLE);
                                loginButton.setVisibility(View.GONE);
                            }
                        }, 500);
                        APIInterface apiManager = ApiManager.getApiService();
                        Call<UserResponse> call = apiManager.getUser(
                                new AuthData(username, password)
                        );
                        ApiManager.request(call, new NetworkCallback<UserResponse>() {
                            @Override
                            public void onSuccess(UserResponse response) {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        loginButton.setVisibility(View.VISIBLE);
                                    }
                                }, 500);
                                loginUser(response, false);
                            }

                            @Override
                            public void onFail(FailedRequestResponse error) {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        loginButton.setVisibility(View.VISIBLE);
                                    }
                                }, 500);

                                final int responseCode = error.getCode();
                                switch (responseCode) {
                                    case 404:
                                        Dialog.makeToast(mContext, getString(R.string.user_not_found));
                                        break;
                                }
                            }
                        });
                    } else {
                        Dialog.makeToast(mContext, getString(Form.getSimpleMessage(form.getReason())));
                    }
                } else {
                    Dialog.makeToast(mContext, getString(R.string.no_network));
                }
            }
        });
    }

    private void loginUser(UserResponse response, final boolean isSignUp) {
        User user = response.getData();
        SessionManager.newSession(mContext, user);
        Dialog.makeToast(mContext, "Hello, " + response.getData().getUsername() + "!");
        startActivity(intent);
        finish();
    }


}

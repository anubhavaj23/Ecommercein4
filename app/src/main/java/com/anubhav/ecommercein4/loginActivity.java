package com.anubhav.ecommercein4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

public class loginActivity extends AppCompatActivity {
    EditText email,password;
    Button loginbutton,registerbutton;
    private CallbackManager callbackManager;
    ProfileTracker profileTracker;
    private LoginButton fbloginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

       // this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        loginbutton = (Button) findViewById(R.id.loginbutton);
        registerbutton = (Button) findViewById(R.id.registerbutton);
        final registeruser reguser = new registeruser(this);
        reguser.logoutusers();
        LoginManager.getInstance().logOut();
        fbloginButton = (LoginButton)findViewById(R.id.login_button);
        fbloginButton.setReadPermissions(Arrays.asList("email"));
        fbloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {


                profileTracker = new ProfileTracker() {

                    @Override
                    protected void onCurrentProfileChanged(
                            Profile oldProfile, Profile currentProfile) {
                        profileTracker.stopTracking();
                        Profile.setCurrentProfile(currentProfile);
                        final Profile profile = Profile.getCurrentProfile();

                        final String[] email = new String[1];

                        //Facebook email id
                        final GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        Log.v("LoginActivity Response ", response.toString());

                                        try {
                                            email[0] = object.getString("email");
                                            Intent intent = new Intent(loginActivity.this, settingsActivity.class);
                                            intent.putExtra("email", email[0]);
                                            intent.putExtra("name",profile.getName());
                                            startActivity(intent);
                                            finish();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "email");
                        request.setParameters(parameters);

                        request.executeAsync();

                    }
                };

            }

            @Override
            public void onCancel() {
                LoginManager.getInstance().logOut();
                Toast.makeText(loginActivity.this, "Login attempt canceled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(loginActivity.this, "Login attempt failed.", Toast.LENGTH_SHORT).show();
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkinput()) {
                    if (reguser.readUser(email.getText().toString().trim(), password.getText().toString().trim())) {
                        reguser.setloggedinstatus(email.getText().toString().trim());
                        finish();
                    } else {
                        Toast.makeText(loginActivity.this, "Wrong id/password", Toast.LENGTH_SHORT).show();
                        email.setText("");
                        password.setText("");
                    }
                }
            }
        });

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this,registerActivity.class));
            }
        });
    }

    boolean checkinput(){
        if(email.getText() == null || email.getText().toString().trim().equals(""))
            return false;
        else if (password.getText() == null || password.getText().toString().trim().equals(""))
            return false;
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

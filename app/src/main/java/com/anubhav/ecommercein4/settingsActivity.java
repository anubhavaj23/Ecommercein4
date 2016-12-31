package com.anubhav.ecommercein4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;

/**
 * Created by anubh on 14-Dec-16.
 */

public class settingsActivity extends AppCompatActivity {
    registeruser reguser;
    EditText name,email,mobileno,password;
    Button savechangesbutton,cancelbutton,changeaddressbutton;
    Users user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        mobileno = (EditText) findViewById(R.id.mobileno);
        password = (EditText) findViewById(R.id.password);
        savechangesbutton = (Button)findViewById(R.id.savechangesbutton);
        cancelbutton = (Button) findViewById(R.id.cancelbutton);
        changeaddressbutton = (Button) findViewById(R.id.changeaddressButton);
        reguser = new registeruser(this);
        user = reguser.getloggedinuser();
        if(getCallingActivity() != null)
            if(getCallingActivity().getClassName().equalsIgnoreCase("com.anubhav.ecommercein4.loginActivity"))
                changeaddressbutton.setVisibility(View.GONE);

        if(user!=null){
            email.setText(user.getEmail());
            mobileno.setText(user.getMobileno());

            mobileno.setPressed(false);
            mobileno.setFocusable(false);
            mobileno.setCursorVisible(false);
            mobileno.setKeyListener(null);

            email.setFocusable(false);
            email.setKeyListener(null);
            email.setCursorVisible(false);
            email.setPressed(false);
        }
        else{
            name.setText(getIntent().getExtras().getString("name"));
            email.setText(getIntent().getExtras().getString("email"));
        }

        savechangesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mobileno.getKeyListener() != null) {
                    if (checkinputforadd()) {
                        Users user = new Users(name.getText().toString().trim(), email.getText().toString().trim(), mobileno.getText().toString().trim(), password.getText().toString().trim());
                        if (reguser.addUser(user)) {
                            Toast.makeText(settingsActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                            reguser.setloggedinstatus(user.getEmail());
                            startActivity(new Intent(getApplicationContext(), loginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(settingsActivity.this, "Error, try again", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            email.setText("");
                            mobileno.setText("");
                            password.setText("");
                        }
                    }
                }
                else {
                    if(checkinputforupd()){
                        if(name.getText().toString().trim().equals("")){
                            if(reguser.updateuser(new Users(user.getName(),email.getText().toString().trim(),mobileno.getText().toString().trim(),password.getText().toString().trim()),user))
                                Toast.makeText(settingsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(settingsActivity.this, "Loss", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            if(reguser.updateuser(new Users(user.getName(),email.getText().toString().trim(),mobileno.getText().toString().trim(),password.getText().toString().trim()),user))
                                Toast.makeText(settingsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(settingsActivity.this, "Loss", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button backbutton = (Button) findViewById(R.id.backButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        changeaddressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(settingsActivity.this,addressActivity.class),1);
            }
        });
    }

    boolean checkinputforadd(){
        if(name.getText() == null || name.getText().toString().trim().equals(""))
            return false;
        else if (email.getText() == null || email.getText().toString().trim().equals(""))
            return false;
        else if(mobileno.getText() == null || mobileno.getText().toString().trim().equals(""))
            return false;
        else if(password.getText() == null || password.getText().toString().trim().equals(""))
            return false;

        return true;
    }

    boolean checkinputforupd(){
        if(password.getText().toString().trim().equals(""))
            return false;
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1){
            Toast.makeText(this, "Address Changed Successfully", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Address not set", Toast.LENGTH_SHORT).show();
    }
}
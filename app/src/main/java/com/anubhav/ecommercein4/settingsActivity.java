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
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;

/**
 * Created by anubh on 14-Dec-16.
 */

public class settingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    registeruser reguser;
    EditText name,email,mobileno,password;
    Button savechangesbutton,cancelbutton;
    Users user;
    ProfileTracker profileTracker;
    Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        mobileno = (EditText) findViewById(R.id.mobileno);
        password = (EditText) findViewById(R.id.password);
        savechangesbutton = (Button)findViewById(R.id.savechangesbutton);
        cancelbutton = (Button) findViewById(R.id.cancelbutton);
        reguser = new registeruser(this);
        user = reguser.getloggedinuser();

        /*profileTracker = new ProfileTracker() {

            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile, Profile currentProfile) {
                profileTracker.stopTracking();
                Profile.setCurrentProfile(currentProfile);
                profile = Profile.getCurrentProfile();

            }
        };*/
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
                            name.setText("1");
                            if(reguser.updateuser(new Users(user.getName(),email.getText().toString().trim(),mobileno.getText().toString().trim(),password.getText().toString().trim()),user))
                                Toast.makeText(settingsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(settingsActivity.this, "Loss", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            name.setText("2");
                            if(reguser.updateuser(new Users(user.getName(),email.getText().toString().trim(),mobileno.getText().toString().trim(),password.getText().toString().trim()),user))
                                Toast.makeText(settingsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(settingsActivity.this, "Loss", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
                //startActivity(new Intent(settingsActivity.this,orderdetailActivity.class));
            }
        });
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent i;
        if (id == R.id.settings) {

            i = new Intent(this,settingsActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.myorder) {

            i = new Intent(this,orderlistActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.aboutus) {

            i = new Intent(this,aboutusActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.ourpolicy) {

            i = new Intent(this,ourpolicyActivity.class);
            startActivity(i);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

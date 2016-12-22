package com.anubhav.ecommercein4;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class orderdetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout changeaddressbutton;
    TextView amount,totalamount;
    Button proceedbutton;
    UserAddress userAddress;
    registeruser reguser;
    Intent intent;
    String commodityname,quantity;
    float billamount;
    public static orderdetailActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activity = this;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        changeaddressbutton = (LinearLayout) findViewById(R.id.changeaddressButton);
        amount = (TextView) findViewById(R.id.amount);
        totalamount = (TextView) findViewById(R.id.totalamount);
        proceedbutton = (Button) findViewById(R.id.proceedButton);
        userAddress = new UserAddress(this);
        reguser = new registeruser(this);
        commodityname = getIntent().getExtras().getString("commodityname");
        billamount = Integer.parseInt(getIntent().getExtras().getString("totalcost"));
        quantity = getIntent().getExtras().getString("quantity");
        amount.setText(billamount+"");
        totalamount.setText(billamount+"");
        intent = new Intent(this,paymentActivity.class);

        proceedbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userAddress.getAddress(reguser.getloggedinuser().getEmail())!=null){
                    intent.putExtra("commodityname",commodityname);
                    intent.putExtra("quantity",quantity);
                    intent.putExtra("totalamount",billamount+"");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(orderdetailActivity.this, reguser.getloggedinuser().getEmail()+"\nAddress not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        changeaddressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(orderdetailActivity.this,addressActivity.class));
            }
        });
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
        getMenuInflater().inflate(R.menu.searchcart_menu, menu);
        return true;
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
        } else if (id == R.id.myorder) {

            i = new Intent(this,orderlistActivity.class);
            startActivity(i);
        } else if (id == R.id.aboutus) {

            i = new Intent(this,aboutusActivity.class);
            startActivity(i);
        } else if (id == R.id.ourpolicy) {

            i = new Intent(this,ourpolicyActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

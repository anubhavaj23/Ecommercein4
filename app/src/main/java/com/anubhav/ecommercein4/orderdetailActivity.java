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

public class orderdetailActivity extends AppCompatActivity {

    LinearLayout changeaddressbutton,addresslayout;
    TextView amount,totalamount,address;
    Button proceedbutton,changeaddresstext;
    UserAddress userAddress;
    registeruser reguser;
    Intent intent;
    String commodityname,quantity;
    float billamount;
    public static orderdetailActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.transition.enter,R.transition.exit);
        setContentView(R.layout.activity_orderdetail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activity = this;

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
        address = (TextView) findViewById(R.id.address);
        changeaddresstext = (Button) findViewById(R.id.changeaddresstext);
        addresslayout = (LinearLayout) findViewById(R.id.linearlayout4);
        if(userAddress.getAddress(reguser.getloggedinuser().getEmail()) != null)
            address.setText("Address Details :\n\n" + userAddress.getAddress(reguser.getloggedinuser().getEmail()));
        else {
            addresslayout.setVisibility(View.INVISIBLE);
            changeaddresstext.setText("Add Address");
        }

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
                    Toast.makeText(orderdetailActivity.this,"Address not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        changeaddressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(orderdetailActivity.this,addressActivity.class),1);
            }
        });
        Button backbutton = (Button) findViewById(R.id.backButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.left_to_right,R.transition.right_to_left);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1){
            addresslayout.setVisibility(View.VISIBLE);
            address.setText("Address Details :\n\n" + userAddress.getAddress(reguser.getloggedinuser().getEmail()));
        }
        else
            Toast.makeText(activity, "address not set", Toast.LENGTH_SHORT).show();
    }
}
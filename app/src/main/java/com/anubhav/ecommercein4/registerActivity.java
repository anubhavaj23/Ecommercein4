package com.anubhav.ecommercein4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class registerActivity extends AppCompatActivity {
    EditText name,email,mobileno,password;
    Button registerbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        mobileno = (EditText) findViewById(R.id.mobileno);
        password = (EditText) findViewById(R.id.password);
        registerbutton = (Button) findViewById(R.id.registerbutton);
        final registeruser reguser = new registeruser(this);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkinput()){
                    Users user = new Users(name.getText().toString().trim(),email.getText().toString().trim(),mobileno.getText().toString().trim(),password.getText().toString().trim());
                    if(reguser.addUser(user)) {
                        Toast.makeText(registerActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), loginActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(registerActivity.this, "Error, try again", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        email.setText("");
                        mobileno.setText("");
                        password.setText("");
                    }
                }
            }
        });

    }

    boolean checkinput(){
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
}

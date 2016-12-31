package com.anubhav.ecommercein4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        Button backbutton = (Button) findViewById(R.id.backButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    boolean checkinput(){
        if(name.getText().toString().trim().equals("")&&
                (email.getText().toString().trim().equals(""))&&
                (mobileno.getText().toString().trim().equals("") || mobileno.getText().toString().trim().length()!=10)&&
                password.getText().toString().trim().equals("")){
            Toast.makeText(registerActivity.this, "Enter input", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(new Validator().validateemail(email.getText().toString().trim()));
        else{
            Toast.makeText(registerActivity.this, "Enter a valid email", Toast.LENGTH_LONG).show();
            return false;
        }
        if(new Validator().validatemobileno(mobileno.getText().toString().trim()));
        else{
            Toast.makeText(registerActivity.this, "Enter a valid Mobile Number", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}

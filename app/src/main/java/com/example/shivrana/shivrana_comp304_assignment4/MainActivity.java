package com.example.shivrana.shivrana_comp304_assignment4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    EditText userName,password;
    RadioButton usrRadioButton,AdminRadioButton;
    Button logInButton, registrationButton;
    Intent intent;
    Intent logIn;
    Intent adminPage;

    //For the Database
    private DatabaseModel databaseModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        declaration();


        try{
            databaseModel = new DatabaseModel(this);
            //TODO Uncomment these if you need
//            databaseModel.setAdmin();
//            databaseModel.setItems();
        }
        catch (Exception exception){
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error",exception.getMessage());
        }

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usrRadioButton.isChecked()){
                    int id =databaseModel.checkValidUser("Customer",userName.getText().toString(),password.getText().toString());
                    if(id ==999){
                        Toast.makeText(MainActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        SharedPreferences.Editor editor = getSharedPreferences("Data",MODE_PRIVATE).edit();
                        editor.putString("userName",userName.getText().toString());
                        editor.putString("password",password.getText().toString());
                        editor.apply();

                        Toast.makeText(MainActivity.this, "Welcome User ", Toast.LENGTH_SHORT).show();

                        logIn.putExtra("cId",String.valueOf(id));
                        startActivity(logIn);

                    }

                }
                else{
                    if(databaseModel.checkValidAdmin("OrderRep",userName.getText().toString(),password.getText().toString())){
                        SharedPreferences.Editor editor = getSharedPreferences("Data",MODE_PRIVATE).edit();
                        editor.putString("AdminName",userName.getText().toString());
                        editor.putString("AdminPass",password.getText().toString());
                        editor.apply();
                        Toast.makeText(MainActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();

                        startActivity(adminPage);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Invalid Admin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }

    public void declaration(){
        radioGroup = findViewById(R.id.usrRadioGrp);
        userName = findViewById(R.id.usrNameTxtFIeld);
        password = findViewById(R.id.usrPassTxtFIeld);
        logInButton = findViewById(R.id.logInBtn);
        registrationButton = findViewById(R.id.mainRegistrationBtn);
        usrRadioButton = findViewById(R.id.Customer);
        AdminRadioButton = findViewById(R.id.Admin);
        intent = new Intent(this,RegistrationActivity.class);
        logIn = new Intent(this,OrderActivity.class);
        adminPage = new Intent(this,AdminActivity.class);

    }

}

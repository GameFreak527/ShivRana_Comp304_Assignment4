package com.example.shivrana.shivrana_comp304_assignment4;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    DatabaseModel databaseModel;
    private static final String TableName = "Customer";
    Customer customer;
    EditText userName,password,fName,lName,address,postalCode;
    Button registrationButton;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        declaration();
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if user is entering any value empty
                if(userName.getText().toString().length() == 0 || password.getText().toString().length() == 0 || fName.getText().toString().length() == 0 || lName.getText().toString().length() == 0 ||
                address.getText().toString().length() == 0 || postalCode.getText().toString().length() == 0 ){
                    Toast.makeText(RegistrationActivity.this, "Please Fill the Fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Adding all the input to the customer object
                    customer = new Customer(userName.getText().toString(),
                            password.getText().toString(),
                            fName.getText().toString(),
                            lName.getText().toString(),
                            address.getText().toString(),
                            postalCode.getText().toString());

                    //Storing user information into the Customer Table
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("userName",customer.getUserName());
                    contentValues.put("password",customer.getPassword());
                    contentValues.put("firstName",customer.getFirstName());
                    contentValues.put("lastName",customer.getLastName());
                    contentValues.put("address",customer.getAddress());
                    contentValues.put("postalCode",customer.getPostalCode());
                    try{
                        //Adding a row into the Customer Table

                        boolean result = databaseModel.addRow(contentValues,TableName);
                        if(result){
                            userName.setText("");
                            password.setText("");
                            fName.setText("");
                            lName.setText("");
                            address.setText("");
                            postalCode.setText("");
                            Toast.makeText(RegistrationActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception exception){
                        Toast.makeText(RegistrationActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("Error",exception.getMessage());
                    }
                }
            }
        });


    }

    public void declaration(){
        userName = findViewById(R.id.regUsrName);
        password = findViewById(R.id.regPassword);
        fName = findViewById(R.id.regFirstName);
        lName = findViewById(R.id.regLastName);
        postalCode = findViewById(R.id.regPostalCode);
        address = findViewById(R.id.regAddress);
        registrationButton = findViewById(R.id.registerBtn);
        intent = new Intent(this,MainActivity.class);
        databaseModel = new DatabaseModel(this);

    }
}

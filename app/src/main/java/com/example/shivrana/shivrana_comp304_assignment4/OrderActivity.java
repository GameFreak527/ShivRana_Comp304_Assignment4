package com.example.shivrana.shivrana_comp304_assignment4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class OrderActivity extends AppCompatActivity {
    //TODO Completed 01  Change the password type of the main activity
    Button orderPlaceButton,CustomerOrders;
    DatabaseModel databaseModel;
    String name,category;
    Intent intent;
    String CustomerId;
    TextView itemPrice;
    int[] itemData = new int[2];
    ListView orderList;
    Spinner quantity,itemCategory,itemName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        declaration();

        //Adding quantity values from the array
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adp.addAll(getResources().getStringArray(R.array.quantity));
        quantity.setAdapter(adp);

        //Adding Category from the Database
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        adp2.addAll(databaseModel.getItemCategory());
        itemCategory.setAdapter(adp2);

        //Adding ItemName to the Spinner on the base of the Category selection
        itemCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                populateSpinners(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        itemName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemData = databaseModel.getItemId(parent.getSelectedItem().toString());
                itemPrice.setText(String.valueOf(itemData[1]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        orderPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = itemCategory.getSelectedItem().toString();
                name = itemName.getSelectedItem().toString();
                int price = itemData[1]* Integer.parseInt( quantity.getSelectedItem().toString());
                Calendar calendar = Calendar.getInstance();
                int cDay = calendar.get(Calendar.DAY_OF_MONTH)+7;
                int cMonth = calendar.get(Calendar.MONTH) + 1;
                int cYear = calendar.get(Calendar.YEAR);
                String date = String.format("%d-%d-%d",cDay,cMonth,cYear);
                try{

                    databaseModel.addOrder(itemData[0],Integer.parseInt(CustomerId),price,date);
                }
                catch (Exception exception){
                    Toast.makeText(OrderActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
                orderPlaceButton.setVisibility(View.INVISIBLE);
                Toast.makeText(OrderActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();

                ArrayAdapter<String> adp4 = new ArrayAdapter<>(OrderActivity.this,android.R.layout.simple_list_item_1);
                adp4.add(String.format("%s \t %s \t %s","Category","Name","Price"));
                adp4.add(String.format("%s \t %s \t %d",category,name,price));
                orderList.setAdapter(adp4);

            }
        });

        CustomerOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CustomerId",CustomerId);
                startActivity(intent);
            }
        });
    }


    public void populateSpinners(String categoryName){
        ArrayAdapter<String> adp3 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        adp3.addAll(databaseModel.getItemName(categoryName));
        itemName.setAdapter(adp3);
    }

    public void declaration(){
        intent = new Intent(this,CustomerOrdersActivity.class);
        CustomerOrders = findViewById(R.id.checkAllOrders);
        CustomerId = getIntent().getStringExtra("cId");
        orderPlaceButton = findViewById(R.id.placeOrderButton);
        orderList = findViewById(R.id.OrderList);
        itemCategory = findViewById(R.id.categoryList);
        itemName = findViewById(R.id.itemNameList);
        quantity = findViewById(R.id.quantitySpinner);
        databaseModel = new DatabaseModel(this);
        itemPrice = findViewById(R.id.itemPrice);
    }


}

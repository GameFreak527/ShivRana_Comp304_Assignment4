package com.example.shivrana.shivrana_comp304_assignment4;

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

import java.util.ArrayList;

public class CustomerOrdersActivity extends AppCompatActivity {
    int CustomerId;
    String itemCategory,itemName;
    String selectedOrder;
    String orderStatus;
    int OrderId;
    DatabaseModel databaseModel;
    ListView showAllOrders;
    //ItemData[0] is itemId
    //ItemData[1] is Item Price
    int[] itemData = new int[2];
    TextView orderInformation;
    Button changeOrder,cancelOrder;
    Spinner orderCategory,orderName,orderQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);
        declaration();

        populateListView();
        listViewEvents();
        populatingSpinners();
        SpinnerEvents();
        buttonPressEvents();
    }

    public void declaration(){
        orderCategory = findViewById(R.id.reOrderCategory);
        orderInformation = findViewById(R.id.orderInformation);
        changeOrder = findViewById(R.id.modifyOrder);
        cancelOrder = findViewById(R.id.orderCancel);
        orderInformation = findViewById(R.id.orderInformation);
        orderName = findViewById(R.id.reOrderItemName);
        orderQuantity = findViewById(R.id.reOrderQuantity);
        databaseModel = new DatabaseModel(this);
        CustomerId = Integer.parseInt(getIntent().getStringExtra("CustomerId"));
        showAllOrders = findViewById(R.id.showAllOrders);

        //Setting the Spinners Invisible in the beginning of the Activity
        orderCategory.setVisibility(View.INVISIBLE);
        orderName.setVisibility(View.INVISIBLE);
        orderQuantity.setVisibility(View.INVISIBLE);
    }

    public void populateListView(){
        ArrayAdapter<Order> OrderList = new ArrayAdapter<Order>(this,android.R.layout.simple_list_item_1);
        OrderList.addAll(databaseModel.showOrderbyCustomer(CustomerId));
        showAllOrders.setAdapter(OrderList);
    }

    public void listViewEvents(){
        showAllOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOrder = parent.getItemAtPosition(position).toString() ;
                orderInformation.setText(selectedOrder);

                char[] orderStatusHold = new char[15];
                selectedOrder.getChars(155,selectedOrder.length(),orderStatusHold,0);
                orderStatus = String.valueOf(orderStatusHold);
                orderStatus = orderStatus.trim();


                char[] valueHold = new char[1];
                selectedOrder.getChars(29,30,valueHold,0);
                OrderId = Integer.parseInt(String.valueOf(valueHold[0]));

                orderCategory.setVisibility(View.VISIBLE);
                orderName.setVisibility(View.VISIBLE);
                orderQuantity.setVisibility(View.VISIBLE);
            }
        });
    }

    public void SpinnerEvents(){
        orderCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                populateItemNameSpinner(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        orderName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemData = databaseModel.getItemId(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void populateItemNameSpinner(String categoryName){
        ArrayAdapter<String> adp3 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        adp3.addAll(databaseModel.getItemName(categoryName));
        orderName.setAdapter(adp3);
    }

    public void populatingSpinners(){

        //Quantity Spinner Populated
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adp.addAll(getResources().getStringArray(R.array.quantity));
        orderQuantity.setAdapter(adp);

        //Adding Category from the Database
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        adp2.addAll(databaseModel.getItemCategory());
        orderCategory.setAdapter(adp2);
    }

    public void buttonPressEvents(){
        changeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(orderStatus.equalsIgnoreCase("DELIVERED")){
                    Toast.makeText(CustomerOrdersActivity.this, "Order is already Delivered", Toast.LENGTH_SHORT).show();
                }
                else{
                    int price = itemData[1]* Integer.parseInt( orderQuantity.getSelectedItem().toString());
                    if(databaseModel.updateOrder(OrderId,itemData[0],price)){
                        populateListView();
                        Toast.makeText(CustomerOrdersActivity.this, "Your Order Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if the orderStatus is pending then it means it is 0(Which means true)
                if(orderStatus.equalsIgnoreCase("DELIVERED")){
                    Toast.makeText(CustomerOrdersActivity.this, "Order is already Delivered", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Delteing the selected Order from the database
                    databaseModel.deleteOrder(OrderId);
                    populateListView();
                    Toast.makeText(CustomerOrdersActivity.this, "Order : "+OrderId+" Deleted", Toast.LENGTH_SHORT).show();
                }
                }
        });

    }
}

package com.example.shivrana.shivrana_comp304_assignment4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    DatabaseModel databaseModel;
    TextView orderInformation;
    EditText orderStatus;
    ListView ordersList;
    int OrderId;
    Button statusChangeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        declaration();
        populatingListView();
        listViewEvents();
        buttonClickEvent();
    }

    public void populatingListView(){
        ArrayAdapter<Order> OrderList = new ArrayAdapter<Order>(this,android.R.layout.simple_list_item_1);
        OrderList.addAll(databaseModel.showAllOrders());
        ordersList.setAdapter(OrderList);
    }

    public void declaration(){
        orderInformation = findViewById(R.id.adminOrderInformation);
        statusChangeButton = findViewById(R.id.changeOrderStatusButton);
        databaseModel = new DatabaseModel(this);
        orderStatus = findViewById(R.id.changeOrderStatus);
        ordersList = findViewById(R.id.adminOrderList);
    }

    public void buttonClickEvent(){
        statusChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Updating the status of the Order
                String status;
                if(orderStatus.getText().toString().startsWith("D") || orderStatus.getText().toString().startsWith("d")){
                    status = "DELIVERED";
                    databaseModel.updateOrderStatus(OrderId,status);
                }
                else if(orderStatus.getText().toString().startsWith("P") || orderStatus.getText().toString().startsWith("p")){
                    status = "PENDING";
                    databaseModel.updateOrderStatus(OrderId,status);
                }
                else{
                    Toast.makeText(AdminActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                }
                populatingListView();
            }
        });
    }

    public void listViewEvents(){
        ordersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedOrder = parent.getItemAtPosition(position).toString();
                orderInformation.setText(selectedOrder);

                //Mining the Order id from the selected item from the List
                char[] valueHold = new char[1];
                selectedOrder.getChars(29,30,valueHold,0);
                OrderId = Integer.parseInt(String.valueOf(valueHold[0]));
            }
        });
    }
}

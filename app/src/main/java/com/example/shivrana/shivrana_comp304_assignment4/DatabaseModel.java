package com.example.shivrana.shivrana_comp304_assignment4;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseModel extends SQLiteOpenHelper{
   static boolean result;
   static int id;
   Customer customer;

   enum Status{
       PENDING,DELIVERED
   }

    private static final  String DATABASE_NAME = "FinalDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static String Table_Name = "Customer";
    private static String createStatement ="CREATE TABLE `Customer` ( `CustomerId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `userName` TEXT NOT NULL, `password` TEXT NOT NULL, `firstName` TEXT NOT NULL, `lastName` TEXT, `address` TEXT NOT NULL, `postalCode` TEXT NOT NULL )";

    private final static String Table_Name2="Item";
    private final static String createStatement2 ="CREATE TABLE `Item` ( `itemId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `itemName` TEXT, `price` INTEGER, `category` TEXT )";

    private final static String Table_Name3="Orders";
    private final static String createStatement3=  "CREATE TABLE "+ Table_Name3+ " (orderId integer NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, itemId integer, customerId integer, amount integer, DeliveryDate text, " +
            " status text);";

    private final static String Table_Name4= "OrderRep";
    private final static String createStatement4= "CREATE TABLE `OrderRep` ( `employeeId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `userName` TEXT NOT NULL, `password` TEXT NOT NULL, `firstName` TEXT NOT NULL, `lastName` TEXT )";


    public DatabaseModel(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        customer = new Customer();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createStatement);
        db.execSQL(createStatement2);
        db.execSQL(createStatement3);
        db.execSQL(createStatement4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //If table already exist
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name3);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name2);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name4);

        //Recreate table
        onCreate(db);

        //Adding preDefined values to the Database
//        setItems();
//        setAdmin();
    }

    public boolean addRow(ContentValues values,String tableName) throws Exception{
        SQLiteDatabase db = this.getWritableDatabase();
        //Inserting Row
        long nextRow = db.insert(tableName,null,values);
        db.close();
        return nextRow> -1;
    }

    public boolean checkValidAdmin(String table_Name,String userName, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        //Checking if the user is valid or not
        Cursor cursor = db.rawQuery("Select * from "+table_Name,null);
        if (cursor.moveToFirst()){
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()){
                String usrName = cursor.getString(cursor.getColumnIndex("userName"));
                String pass = cursor.getString(cursor.getColumnIndex("password"));
                if((usrName.equalsIgnoreCase(userName))  && (pass.equalsIgnoreCase(password)) ){
                    result = true;
                    break;
                }
                else {
                    result = false;
                }
            }
            cursor.close();
            }
        return  result;
    }

    public int checkValidUser(String table_Name,String userName, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        //Checking if the user is valid or not
        Cursor cursor = db.rawQuery("Select * from "+table_Name,null);
        if (cursor.moveToFirst()){
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()){
                String usrName = cursor.getString(cursor.getColumnIndex("userName"));
                String pass = cursor.getString(cursor.getColumnIndex("password"));
                if((usrName.equalsIgnoreCase(userName)) && (pass.equalsIgnoreCase(password)) ){
                    id = cursor.getInt(cursor.getColumnIndex("CustomerId"));
                    break;
                }
                else {
                   id = 999;
                }
            }
            cursor.close();
        }
        return  id;
    }



    public ArrayList<String> getItemCategory(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> categoryData  = new ArrayList<String>();
        //Fetching all the item categories from the Item table
        Cursor cursor = db.rawQuery("select DISTINCT category from Item",null);
        if(cursor.moveToFirst()){
            cursor.moveToPosition(-1); // So that cursor wont skip the first Row
            while (cursor.moveToNext()){
                categoryData.add(cursor.getString(cursor.getColumnIndex("category")));
            }
            cursor.close();
        }
        return categoryData;
    }

    public ArrayList<Customer> showallCustomers(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Customer> allCustomer = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from Customer",null);
        if(cursor.moveToFirst()){
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()){
               allCustomer.add(new Customer(
                       cursor.getInt(cursor.getColumnIndex("CustomerId")),
                       cursor.getString(cursor.getColumnIndex("userName")),
                       cursor.getString(cursor.getColumnIndex("password")),
                       cursor.getString(cursor.getColumnIndex("firstName")),
                       cursor.getString(cursor.getColumnIndex("lastName")),
                       cursor.getString(cursor.getColumnIndex("address")),
                       cursor.getString(cursor.getColumnIndex("postalCode"))
               ));
            }
        }
        cursor.close();
        return  allCustomer;
    }

    public ArrayList<Item> showallItems(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Item> allItems = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from Item",null);
        if(cursor.moveToFirst()){
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()){
                allItems.add(new Item(
                        cursor.getInt(cursor.getColumnIndex("itemId")),
                        cursor.getString(cursor.getColumnIndex("itemName")),
                        cursor.getInt(cursor.getColumnIndex("price")),
                        cursor.getString(cursor.getColumnIndex("category"))
                ));
            }
        }
        cursor.close();
        return  allItems;
    }

    public ArrayList<String> getItemName(String category){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> itemNameData = new ArrayList<String>();
        itemNameData.clear();

        Cursor cursor = db.rawQuery("select itemName from Item where category ='"+category+"'",null);
        if(cursor.moveToFirst()){
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()){
                String data = cursor.getString(cursor.getColumnIndex("itemName"));
                itemNameData.add(cursor.getString(cursor.getColumnIndex("itemName")));
            }
        }
        cursor.close();
        return  itemNameData;
    }

    public void setAdmin(){
        ContentValues contentValues = new ContentValues();
        for (int i=0; i<5;i++){
            contentValues.put("userName","Admin"+i);
            contentValues.put("password","admin");
            contentValues.put("firstName","Admin");
            contentValues.put("lastName",i);
            try{
                addRow(contentValues,Table_Name4);
            }
            catch (Exception exception){
                Log.i("Error message",exception.getMessage());
            }
        }
    }

    public void setItems(){
        //Item 1
        addItemsContents("Mouse",10,"Wired");
        //Item 2
        addItemsContents("Keyboard",20,"Wired");
        //Item 3
        addItemsContents("Monitor",40,"Wired");
        //Item 4
        addItemsContents("Wireless Mouse",20,"Wireless");
        //Item 5
        addItemsContents("Wireless Keyboard",30,"Wireless");
        //Item 6
        addItemsContents("Wireless Monitor",50,"Wireless");

    }

    public void addItemsContents(String itemName,int price,String Category){
        ContentValues contentValues = new ContentValues();
        contentValues.put("itemName",itemName);
        contentValues.put("price",price);
        contentValues.put("category",Category);
        try{
            addRow(contentValues,Table_Name2);
        }
        catch (Exception exception){
            Log.i("Error message",exception.getMessage());
        }
    }

    public int[] getItemId(String itemName){
        int itemId[]=new int[2];
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select itemId,price from Item where itemName ='"+itemName+"'",null);
        if(cursor.moveToNext()){
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()){
                itemId[0] = cursor.getInt(cursor.getColumnIndex("itemId"));
                itemId[1] = cursor.getInt(cursor.getColumnIndex("price"));
            }
        }
        cursor.close();
        return itemId;

    }

    public void addOrder(int itemId, int customerId, int amount,String deliveryDate){
        ContentValues contentValues = new ContentValues();
        contentValues.put("itemId",itemId);
        contentValues.put("customerId",customerId);
        contentValues.put("amount",amount);
        contentValues.put("deliveryDate",deliveryDate);
        contentValues.put("status",Status.PENDING.toString());
        try{
            addRow(contentValues,Table_Name3);
        }
        catch (Exception exception){
            Log.i("Error message",exception.getMessage());
        }

    }

    public ArrayList<Order> showOrderbyCustomer(int customerId){
        ArrayList<Order> orderList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select orderId,itemId,amount,deliveryDate,status from "+Table_Name3+" where customerId ='"+customerId+"'",null);
        if(cursor.moveToFirst()){
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()){
                orderList.add(new Order(
                        cursor.getInt(cursor.getColumnIndex("orderId")),
                        cursor.getInt(cursor.getColumnIndex("itemId")),
                        cursor.getInt(cursor.getColumnIndex("amount")),
                        cursor.getString(cursor.getColumnIndex("DeliveryDate")),
                        cursor.getString(cursor.getColumnIndex("status"))
                ));

            }
        }
        cursor.close();
        return  orderList;
    }

    public boolean updateOrder(int orderId,int itemId,int price){
        ContentValues contentValues = new ContentValues();
        contentValues.put("orderId",orderId);
        contentValues.put("itemId",itemId);
        contentValues.put("amount",price);
        SQLiteDatabase db = this.getWritableDatabase();
        //Updating Row
        try{
            db.update(Table_Name3,contentValues,"orderId='"+orderId+"'",null);
        }
        catch (Exception exception){
            Log.i("Error Message",exception.getMessage());
        }
        db.close();
        return  true;
    }

    public void deleteOrder(int orderId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
             db.delete(Table_Name3, "orderId ="+ orderId, null) ;
        }
        catch (Exception exception){
            Log.i("Error Message",exception.getMessage());
        }
    }

    public ArrayList<Order> showAllOrders(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Order> allOrders = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from "+Table_Name3,null);
        if(cursor.moveToFirst()){
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()){
                allOrders.add(new Order(
                        cursor.getInt(cursor.getColumnIndex("orderId")),
                        cursor.getInt(cursor.getColumnIndex("itemId")),
                        cursor.getInt(cursor.getColumnIndex("amount")),
                        cursor.getString(cursor.getColumnIndex("DeliveryDate")),
                        cursor.getString(cursor.getColumnIndex("status"))
                ));
            }
        }
        cursor.close();
        return allOrders;
    }

    public void updateOrderStatus(int orderId, String status){
        ContentValues contentValues = new ContentValues();
        contentValues.put("status",status);
        SQLiteDatabase db = this.getWritableDatabase();
        //Updating Row
        try{
            db.update(Table_Name3,contentValues,"orderId='"+orderId+"'",null);
        }
        catch (Exception exception){
            Log.i("Error Message",exception.getMessage());
        }
        db.close();
    }

}

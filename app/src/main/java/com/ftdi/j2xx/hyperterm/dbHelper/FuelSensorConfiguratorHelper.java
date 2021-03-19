package com.ftdi.j2xx.hyperterm.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.ftdi.j2xx.hyperterm.dbHelper.Tables.Mastertables.tbl_fuel_configurator;
import static com.ftdi.j2xx.hyperterm.J2xxHyperTerm.isDataExistInsideTable;

public class FuelSensorConfiguratorHelper extends SQLiteOpenHelper
{
    SQLiteDatabase db;
    String TAG = getClass().getSimpleName();

    public FuelSensorConfiguratorHelper (Context context)
    {
        super(context, "dbHelper", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE tbl_fuel_configurator(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "sid TEXT," +
                "eventtype TEXT," +
                "datetime TEXT," +
                "data TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+ tbl_fuel_configurator);
        onCreate(db);
    }

    public void open()
    {
        db = getWritableDatabase();
    }

    public void close()
    {
        db.close();
    }

    public long addFuelSensorDetails(String sid,long date, String eventType ,JSONArray data)
    {
        Log.e(TAG, "Inside addFuelSensorDetails: "+"Date : "+date +"sid : "+sid+ "eventType : "+eventType+" , Data : "+data);
        long x = 0;
        String dataStr = data.toString();
        try
        {
            this.open();
            ContentValues cv = new ContentValues();
            cv.put("sid", sid);
            cv.put("datetime", date);
            cv.put("eventtype", eventType);
            cv.put("data",dataStr);
            Log.e(TAG, "addFuelSensorDetails cv: "+cv);
            x = db.insert("tbl_fuel_configurator", null, cv);
            this.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("***********Error **********"+ e.getMessage());
        }

        return x;
    }


    public void clearProductDetails()
    {
        Log.e(TAG, "Inside clearProductDetails: ");
        try
        {
            this.open();
            db.execSQL("delete from tbl_fuel_configurator");
            this.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteUploadedConfiguredData(String sid,long dateTime)
    {
        Log.e(TAG, "Inside deleteUploadedConfiguredData: "+"sid : "+sid+" dateTime : "+ dateTime);
        try
        {
            this.open();
            db.execSQL("delete from "+ tbl_fuel_configurator +" where sid = '"+sid+"' and datetime = '"+dateTime+"'");
            this.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.e(TAG, "deleteUploadedConfiguredData Exception : "+e.getMessage());
        }
    }

    public JSONObject fuelConfiguratorDetails()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("isRecordFound",false);
            int start = 1;
            this.open();
            ////Cursor c = db.rawQuery("select * from tbl_fuel_configurator where cust_name="+custName+" and Status=1 Order by ID DESC",null); //
            Cursor c = db.rawQuery("select * from tbl_fuel_configurator  Order by ID DESC LIMIT 1",null);

            int date = c.getColumnIndex("datetime");
            int sid = c.getColumnIndex("sid");
            int eventType = c.getColumnIndex("eventtype");
            int data = c.getColumnIndex("data");

            if(c!=null)
            {
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                {
                    Log.e(TAG, "fuelConfiguratorDetails getDate : "+c.getString(date));
                    Log.e(TAG, "fuelConfiguratorDetails sId : "+c.getString(sid));
                    Log.e(TAG, "fuelConfiguratorDetails getData : "+c.getString(data));
                    Log.e(TAG, "fuelConfiguratorDetails eventType : "+c.getString(eventType));

                    jsonObject.put("isRecordFound",true);
                    jsonObject.put("sid",c.getString(sid));
                    jsonObject.put("date",c.getLong(date));
                    jsonObject.put("eventType",c.getString(eventType));
                    JSONArray dataArray = new JSONArray(c.getString(data));
                    jsonObject.put("data",dataArray);
                    Log.e(TAG, "fuelConfiguratorDetails jsonObject **: "+ jsonObject);
                }
            }
            c.close();
            this.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.e(TAG, "fuelConfiguratorDetails Exception : "+e.getMessage());
        }
        return jsonObject;
    }


    public long updateProductStatus(int status,int BillingTab)
    {
        long x = 0;
        try
        {
            this.open()  ;
            ContentValues cv = new ContentValues();
            //Cursor c = db.rawQuery("update tbl_user_account set Branch_id= '"+BranchId.trim()+"'", null);
            cv.put("Status", status);
            x = db.update("tbl_fuel_configurator",cv,"Status=0",null);
            this.close();
        }
        catch (Exception e)
        {
            this.close();
        }
        return x;
    }
}



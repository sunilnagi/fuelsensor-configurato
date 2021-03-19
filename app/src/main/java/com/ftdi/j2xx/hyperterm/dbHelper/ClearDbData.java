package com.ftdi.j2xx.hyperterm.dbHelper;

import android.content.Context;
import android.util.Log;


public class ClearDbData
{
    //TODO clear SQLite tables data
    public static void clearDataBaseData(Context ctx)
    {
        String TAG = "ClearDbData";
        Log.e(TAG,"inside clearDataBaseData");
        try
        {
            FuelSensorConfiguratorHelper fuelSensorConfiguratorHelper = new FuelSensorConfiguratorHelper(ctx);
            fuelSensorConfiguratorHelper.clearProductDetails();
        }
        catch (Exception e)
        {
            Log.e(TAG,"clearDataBaseData Exception "+e.getMessage());
        }
    }
}

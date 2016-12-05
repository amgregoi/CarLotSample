package com.teioh08.carlotsample.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.teioh08.carlotsample.CarLotApplication;
import com.teioh08.carlotsample.Models.CarBase;
import com.teioh08.carlotsample.Models.Funny;
import com.teioh08.carlotsample.Models.Pinto;
import com.teioh08.carlotsample.Models.Semi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class CarLotDB extends SQLiteOpenHelper
{
    public static final String TAG = CarLotDB.class.getSimpleName();

    public static final int sDATABASE_VERSION = 1;
    private static final String sDB_PATH = "/data/data/com.teioh08.carlotsample/databases/";
    private static final String sDB_NAME = "CarLotDB.db";
    private static CarLotDB sInstance;
    private Context mContext;

    public CarLotDB(Context aContext)
    {
        super(aContext, sDB_NAME, null, sDATABASE_VERSION);
        mContext = aContext;
    }

    public static synchronized CarLotDB getInstance()
    {
        if (sInstance == null)
        {
            sInstance = new CarLotDB(CarLotApplication.getInstance());
        }
        return sInstance;
    }

    public void onCreate(SQLiteDatabase aDb)
    {
        cupboard().withDatabase(aDb).createTables();
    }


    public void onUpgrade(SQLiteDatabase aDb, int aOldVersion, int aNewVersion)
    {
        cupboard().withDatabase(aDb).upgradeTables();
    }

    public void createDatabase()
    {
        createDB();
    }

    private void createDB()
    {
        boolean dbExist = DBExists();
        if (!dbExist)
        {
            this.getReadableDatabase();
            copyDBFromResource();
        }
    }

    private boolean DBExists()
    {
        SQLiteDatabase db = null;

        try
        {
            File database = mContext.getDatabasePath(sDB_NAME);
            if (database.exists())
            {
                db = SQLiteDatabase.openDatabase(database.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
                db.setLocale(Locale.getDefault());
                db.setVersion(1);
            }
        }
        catch (Exception aException)
        {
            Log.e(TAG, aException.getMessage());
        }

        if (db != null)
        {
            db.close();
        }
        return db != null;
    }

    private void copyDBFromResource()
    {
        String dbFilePath = sDB_PATH + sDB_NAME;
        try
        {
            InputStream inputStream = CarLotApplication.getInstance().getAssets().open(sDB_NAME);
            OutputStream outStream = new FileOutputStream(dbFilePath);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0)
            {
                outStream.write(buffer, 0, length);
            }

            outStream.flush();
            outStream.close();
            inputStream.close();
        }
        catch (IOException aException)
        {
            Log.e(TAG, aException.getMessage());
        }
    }

    public void putCar(CarBase aCar)
    {

        if (getInventory().size() < 25)
        {
            cupboard().withDatabase(getWritableDatabase()).put(aCar);
        }
        else
        {
            Toast.makeText(CarLotApplication.getInstance(), "Car lot is full!", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeCar(CarBase aCar)
    {
        cupboard().withDatabase(getWritableDatabase()).delete(aCar);
    }

    public void updateCar(ContentValues aValues, String aId, CarBase aCar)
    {
        if (aCar instanceof Pinto) cupboard().withDatabase(getWritableDatabase()).update(Pinto.class, aValues, "_id = ?", aId);
        else if (aCar instanceof Semi) cupboard().withDatabase(getWritableDatabase()).update(Semi.class, aValues, "_id = ?", aId);
        else cupboard().withDatabase(getWritableDatabase()).update(Funny.class, aValues, "_id = ?", aId);
    }

    public List<CarBase> getInventory()
    {
        List<CarBase> lResult = new ArrayList<>();
        //gets list of pinto cars
        QueryResultIterable<Pinto> lPintoItr = cupboard().withDatabase(getReadableDatabase()).query(Pinto.class).query();
        for (CarBase lCar : lPintoItr.list())
        {
            lResult.add(lCar);
        }

        //gets list of semi trucks
        QueryResultIterable<Semi> lSemiItr = cupboard().withDatabase(getReadableDatabase()).query(Semi.class).query();
        for (CarBase lCar : lSemiItr.list())
        {
            lResult.add(lCar);
        }

        //gets list of funny cars
        QueryResultIterable<Funny> lFunnyItr = cupboard().withDatabase(getReadableDatabase()).query(Funny.class).query();
        for (CarBase lCar : lFunnyItr.list())
        {
            lResult.add(lCar);
        }

        return lResult;
    }
}

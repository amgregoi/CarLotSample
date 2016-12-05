package com.teioh08.carlotsample;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.teioh08.carlotsample.Models.CarBase;
import com.teioh08.carlotsample.Models.Funny;
import com.teioh08.carlotsample.Models.Pinto;
import com.teioh08.carlotsample.Models.Semi;
import com.teioh08.carlotsample.Utils.CarLotDB;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class CarLotApplication extends Application
{
    private static CarLotApplication aInstance;

    static
    {
        cupboard().register(CarBase.class);
        cupboard().register(Pinto.class);
        cupboard().register(Semi.class);
        cupboard().register(Funny.class);
    }


    public CarLotApplication()
    {
        aInstance = this;
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        //creates database if fresh install
        CarLotDB.getInstance().createDatabase();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    @Override
    public void onTrimMemory(int aLevel)
    {
        super.onTrimMemory(aLevel);
        Glide.get(this).trimMemory(aLevel);
    }

    public static synchronized CarLotApplication getInstance()
    {
        return aInstance;
    }


}

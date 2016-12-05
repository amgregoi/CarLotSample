package com.teioh08.carlotsample.Models;

import android.os.Parcel;

import com.teioh08.carlotsample.eCarLotEnums;

public class Semi extends CarBase
{

    public Semi(boolean aHybrid)
    {
        super();
        setCurrentPrice(50000);
        setFuelType(eCarLotEnums.eFuelType.Diesel);

        if (aHybrid)
        {
            setHybrid(aHybrid);
            setFuelCost(getFuelCost() - 2);
        }
    }

    public Semi()
    {
    }

    public Semi(Parcel aIn)
    {
        super(aIn);
    }

    public static final Creator<CarBase> CREATOR = new Creator<CarBase>()
    {
        @Override
        public CarBase createFromParcel(Parcel aIn)
        {
            return new Semi(aIn);
        }

        @Override
        public CarBase[] newArray(int aSize)
        {
            return new CarBase[aSize];
        }
    };

    @Override
    public String getCarName()
    {
        return "Semi Truck " + _id;
    }
}

package com.teioh08.carlotsample.Models;

import android.os.Parcel;

import com.teioh08.carlotsample.eCarLotEnums;


public class Pinto extends CarBase
{

    public Pinto(boolean aHybrid)
    {
        super();
        setSaleDiscount(.75);
        setFuelType(eCarLotEnums.eFuelType.Gas);

        if (aHybrid)
        {
            setHybrid(aHybrid);
            setFuelCost(getFuelCost() - 2);
        }
    }

    public Pinto()
    {

    }

    public Pinto(Parcel aIn)
    {
        super(aIn);
    }

    public static final Creator<CarBase> CREATOR = new Creator<CarBase>()
    {
        @Override
        public CarBase createFromParcel(Parcel aIn)
        {
            return new Pinto(aIn);
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
        return "Pinto Car " + _id;
    }
}


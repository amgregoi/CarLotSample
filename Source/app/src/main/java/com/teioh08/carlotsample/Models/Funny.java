package com.teioh08.carlotsample.Models;

import android.os.Parcel;

import com.teioh08.carlotsample.eCarLotEnums;

public class Funny extends CarBase
{

    public Funny(boolean aHybrid)
    {
        super();
        setFuelCost(14);
        setFuelType(eCarLotEnums.eFuelType.Methanol);

        if (aHybrid)
        {
            setHybrid(aHybrid);
            setFuelCost(getFuelCost() - 2);
        }
    }

    public Funny()
    {
    }

    public Funny(Parcel aIn)
    {
        super(aIn);
    }

    public static final Creator<CarBase> CREATOR = new Creator<CarBase>()
    {
        @Override
        public CarBase createFromParcel(Parcel aIn)
        {
            return new Funny(aIn);
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
        return "Funny Car " + _id;
    }

}

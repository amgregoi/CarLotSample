package com.teioh08.carlotsample.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import com.teioh08.carlotsample.CarLotApplication;
import com.teioh08.carlotsample.eCarLotEnums;

public abstract class CarBase implements Parcelable
{
    public final static String TAG = CarBase.class.getSimpleName();
    protected Long _id;
    protected int FuelLevel;
    protected int CurrentPrice;
    protected eCarLotEnums.eFuelType FuelType;

    protected int FuelCost; //cost to drive the car
    protected double SaleDiscount; //sale discount (defaults 20%)

    protected eCarLotEnums.eDrive DriveSound;
    protected eCarLotEnums.eHonk HonkSound;

    private boolean Hybrid; // Variable to see if car is a hybrid
    private boolean CanDrive = true;

    public CarBase()
    {
        FuelLevel = 14;
        CurrentPrice = 20000;
        SaleDiscount = .20;
        FuelCost = 3;

        DriveSound = eCarLotEnums.eDrive.Zoom;
        HonkSound = eCarLotEnums.eHonk.Beep;
    }

    public CarBase(Parcel aIn)
    {
        _id = aIn.readLong();
        FuelLevel = aIn.readInt();
        CurrentPrice = aIn.readInt();
        FuelType = eCarLotEnums.eFuelType.valueOf(aIn.readString());
        FuelCost = aIn.readInt();
        SaleDiscount = aIn.readDouble();
        DriveSound = eCarLotEnums.eDrive.valueOf(aIn.readString());
        HonkSound = eCarLotEnums.eHonk.valueOf(aIn.readString());
        Hybrid = aIn.readByte() != 0;
        CanDrive = aIn.readByte() != 0;
    }

    public boolean isCanDrive()
    {
        return CanDrive;
    }

    public void setCanDrive(boolean aCanDrive)
    {
        CanDrive = aCanDrive;
    }

    public Long getID()
    {
        return _id;
    }

    public void setID(Long aID)
    {
        _id = aID;
    }

    public int getFuelLevel()
    {
        return FuelLevel;
    }

    public void setFuelLevel(int aFuelLevel)
    {
        if (aFuelLevel < 0)
        {
            FuelLevel = 0;
            Toast.makeText(CarLotApplication.getInstance(), "Oh No, you are out of fuel", Toast.LENGTH_SHORT).show();
        }
        else
        {
            FuelLevel = aFuelLevel;
        }
    }

    public int getCurrentPrice()
    {
        return CurrentPrice;
    }

    public void setCurrentPrice(int aCurrentPrice)
    {
        CurrentPrice = aCurrentPrice;
    }

    public eCarLotEnums.eFuelType getFuelType()
    {
        return FuelType;
    }

    public void setFuelType(eCarLotEnums.eFuelType aFuelType)
    {
        FuelType = aFuelType;
    }

    public int getFuelCost()
    {
        return FuelCost;
    }

    public void setFuelCost(int aFuelCost)
    {
        FuelCost = aFuelCost;
    }

    public double getSaleDiscount()
    {
        return SaleDiscount;
    }

    public void setSaleDiscount(double aSaleDiscount)
    {
        SaleDiscount = aSaleDiscount;
    }

    public eCarLotEnums.eDrive getDriveSound()
    {
        return DriveSound;
    }

    public void setDriveSound(eCarLotEnums.eDrive aDriveSound)
    {
        DriveSound = aDriveSound;
    }

    public eCarLotEnums.eHonk getHonkSound()
    {
        return HonkSound;
    }

    public void setHonkSound(eCarLotEnums.eHonk aHonkSound)
    {
        HonkSound = aHonkSound;
    }

    public boolean isHybrid()
    {
        return Hybrid;
    }

    public void setHybrid(boolean aHybrid)
    {
        Hybrid = aHybrid;
    }

    @Override
    public int describeContents()
    {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(_id);
        dest.writeInt(FuelLevel);
        dest.writeInt(CurrentPrice);
        dest.writeString(FuelType.name());
        dest.writeInt(FuelCost);
        dest.writeDouble(SaleDiscount);
        dest.writeString(DriveSound.name());
        dest.writeString(HonkSound.name());
        dest.writeByte((byte) (Hybrid ? 1 : 0));
        dest.writeByte((byte) (CanDrive ? 1 : 0));
    }

    public abstract String getCarName();
}

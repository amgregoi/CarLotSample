package com.teioh08.carlotsample.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.teioh08.carlotsample.Models.CarBase;
import com.teioh08.carlotsample.Models.Funny;
import com.teioh08.carlotsample.Models.Pinto;
import com.teioh08.carlotsample.Models.Semi;
import com.teioh08.carlotsample.R;
import com.teioh08.carlotsample.Utils.CarLotDB;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarLotActivity extends AppCompatActivity
{

    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_lot);
        ButterKnife.bind(this);

        mToast = Toast.makeText(this, "Press back again to exit!", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.funny_car)
    public void onFunnyCarButtonClick()
    {
        CarBase lCar = new Funny(false);
        CarLotDB.getInstance().putCar(lCar);
    }

    @OnClick(R.id.pinto_car)
    public void onPintoCarButtonClick()
    {
        CarBase lCar = new Pinto(false);
        CarLotDB.getInstance().putCar(lCar);
    }

    @OnClick(R.id.semi_truck)
    public void onSemiTruckButtonClick()
    {
        CarBase lCar = new Semi(false);
        CarLotDB.getInstance().putCar(lCar);
    }

    @OnClick(R.id.random_car)
    public void onRandomCarButtonClick()
    {
        Random lSeed = new Random();
        int lRandomNumber = (lSeed.nextInt(100));
        CarBase lCar;

        switch (lRandomNumber % 3)
        {
            case 0:
                lCar = new Funny(true);
                break;
            case 1:
                lCar = new Semi(true);
                break;
            default:
                lCar = new Pinto(true);

        }

        //add car to database
        CarLotDB.getInstance().putCar(lCar);

    }

    @OnClick(R.id.inventory)
    public void onInventoryButtonClick()
    {
        Intent lInventoryIntent = InventoryActivity.getNewInstance(this);
        startActivity(lInventoryIntent);
    }

    @Override
    public void onBackPressed()
    {
        if (!mToast.getView().isShown())
        {
            mToast.show();
        }
        else
        {    //user double back pressed to exit within time frame (mToast length)
            mToast.cancel();
            super.onBackPressed();
        }
    }
}

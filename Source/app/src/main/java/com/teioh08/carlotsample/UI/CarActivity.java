package com.teioh08.carlotsample.UI;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.teioh08.carlotsample.Models.CarBase;
import com.teioh08.carlotsample.R;
import com.teioh08.carlotsample.Utils.CarLotDB;
import com.teioh08.carlotsample.eCarLotEnums;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CarActivity extends AppCompatActivity
{
    @Bind(R.id.fuel_level) TextView mFuelLevel;
    @Bind(R.id.car_price) TextView mCarPrice;
    @Bind(R.id.fuel_button) Button mFuelButton;
    @Bind(R.id.car_name) TextView mCarName;
    @Bind(R.id.fuel_type) TextView mFuelType;

    private CarBase mCar;
    private Toast mToast;
    public boolean mKeepCar = true; //quick work around

    public static Intent getNewInstance(CarBase aCar, Context aContext)
    {
        Intent lIntent = new Intent(aContext, CarActivity.class);
        lIntent.putExtra(CarBase.TAG, aCar);
        return lIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);
        setupLayout();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        //update car when leaving activity
        if (mKeepCar)
        {
            ContentValues lValues = new ContentValues();
            lValues.put("FuelLevel", mCar.getFuelLevel());
            lValues.put("CurrentPrice", mCar.getCurrentPrice());
            lValues.put("CanDrive", mCar.isCanDrive() ? 1 : 0);
            CarLotDB.getInstance().updateCar(lValues, mCar.getID().toString(), mCar);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.drive_button)
    public void onDriveButtonClicked()
    {
        if (mCar.isCanDrive())
        {
            int lDifference = mCar.getFuelLevel() - mCar.getFuelCost();
            if (mCar.getFuelLevel() == 0)
            {
                sendToast("There is no fuel");
            }
            else
            {
                sendToast(mCar.getDriveSound().name());
                mCar.setFuelLevel(lDifference);
                mFuelLevel.setText(Integer.toString(mCar.getFuelLevel()));
            }
        }
        else
        {
            sendToast("You have used the wrong fuel the car will no longer start");
        }
    }

    @OnClick(R.id.fuel_button)
    public void onFuelButtonClicked()
    {
        PopupMenu popup = new PopupMenu(CarActivity.this, mFuelButton);
        popup.getMenuInflater().inflate(R.menu.fuel_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                String val = item.getTitle().toString();
                if (mCar.getFuelType() == eCarLotEnums.eFuelType.valueOf(item.getTitle().toString()))
                {
                    mCar.setFuelLevel(mCar.getFuelLevel() + mCar.getFuelCost());
                    mFuelLevel.setText(Integer.toString(mCar.getFuelLevel()));
                }
                else
                {
                    sendToast("You have used the wrong fuel the car will no longer start");
                    mCar.setCanDrive(false);
                }
                return true;
            }

        });

        popup.show();
    }

    @OnClick(R.id.honk_button)
    public void onHonkButtonClicked()
    {
        sendToast(mCar.getHonkSound().name());
    }

    @OnClick(R.id.sale_button)
    public void onSaleButtonClicked()
    {
        int lNewPrice = ((int) (mCar.getCurrentPrice() - (mCar.getCurrentPrice() * mCar.getSaleDiscount())));
        mCar.setCurrentPrice(lNewPrice);
        mCarPrice.setText(Integer.toString(lNewPrice));
    }

    @OnClick(R.id.remove_button)
    public void onRemoveButtonClicked()
    {
        CarLotDB.getInstance().removeCar(mCar);
        sendToast("Removed car from lot");
        mKeepCar = false;
        super.onBackPressed();
    }

    private void setupLayout()
    {
        mToast = Toast.makeText(this, "Temp Message", Toast.LENGTH_SHORT);
        mCar = getIntent().getExtras().getParcelable(CarBase.TAG);
        mFuelLevel.setText(Integer.toString(mCar.getFuelLevel()));
        mCarPrice.setText(Integer.toString(mCar.getCurrentPrice()));
        mCarName.setText(mCar.getCarName());
        mFuelType.setText(mCar.getFuelType().name());
    }

    private void sendToast(String aMessage)
    {
        if (mToast.getView().isShown())
        {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, aMessage, Toast.LENGTH_SHORT);
        mToast.show();

    }


}

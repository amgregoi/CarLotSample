package com.teioh08.carlotsample.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.teioh08.carlotsample.Adapters.InventoryAdapter;
import com.teioh08.carlotsample.R;
import com.teioh08.carlotsample.Utils.CarLotDB;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InventoryActivity extends AppCompatActivity implements InventoryAdapter.ItemSelectedListener
{

    @Bind(R.id.inventory_recyclerview) RecyclerView mInventoryRecyclerView;
    InventoryAdapter mAdapter;

    public static Intent getNewInstance(Context aContext)
    {
        Intent lIntent = new Intent(aContext, InventoryActivity.class);
        return lIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        ButterKnife.bind(this);
        setupLayout(true);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setupLayout(false);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void setupLayout(boolean aNeedsDecoration)
    {
        mAdapter = new InventoryAdapter(CarLotDB.getInstance().getInventory(), this);
        mInventoryRecyclerView.setAdapter(mAdapter);
        mInventoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (aNeedsDecoration) mInventoryRecyclerView.addItemDecoration(new InventoryAdapter.SpacesItemDecoration(20));
    }

    @Override
    public void onItemSelected(int aPosition)
    {
        Intent lCarActivityIntent = CarActivity.getNewInstance(mAdapter.getItemAt(aPosition), this);
        startActivity(lCarActivityIntent);
    }
}

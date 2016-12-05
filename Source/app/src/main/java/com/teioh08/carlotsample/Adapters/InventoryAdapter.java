package com.teioh08.carlotsample.Adapters;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teioh08.carlotsample.Models.CarBase;
import com.teioh08.carlotsample.R;

import java.util.ArrayList;
import java.util.List;


public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder>
{

    private ArrayList<CarBase> mData = null;
    private final ItemSelectedListener mListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView mFuelLevel;
        public TextView mFuelType;
        public TextView mCarName;
        public TextView mCarPrice;


        public ViewHolder(View aView)
        {
            super(aView);
            mFuelLevel = (TextView) aView.findViewById(R.id.fuel_level);
            mFuelType = (TextView) aView.findViewById(R.id.fuel_type);
            mCarName = (TextView) aView.findViewById(R.id.car_name);
            mCarPrice = (TextView) aView.findViewById(R.id.car_price);

            aView.setOnClickListener(this);
        }

        @Override
        public void onClick(View aView)
        {
            notifyItemChanged(getLayoutPosition());
            mListener.onItemSelected(getAdapterPosition());
        }

    }

    public interface ItemSelectedListener
    {
        void onItemSelected(int aPosition);
    }

    public InventoryAdapter(List<CarBase> aData, ItemSelectedListener aListener)
    {
        mData = new ArrayList<>(aData);
        mListener = aListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup aParent, int aViewType)
    {
        View lView = LayoutInflater.from(aParent.getContext()).inflate(R.layout.inventory_list_item, aParent, false);
        return new ViewHolder(lView);
    }

    @Override
    public void onBindViewHolder(ViewHolder aHolder, int aPosition)
    {
        CarBase lCar = mData.get(aPosition);

        aHolder.mFuelLevel.setText(Integer.toString(lCar.getFuelLevel()));
        aHolder.mFuelType.setText(lCar.getFuelType().name());
        aHolder.mCarName.setText(lCar.getCarName());
        aHolder.mCarPrice.setText("$" + Integer.toString(lCar.getCurrentPrice()));
    }


    public long getItemId(int aPosition)
    {
        return aPosition;
    }

    public CarBase getItemAt(int aPosition)
    {
        return mData.get(aPosition);
    }

    public void updateItem(CarBase aCar)
    {
        int lPosition;

        if ((lPosition = mData.indexOf(aCar)) != -1)
        {
            mData.remove(lPosition);
            mData.add(lPosition, aCar);
            notifyItemChanged(lPosition);
        }
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration
    {

        private int lHalfSpace;

        public SpacesItemDecoration(int space)
        {
            this.lHalfSpace = space / 2;
        }


        @Override
        public void getItemOffsets(Rect aOutRect, View aView, RecyclerView aParent, RecyclerView.State aState)
        {

            if (aParent.getPaddingLeft() != lHalfSpace)
            {
                aParent.setPadding(lHalfSpace, lHalfSpace, lHalfSpace, lHalfSpace);
                aParent.setClipToPadding(false);
            }

            aOutRect.top = lHalfSpace;
            aOutRect.bottom = lHalfSpace;
            aOutRect.left = lHalfSpace;
            aOutRect.right = lHalfSpace;
        }
    }

}


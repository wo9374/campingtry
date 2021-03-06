package com.example.mylogin.SEARCH.Detail;

import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylogin.R;

import java.util.ArrayList;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.PriceViewHolder> {
    private ArrayList<PriceItem> PriceItems = null;

    public void setData(ArrayList<PriceItem> list) {
        this.PriceItems = list;
    }

    public SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);


    private OnItemClickListener mListner = null;

    @NonNull
    @Override
    public PriceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_detail_price, parent, false);

        PriceViewHolder holder = new PriceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PriceViewHolder holder, int position) {
        PriceItem data = PriceItems.get(position);

        holder.zone.setText(data.getZone());
        holder.facility.setText(data.getFacility());
        holder.price.setText(data.getPrice());
        holder.pricecode.setText(data.getPricecode());

        if (isItemSelected(position)) {
            holder.itemView.setBackgroundColor(Color.GRAY);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return PriceItems.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public  void setOnItemClickListener(OnItemClickListener listener){
        this.mListner = listener;
    }


    public class PriceViewHolder extends RecyclerView.ViewHolder {
        TextView zone;
        TextView facility;
        TextView pr;
        TextView price;
        TextView pricecode;

        public PriceViewHolder(View itemView) {
            super(itemView);

            zone = (TextView) itemView.findViewById(R.id.zone);
            facility = (TextView) itemView.findViewById(R.id.facility);
            pr = (TextView) itemView.findViewById(R.id.pr);
            price = (TextView) itemView.findViewById(R.id.price);
            pricecode = (TextView) itemView.findViewById(R.id.pricecode);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //if(position  != RecyclerView.NO_POSITION){}
                    toggleItemSelected(position);

                    mListner.onItemClick(v,position);
                }
            });
        }
    }


    private void toggleItemSelected(int position) {
        clearSelectedItem();

        if (mSelectedItems.get(position, false) == true) {
            mSelectedItems.delete(position);
            notifyItemChanged(position);
        } else {
            mSelectedItems.put(position, true);
            notifyItemChanged(position);
        }
    }


    public boolean isItemSelected(int position) {
        return mSelectedItems.get(position, false);
    }

    public void clearSelectedItem() {
        int position;

        for (int i = 0; i < mSelectedItems.size(); i++) {
            position = mSelectedItems.keyAt(i);
            mSelectedItems.put(position, false);
            notifyItemChanged(position);
        }

        mSelectedItems.clear();
    }
}
package com.example.bestbefore.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestbefore.R;
import com.example.bestbefore.db.entity.FoodEntity;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    private final LayoutInflater mInflater;
    private List<FoodEntity> mFoods;

    public FoodListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FoodListAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        if (mFoods != null) {
            FoodEntity current = mFoods.get(position);
            holder.foodItemView.setText(current.getFoodName());
        }
    }

    void setFoods(List<FoodEntity> foods){
        mFoods = foods;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mFoods != null) return mFoods.size();
        else return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        private TextView foodItemView;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodItemView = itemView.findViewById(R.id.textView);
        }
    }
}

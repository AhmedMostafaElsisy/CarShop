package com.example.cars.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cars.R;
import com.example.cars.model.Car;

import java.util.ArrayList;
import java.util.List;

public class CarAdaptor extends RecyclerView.Adapter<CarAdaptor.ExampleViewHolder> implements Filterable {
    private List<Car> exampleList;
    private List<Car> exampleListFull;
    private OnCarListener onCarListener;
    private Context context;

    class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView carGas;
        TextView carDesc;
        TextView carColor;
        OnCarListener onCarListener;


        ExampleViewHolder(View itemView, OnCarListener onCarListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.carImg);
            carGas = itemView.findViewById(R.id.carGas);
            carDesc = itemView.findViewById(R.id.carDesc);
            carColor = itemView.findViewById(R.id.carColor);

            this.onCarListener = onCarListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onCarListener.onCarClick(getAdapterPosition());
        }
    }

    public CarAdaptor(List<Car> exampleList, OnCarListener onCarListener, Context context) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
        this.onCarListener = onCarListener;
        this.context = context;


    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,
                parent, false);
        return new ExampleViewHolder(v, onCarListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdaptor.ExampleViewHolder holder, int position) {
        Car currentItem = exampleList.get(position);
        holder.imageView.setImageResource(currentItem.getPicUrl());
        Glide.with(context)
                .load(currentItem.getPicUrl())
                .apply(new RequestOptions().override(600, 200))
                .into(holder.imageView);
        holder.carGas.setText(String.valueOf(currentItem.getGas()));
        holder.carDesc.setText(currentItem.getCarModel());
        holder.carColor.setText(String.valueOf(currentItem.getColor()));
    }


    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Car> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Car item : exampleListFull) {
                    if (item.getCarModel().toLowerCase().contains(filterPattern)) {

                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnCarListener {
        void onCarClick(int position);
    }
}
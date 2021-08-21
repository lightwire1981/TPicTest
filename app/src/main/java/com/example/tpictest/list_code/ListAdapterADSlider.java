package com.example.tpictest.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tpictest.R;

public class ListAdapterADSlider extends RecyclerView.Adapter<ListAdapterADSlider.ViewHolder>{

    private Context context;
    private final int[] resID;

    public ListAdapterADSlider(int[] resID) {
        this.resID = resID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_ad_slider, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int index = position % resID.length;
        holder.bindSliderImage(resID[index]);
        holder.adImage.setOnClickListener(view -> Toast.makeText(view.getContext(), R.string.txt_test_message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView adImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            adImage = itemView.findViewById(R.id.iVwAdSlider);
        }

        public void bindSliderImage(int resID) {
            Glide.with(context).load(resID).into(adImage);
        }
    }

    private Runnable runnable = () -> {

    };
}

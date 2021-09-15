package com.auroraworld.toypic.list_code;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.auroraworld.toypic.R;

import java.util.ArrayList;

public class ListAdptDlgPopupImage extends RecyclerView.Adapter<ListAdptDlgPopupImage.ViewHolder> {
    private Context context;
    private final ArrayList<Bitmap> bitmaps;

    public ListAdptDlgPopupImage(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_popup_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(bitmaps.get(position)).placeholder(R.drawable.popup_sample01).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.popupImage);
        holder.popupImage.setOnClickListener(v -> Toast.makeText(context, "해당 사이트 이동(구성 중)", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView popupImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popupImage = itemView.findViewById(R.id.iVwPopupImage);
        }
    }
}

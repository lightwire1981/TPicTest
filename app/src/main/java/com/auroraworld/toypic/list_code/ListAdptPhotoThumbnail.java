package com.auroraworld.toypic.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.utils.BitmapConverter;

import java.util.ArrayList;

public class ListAdptPhotoThumbnail extends RecyclerView.Adapter<ListAdptPhotoThumbnail.ViewHolder> {
    private Context context;
    private final ArrayList<ListItemPhotoThumbnail> mData;

    public interface SelectPhotoListener {
        void onSelectPhoto(int dataOffset);
    }
    private SelectPhotoListener selectPhotoListener;

    public ListAdptPhotoThumbnail(ArrayList<ListItemPhotoThumbnail> list, SelectPhotoListener selectPhotoListener) {
        this.mData = list;
        this.selectPhotoListener = selectPhotoListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_photo_thumbnail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemPhotoThumbnail item = mData.get(position);

        switch (item.getItemCount()) {
            case 3:
                holder.photo3.setVisibility(View.VISIBLE);
                Glide.with(context).
                        load(BitmapConverter.StringToBitMap(item.getPhoto3Url())).
                        placeholder(R.drawable.review_img_blank).
                        diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                        into(holder.photo3);
                holder.photo3.setOnClickListener(v -> selectPhotoListener.onSelectPhoto(item.getDataOffset3()));
            case 2:
                holder.photo2.setVisibility(View.VISIBLE);
                Glide.with(context).
                        load(BitmapConverter.StringToBitMap(item.getPhoto2Url())).
                        placeholder(R.drawable.review_img_blank).
                        diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                        into(holder.photo2);
                holder.photo2.setOnClickListener(v -> selectPhotoListener.onSelectPhoto(item.getDataOffset2()));
            case 1:
                Glide.with(context).
                        load(BitmapConverter.StringToBitMap(item.getPhoto1Url())).
                        placeholder(R.drawable.review_img_blank).
                        diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                        into(holder.photo1);
                holder.photo1.setOnClickListener(v -> selectPhotoListener.onSelectPhoto(item.getDataOffset1()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageButton photo1, photo2, photo3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo1 = itemView.findViewById(R.id.iBtnPhotoThumb1);
            photo2 = itemView.findViewById(R.id.iBtnPhotoThumb2);
            photo3 = itemView.findViewById(R.id.iBtnPhotoThumb3);
        }
    }
}

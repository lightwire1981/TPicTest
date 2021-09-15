package com.auroraworld.toypic.list_code;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.auroraworld.toypic.R;

import java.util.ArrayList;

public class ListAdptDlgScalableGoodsImg extends RecyclerView.Adapter<ListAdptDlgScalableGoodsImg.ViewHolder> {
    Context context;
    private final ArrayList<Bitmap> bitMapList;

    public ListAdptDlgScalableGoodsImg(ArrayList<Bitmap> bitmapList) {
        this.bitMapList = bitmapList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_scalable_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Glide.with(context).
//                load(imageUrl.get(position)).
//                placeholder(R.drawable.tp_icon_brand01_on).
//                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
//                into(holder.goodsImage);
//        Glide.with(context).asBitmap().load(imageUrl.get(position));
//        holder.goodsImage.setImage(ImageSource.uri(imageUrl.get(position)));

        holder.goodsImage.setImage(ImageSource.bitmap(bitMapList.get(position)));
    }

    @Override
    public int getItemCount() {
        return bitMapList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final SubsamplingScaleImageView goodsImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImage = itemView.findViewById(R.id.sclImgVwImage);
        }
    }
}

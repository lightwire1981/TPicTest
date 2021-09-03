package com.wellstech.tpictest.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.wellstech.tpictest.R;

import java.util.ArrayList;

public class ListAdptDlgScalableGoodsImg extends RecyclerView.Adapter<ListAdptDlgScalableGoodsImg.ViewHolder> {
    Context context;
    private final ArrayList<String> imageUrl;

    public ListAdptDlgScalableGoodsImg(ArrayList<String> imageUrl) { this.imageUrl = imageUrl; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_dialog_goods_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Glide.with(context).
//                load(imageUrl.get(position)).
//                placeholder(R.drawable.tp_icon_brand01_on).
//                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
//                into(holder.goodsImage);
        holder.goodsImage.setImage(ImageSource.uri(imageUrl.get(position)));
    }

    @Override
    public int getItemCount() {
        return imageUrl.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final SubsamplingScaleImageView goodsImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImage = itemView.findViewById(R.id.sclImgVwGoodsImg);
        }
    }
}

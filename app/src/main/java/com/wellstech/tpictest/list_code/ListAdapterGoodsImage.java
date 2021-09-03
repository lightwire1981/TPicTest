package com.wellstech.tpictest.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wellstech.tpictest.GoodsInfoActivity;
import com.wellstech.tpictest.R;
import com.wellstech.tpictest.utils.CustomDialog;

import java.util.ArrayList;

public class ListAdapterGoodsImage extends RecyclerView.Adapter<ListAdapterGoodsImage.ViewHolder> {
    private Context context;
    private final ArrayList<String> imageUrl;
    private View view;

    public ListAdapterGoodsImage(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_goods_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int index = position % imageUrl.size();
        Glide.with(context).
                load(imageUrl.get(index)).
                placeholder(R.drawable.tp_icon_brand01_on).
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                into(holder.goodsImage);
        holder.goodsImage.setOnClickListener(v -> {
            // Call Goods image Dialog
            new CustomDialog(GoodsInfoActivity.getGoodsInfoActivity(), CustomDialog.DIALOG_CATEGORY.GOODS_IMAGE, (response, data) -> {

            }, imageUrl).show();
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView goodsImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImage = itemView.findViewById(R.id.iVwGoodsInfoImage);
        }
    }
}

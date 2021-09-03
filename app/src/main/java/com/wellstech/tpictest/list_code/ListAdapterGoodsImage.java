package com.wellstech.tpictest.list_code;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wellstech.tpictest.R;
import com.wellstech.tpictest.utils.CustomDialog;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ListAdapterGoodsImage extends RecyclerView.Adapter<ListAdapterGoodsImage.ViewHolder> {
    private Context context;
    private final ArrayList<String> imageUrl;
    private ArrayList<Bitmap> bitmapList;

    public ListAdapterGoodsImage(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_goods_image, parent, false);
        getBitmapList();
        return new ViewHolder(view);
    }

    private void getBitmapList() {
        bitmapList = new ArrayList<>();
        Runnable runnable = () -> {
            for (String url : imageUrl) {
                try {
                    Bitmap imgBitmap = Glide.with(context).asBitmap().load(url).submit().get();
                    bitmapList.add(imgBitmap);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
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
            new CustomDialog(context, CustomDialog.DIALOG_CATEGORY.GOODS_IMAGE, (response, data) -> {

            }, bitmapList).show();
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

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
import com.auroraworld.toypic.utils.BitmapConverter;

import java.util.ArrayList;

public class ListAdptShowPhoto extends RecyclerView.Adapter<ListAdptShowPhoto.ViewHolder> {
    private Context context;
    private final ArrayList<String> photoList;
    private ArrayList<Bitmap> bitmapList = new ArrayList<>();

    public ListAdptShowPhoto(ArrayList<String> photoList) {
        this.photoList = photoList;
        for(String data: photoList) {
            bitmapList.add(BitmapConverter.StringToBitMap(data));
        }
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
//                load(bitmapList.get(position)).
//                placeholder(R.drawable.review_img_blank).
//                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
//                into((ImageView)holder.photoView);
        holder.photoView.setImage(ImageSource.bitmap(bitmapList.get(position)));
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final SubsamplingScaleImageView photoView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photoView = itemView.findViewById(R.id.sclImgVwImage);
        }
    }

}

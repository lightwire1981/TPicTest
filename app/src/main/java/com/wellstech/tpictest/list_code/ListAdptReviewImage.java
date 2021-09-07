package com.wellstech.tpictest.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wellstech.tpictest.R;
import com.wellstech.tpictest.utils.BitmapConverter;

import java.util.ArrayList;

public class ListAdptReviewImage extends RecyclerView.Adapter<ListAdptReviewImage.ViewHolder> {
    private Context context;
    private final ArrayList<ListItemReviewImage> mData;

    public interface SelectReviewListener {
        void onClick();
    }
    private final SelectReviewListener selectReviewListener;

    public ListAdptReviewImage(ArrayList<ListItemReviewImage> list, SelectReviewListener selectReviewListener) {
        this.mData = list;
        this.selectReviewListener = selectReviewListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_review_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemReviewImage item = mData.get(position);
        Glide.with(context).
                load(BitmapConverter.StringToBitMap(item.getPhotoUri())).
                placeholder(R.drawable.review_img_blank).
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                into(holder.photoView);
        holder.photoView.setOnClickListener(v -> selectReviewListener.onClick());
    }

    @Override
    public int getItemCount() {
        return Math.min(mData.size(), 5);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView photoView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photoView = itemView.findViewById(R.id.iVwPhotoForReview);
            itemView.findViewById(R.id.iBtnDeletePhoto).setVisibility(View.GONE);
        }
    }
}

package com.auroraworld.toypic.list_code;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.auroraworld.toypic.R;

import java.util.ArrayList;

public class ListAdptPhotoForReview extends RecyclerView.Adapter<ListAdptPhotoForReview.ViewHolder> {
    private Context context;
    private final ArrayList<Uri> imageUri;

    public interface RemovePhotoListener {
        void removePhoto(int position);
    }
    private final RemovePhotoListener removePhotoListener;

    public ListAdptPhotoForReview(ArrayList<Uri> imageUri, RemovePhotoListener removePhotoListener) {
        this.imageUri = imageUri;
        this.removePhotoListener = removePhotoListener;
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
        Glide.with(context).load(imageUri.get(position)).placeholder(R.drawable.review_img_blank).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.photoView);
        holder.photoDelete.setOnClickListener(view -> removePhotoListener.removePhoto(position));
    }

    @Override
    public int getItemCount() {
        return imageUri.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView photoView;
        private final ImageButton photoDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photoView = itemView.findViewById(R.id.iVwPhotoForReview);
            photoDelete = itemView.findViewById(R.id.iBtnDeletePhoto);
        }
    }
}

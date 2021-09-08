package com.wellstech.tpictest.list_code;

import static com.wellstech.tpictest.utils.BitmapConverter.StringToBitMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.wellstech.tpictest.R;
import com.wellstech.tpictest.utils.BitmapConverter;
import com.wellstech.tpictest.utils.PreferenceSetting;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListAdapterReview extends RecyclerView.Adapter<ListAdapterReview.ViewHolder> {
    private Context context;
    private final ArrayList<ListItemReviewToy> mData;

    public interface SelectReviewListener {
        void onSelectReview(ListItemReviewToy item);
        void onModifyReview(JSONObject jsonObject);
        void onDeleteReview(String reviewId);
    }
    private final SelectReviewListener selectReviewListener;

    public ListAdapterReview(ArrayList<ListItemReviewToy> list, SelectReviewListener selectReviewListener) {
        this.mData = list;
        this.selectReviewListener = selectReviewListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemReviewToy item = mData.get(position);
        Glide.with(context).
                load(BitmapConverter.StringToBitMap(item.getUrlImg1())).
                placeholder(R.drawable.review_img_blank).
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                into(holder.imgPhoto);
        holder.evaluateBar.setRating(Float.parseFloat(item.getNumberRate()));
        holder.evaluate.setText(item.getNumberRate());
        holder.likeCount.setText(context.getString(R.string.txt_goods_info_review_like_template, item.getNumberLike()));
        holder.reviewerName.setText(item.getReviewerName());
        holder.reviewDate.setText(item.getDate());
        holder.photoCount.setText(item.getPhotoCount());
        holder.review.setText(item.getCommentReview());
        holder.review.setOnClickListener(view -> selectReviewListener.onSelectReview(item));
        holder.imgPhoto.setOnClickListener(view -> selectReviewListener.onSelectReview(item));
        String user_id = PreferenceSetting.loadPreference(context, PreferenceSetting.PREFERENCE_KEY.USER_INFO);
        try {
            if (item.getReviewerId().equals(new JSONObject(user_id).getString("id"))){
                holder.reviewModify.setText(R.string.btn_review_modify);
                holder.reviewModify.setOnClickListener(v -> selectReviewListener.onModifyReview(item.getItem()));
                holder.reviewDelete.setText(R.string.btn_review_delete);
                holder.reviewDelete.setOnClickListener(v -> selectReviewListener.onDeleteReview(item.getReviewId()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        RatingBar evaluateBar;
        TextView evaluate, likeCount, reviewerName, reviewDate, reviewModify, reviewDelete, photoCount, review;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.iVwReviewerPhoto);
            evaluateBar = itemView.findViewById(R.id.rtnBrReviewerEvaluate);
            evaluate = itemView.findViewById(R.id.tVwReviewerEvaluate);
            likeCount = itemView.findViewById(R.id.tVwReviewLike);
            reviewerName = itemView.findViewById(R.id.tVwReviewerName);
            reviewDate = itemView.findViewById(R.id.tVwReviewDate);
            reviewModify = itemView.findViewById(R.id.tVwReviewModify);
            reviewDelete = itemView.findViewById(R.id.tVwReviewDelete);
            photoCount = itemView.findViewById(R.id.tVwReviewerPhotoSum);
            review = itemView.findViewById(R.id.tVwReviewSummary);
        }
    }
}

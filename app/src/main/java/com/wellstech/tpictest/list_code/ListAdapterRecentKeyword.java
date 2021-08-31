package com.wellstech.tpictest.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wellstech.tpictest.R;

import java.util.ArrayList;

public class ListAdapterRecentKeyword extends RecyclerView.Adapter<ListAdapterRecentKeyword.ViewHolder> {

    private final ArrayList<ListItemRecentKeyword> mData;

    public interface KeywordSelectListener {
        void onSelectKeyword(String keyword);
        void onDeleteKeyword(String keywordId);
    }

    private final KeywordSelectListener keywordSelectListener;

    public ListAdapterRecentKeyword(ArrayList<ListItemRecentKeyword> list, KeywordSelectListener keywordSelectListener) {
        mData = list;
        this.keywordSelectListener = keywordSelectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_recent_keyword, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position > 5) {
            return;
        }
        ListItemRecentKeyword item = mData.get(position);

        holder.tvwRecentKeyword.setText(item.getKeyWord());
        holder.tvwRecentKeyword.setOnClickListener(view -> keywordSelectListener.onSelectKeyword(((TextView)view).getText().toString()));
        holder.iBtnKeywordDelete.setTag(item.getKeyWordId());
        holder.iBtnKeywordDelete.setOnClickListener(view -> keywordSelectListener.onDeleteKeyword(view.getTag().toString()));
        holder.iBtnKeywordSearch.setOnClickListener(view -> keywordSelectListener.onSelectKeyword(holder.tvwRecentKeyword.getText().toString()));
    }

    @Override
    public int getItemCount() {
//        return mData.size();
        if (mData.size() > 5) {
            return 5;
        } else {
            return mData.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvwRecentKeyword;
        ImageButton iBtnKeywordDelete, iBtnKeywordSearch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvwRecentKeyword = itemView.findViewById(R.id.tvwRecentKeyword);
            iBtnKeywordDelete = itemView.findViewById(R.id.iBtnRecentKeywordDelete);
            iBtnKeywordSearch = itemView.findViewById(R.id.iBtnRecentKeywordSearch);
        }
    }
}

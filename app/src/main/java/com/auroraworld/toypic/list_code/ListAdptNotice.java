package com.auroraworld.toypic.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auroraworld.toypic.R;

import java.util.ArrayList;

public class ListAdptNotice extends RecyclerView.Adapter<ListAdptNotice.ViewHolder> {
    private final ArrayList<ListItemNotice> noticeList;

    public ListAdptNotice(ArrayList<ListItemNotice> noticeList) {
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_notice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemNotice item = noticeList.get(position);

        holder.noticeTitle.setText(item.getNoticeTitle());
        holder.noticeDate.setText(item.getNoticeDate());
        holder.noticeContent.setText(item.getNoticeContent());
        holder.ckbShowNotice.setOnCheckedChangeListener((checkBox, isChecked) -> {
            if (isChecked) {
                holder.noticeTitle.setLines(3);
                holder.noticeContent.setVisibility(View.VISIBLE);
            } else {
                holder.noticeTitle.setLines(1);
                holder.noticeContent.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView noticeTitle;
        private final TextView noticeDate;
        private final TextView noticeContent;
        private final CheckBox ckbShowNotice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeTitle = itemView.findViewById(R.id.tVwNoticeTitle);
            noticeDate = itemView.findViewById(R.id.tVwNoticeDate);
            noticeContent = itemView.findViewById(R.id.tVwNoticeContent);
            ckbShowNotice = itemView.findViewById(R.id.cKbNoticeShow);
        }
    }
}

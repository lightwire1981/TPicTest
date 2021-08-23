package com.example.tpictest.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tpictest.R;

import java.util.ArrayList;

public class ListAdapterChildInfo extends RecyclerView.Adapter<ListAdapterChildInfo.ViewHolder> {

    private final ArrayList<ListItemChildInfo> mData;

    public ListAdapterChildInfo(ArrayList<ListItemChildInfo> list) {mData = list;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemChildInfo item = mData.get(position);

        switch (item.getItemCount()) {
            case 1:
                if (item.isAddAction1()) {
                    holder.childBtn1.setImageDrawable(item.getDrawableAddChild());
                } else {
                    holder.childOrder1.setText(item.getChildOrder1());
                    holder.childNick1.setText(item.getChildNick1());
                }
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout childCly2, childCly3;
        ImageButton childBtn1, childBtn2, childBtn3;
        TextView childOrder1, childOrder2, childOrder3;
        TextView childNick1, childNick2, childNick3;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childCly2 = itemView.findViewById(R.id.cLyChild2);
            childCly3 = itemView.findViewById(R.id.cLyChild3);
            childBtn1 = itemView.findViewById(R.id.iBtnChildBg1);
            childBtn2 = itemView.findViewById(R.id.iBtnChildBg2);
            childBtn3 = itemView.findViewById(R.id.iBtnChildBg3);
            childOrder1 = itemView.findViewById(R.id.tVwChildOrder1);
            childOrder2 = itemView.findViewById(R.id.tVwChildOrder2);
            childOrder3 = itemView.findViewById(R.id.tVwChildOrder3);
            childNick1 = itemView.findViewById(R.id.tVwChildNick1);
            childNick2 = itemView.findViewById(R.id.tVwChildNick2);
            childNick3 = itemView.findViewById(R.id.tVwChildNick3);
        }
    }
}

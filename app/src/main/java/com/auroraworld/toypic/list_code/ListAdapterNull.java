package com.auroraworld.toypic.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auroraworld.toypic.R;

public class ListAdapterNull extends RecyclerView.Adapter<ListAdapterNull.ViewHolder> {
    private final String message;

    public ListAdapterNull (String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_null, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nullMsgView.setText(message);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nullMsgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nullMsgView = itemView.findViewById(R.id.tVwResultNull);
        }
    }
}

package com.wellstech.tpictest.list_code;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wellstech.tpictest.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListAdapterNewToy extends RecyclerView.Adapter<ListAdapterNewToy.ViewHolder>{
    private final ArrayList<ListItemNewToy> mData;

    public ListAdapterNewToy(ArrayList<ListItemNewToy> list) {
        mData = list;
    }

    @NonNull
    @NotNull
    @Override
    public ListAdapterNewToy.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_newtoy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ListItemNewToy item = mData.get(position);

        holder.imgProduct1.setImageDrawable(item.getImgProduct1());
        holder.imgProduct2.setImageDrawable(item.getImgProduct2());
        holder.nameProduct1.setText(item.getNameProduct1());
        holder.nameProduct2.setText(item.getNameProduct2());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct1, imgProduct2;
        TextView nameProduct1, nameProduct2;

        public ViewHolder(View itemView) {
            super(itemView);
            imgProduct1 = itemView.findViewById(R.id.iVwMainNewToyItem1);
            imgProduct1.setOnClickListener(onClickListener);
            imgProduct2 = itemView.findViewById(R.id.iVwMainNewToyItem2);
            imgProduct2.setOnClickListener(onClickListener);
            nameProduct1 = itemView.findViewById(R.id.tVwMainNewToyItem1);
            nameProduct2 = itemView.findViewById(R.id.tVwMainNewToyItem2);
        }

        @SuppressLint("NonConstantResourceId")
        View.OnClickListener onClickListener = v -> {
          switch (v.getId()) {
              case R.id.iVwMainNewToyItem1:
                  Toast.makeText(v.getContext(), v.getContext().getString(R.string.txt_test_message), Toast.LENGTH_SHORT).show();
                  break;
              case R.id.iVwMainNewToyItem2:
                  Toast.makeText(v.getContext(), v.getContext().getString(R.string.txt_test_message2), Toast.LENGTH_SHORT).show();
                  break;
              default:
                  break;
          }
        };
    }
}

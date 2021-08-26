package com.wellstech.tpictest.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wellstech.tpictest.R;

import java.util.ArrayList;

public class ListAdapterEvalChild extends RecyclerView.Adapter<ListAdapterEvalChild.ViewHolder> {

    private final ArrayList<ListItemEvalChild> childList;
    private final ArrayList<CheckBox> ChildBoxList = new ArrayList<>();

    public interface CheckBoxSelectListener {
        void onSelected(int position);
    }
    private final CheckBoxSelectListener checkBoxSelectListener;

    public ListAdapterEvalChild(ArrayList<ListItemEvalChild> childList, String childData, CheckBoxSelectListener checkBoxSelectListener) {
        this.childList = childList;
        this.checkBoxSelectListener = checkBoxSelectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemEvalChild item = childList.get(position);

        holder.childOrder.setText(item.getChildOrder());
        holder.childNick.setText(item.getChildNick());
        holder.ckbChild.setTag(item.getChildIdx());
        holder.ckbChild.setOnCheckedChangeListener((checkBox, isChecked) -> {
            if (isChecked) {
                checkBoxSelectListener.onSelected(holder.getAdapterPosition());
                // do Goods List Work
            }
            textViewStyleChange(isChecked, holder.childOrder, holder.childNick);
        });
        ChildBoxList.add(holder.ckbChild);
        if (position == 0) holder.ckbChild.performClick();
    }

    public ArrayList<CheckBox> getCheckBoxes() {
        return ChildBoxList;
    }

    private void textViewStyleChange(boolean checked, TextView order, TextView nick) {
        if (checked) {
            order.setTextAppearance(R.style.EvalChildOrderSelectStyle);
            nick.setTextAppearance(R.style.EvalChildNickSelectStyle);
        } else {
            order.setTextAppearance(R.style.EvalChildOrderDeselectStyle);
            nick.setTextAppearance(R.style.EvalChildNickDeselectStyle);
        }
    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox ckbChild;
        TextView childOrder, childNick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ckbChild = itemView.findViewById(R.id.cKbEvaluateChild);
            childOrder = itemView.findViewById(R.id.tVwEvaluateChildOrder);
            childNick = itemView.findViewById(R.id.tVwEvaluateChildNick);
        }
    }
}

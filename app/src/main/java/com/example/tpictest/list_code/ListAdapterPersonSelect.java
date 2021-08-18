package com.example.tpictest.list_code;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tpictest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListAdapterPersonSelect extends RecyclerView.Adapter<ListAdapterPersonSelect.ViewHolder>{

    private final ArrayList<ListItemPersonSelect> mData;

    public ListAdapterPersonSelect(ArrayList<ListItemPersonSelect> list) { mData = list; }

    private static Map<String, String> selectedPerson;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_person_select, parent, false);
        selectedPerson = new HashMap<>();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemPersonSelect item = mData.get(position);

        switch (item.getItemCount()) {
            case 1:
                holder.personImg1.setImageDrawable(item.getPsnlDrawable1());
                holder.personName1.setText(item.getPsnlName1());
//                holder.personCkb1.setChecked(item.isPsnlChecked1());
                break;
            case 2:
                holder.personCly2.setVisibility(View.VISIBLE);
                holder.personImg1.setImageDrawable(item.getPsnlDrawable1());
                holder.personImg2.setImageDrawable(item.getPsnlDrawable2());
                holder.personName1.setText(item.getPsnlName1());
                holder.personName2.setText(item.getPsnlName2());
                break;
            case 3:
                holder.personCly2.setVisibility(View.VISIBLE);
                holder.personCly3.setVisibility(View.VISIBLE);
                holder.personImg1.setImageDrawable(item.getPsnlDrawable1());
                holder.personImg2.setImageDrawable(item.getPsnlDrawable2());
                holder.personImg3.setImageDrawable(item.getPsnlDrawable3());
                holder.personName1.setText(item.getPsnlName1());
                holder.personName2.setText(item.getPsnlName2());
                holder.personName3.setText(item.getPsnlName3());
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

        ConstraintLayout personCly2, personCly3;
        ImageView personImg1, personImg2, personImg3;
        TextView personName1, personName2, personName3;
        CheckBox personCkb1, personCkb2, personCkb3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            personCly2 = itemView.findViewById(R.id.cLyPersonSelect2);
            personCly3 = itemView.findViewById(R.id.cLyPersonSelect3);
            personImg1 = itemView.findViewById(R.id.iVwPersonImage1);
            personImg2 = itemView.findViewById(R.id.iVwPersonImage2);
            personImg3 = itemView.findViewById(R.id.iVwPersonImage3);
            personName1 = itemView.findViewById(R.id.tVwPersonName1);
            personName2 = itemView.findViewById(R.id.tVwPersonName2);
            personName3 = itemView.findViewById(R.id.tVwPersonName3);
            personCkb1 = itemView.findViewById(R.id.cKbPersonSelect1);
            personCkb1.setOnCheckedChangeListener(listener);
            personCkb2 = itemView.findViewById(R.id.cKbPersonSelect2);
            personCkb2.setOnCheckedChangeListener(listener);
            personCkb3 = itemView.findViewById(R.id.cKbPersonSelect3);
            personCkb3.setOnCheckedChangeListener(listener);
        }

        @SuppressLint("NonConstantResourceId")
        CompoundButton.OnCheckedChangeListener listener = (checkBox, isChecked) -> {
            switch (checkBox.getId()) {
                case R.id.cKbPersonSelect1:
                    if (isChecked) {
                        // do tag write work
                        selectedPerson.put(personName1.getText().toString(), personName1.getText().toString());
                        textViewStyleChange(true, personName1);
                    } else {
                        // do tag delete work
                        selectedPerson.remove(personName1.getText().toString());
                        textViewStyleChange(false, personName1);
                    }
                    break;
                case R.id.cKbPersonSelect2:
                    if (isChecked) {
                        selectedPerson.put(personName2.getText().toString(), personName2.getText().toString());
                        textViewStyleChange(true, personName2);
                    } else {
                        selectedPerson.remove(personName2.getText().toString());
                        textViewStyleChange(false, personName2);
                    }
                    break;
                case R.id.cKbPersonSelect3:
                    if (isChecked) {
                        selectedPerson.put(personName3.getText().toString(), personName3.getText().toString());
                        textViewStyleChange(true, personName3);
                    } else {
                        selectedPerson.remove(personName3.getText().toString());
                        textViewStyleChange(false, personName3);
                    }
                    break;
                default:
                    break;
            }
        };

        private void textViewStyleChange(boolean checked, TextView textView) {
            if (checked) {
                textView.setTextAppearance(R.style.SelectTextFocusStyle);
            } else {
                textView.setTextAppearance(R.style.SelectTextNormalStyle);
            }
        }
    }

    public Map<String, String> getSelectedPerson () { return selectedPerson; }
}

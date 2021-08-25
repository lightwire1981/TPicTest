package com.wellstech.tpictest.list_code;

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

import com.wellstech.tpictest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListAdapterCharSelect extends RecyclerView.Adapter<ListAdapterCharSelect.ViewHolder>{

    private final ArrayList<ListItemCharSelect> mData;

    public ListAdapterCharSelect(ArrayList<ListItemCharSelect> list) {
        mData = list;
    }

    private static Map<String, String> selectedChar;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_char_select, parent, false);
        selectedChar = new HashMap<>();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemCharSelect item = mData.get(position);

        switch (item.getItemCount()) {
            case 1:
                holder.characterImg1.setImageDrawable(item.getCharDrawable1());
                holder.characterName1.setText(item.getCharName1());
//                holder.characterCkb1.setChecked(item.isCharChecked1());
                break;
            case 2:
                holder.characterCly2.setVisibility(View.VISIBLE);
                holder.characterImg1.setImageDrawable(item.getCharDrawable1());
                holder.characterImg2.setImageDrawable(item.getCharDrawable2());
                holder.characterName1.setText(item.getCharName1());
                holder.characterName2.setText(item.getCharName2());
//                holder.characterCkb1.setChecked(item.isCharChecked1());
//                holder.characterCkb2.setChecked(item.isCharChecked2());
                break;
            case 3:
                holder.characterCly2.setVisibility(View.VISIBLE);
                holder.characterCly3.setVisibility(View.VISIBLE);
                holder.characterImg1.setImageDrawable(item.getCharDrawable1());
                holder.characterImg2.setImageDrawable(item.getCharDrawable2());
                holder.characterImg3.setImageDrawable(item.getCharDrawable3());
                holder.characterName1.setText(item.getCharName1());
                holder.characterName2.setText(item.getCharName2());
                holder.characterName3.setText(item.getCharName3());
//                holder.characterCkb1.setChecked(item.isCharChecked1());
//                holder.characterCkb2.setChecked(item.isCharChecked2());
//                holder.characterCkb3.setChecked(item.isCharChecked3());
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

        ConstraintLayout characterCly2, characterCly3;
        ImageView characterImg1, characterImg2, characterImg3;
        TextView characterName1, characterName2, characterName3;
        CheckBox characterCkb1, characterCkb2, characterCkb3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            characterCly2 = itemView.findViewById(R.id.cLyCharSelect2);
            characterCly3 = itemView.findViewById(R.id.cLyCharSelect3);
            characterImg1 = itemView.findViewById(R.id.iVwCharImage1);
            characterImg2 = itemView.findViewById(R.id.iVwCharImage2);
            characterImg3 = itemView.findViewById(R.id.iVwCharImage3);
            characterName1 = itemView.findViewById(R.id.tVwCharName1);
            characterName2 = itemView.findViewById(R.id.tVwCharName2);
            characterName3 = itemView.findViewById(R.id.tVwCharName3);
            characterCkb1 = itemView.findViewById(R.id.cKbCharSelect1);
            characterCkb1.setOnCheckedChangeListener(listener);
            characterCkb2 = itemView.findViewById(R.id.cKbCharSelect2);
            characterCkb2.setOnCheckedChangeListener(listener);
            characterCkb3 = itemView.findViewById(R.id.cKbCharSelect3);
            characterCkb3.setOnCheckedChangeListener(listener);
        }

        @SuppressLint("NonConstantResourceId")
        CompoundButton.OnCheckedChangeListener listener = (checkBox, isChecked) -> {
            switch (checkBox.getId()) {
                case R.id.cKbCharSelect1:
                    if (isChecked) {
                        // do tag write work
                        selectedChar.put(characterName1.getText().toString(), characterName1.getText().toString());
                        textViewStyleChange(true, characterName1);
                    } else {
                        // do tag delete work
                        selectedChar.remove(characterName1.getText().toString());
                        textViewStyleChange(false, characterName1);
                    }
                    break;
                case R.id.cKbCharSelect2:
                    if (isChecked) {
                        // do tag write work
                        selectedChar.put(characterName2.getText().toString(), characterName2.getText().toString());
                        textViewStyleChange(true,characterName2);
                    } else {
                        // do tag delete work
                        selectedChar.remove(characterName2.getText().toString());
                        textViewStyleChange(false, characterName2);
                    }
                    break;
                case R.id.cKbCharSelect3:
                    if (isChecked) {
                        // do tag write work
                        selectedChar.put(characterName3.getText().toString(), characterName3.getText().toString());
                        textViewStyleChange(true,characterName3);
                    } else {
                        // do tag delete work
                        selectedChar.remove(characterName3.getText().toString());
                        textViewStyleChange(false, characterName3);
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

    public Map<String, String> getSelectedChar () {
        return selectedChar;
    }
}

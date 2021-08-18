package com.example.tpictest.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tpictest.ChildRegistActivity;
import com.example.tpictest.R;
import com.example.tpictest.utils.MakeDate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildRegistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildRegistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildRegistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildRegistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildRegistFragment newInstance(String param1, String param2) {
        ChildRegistFragment fragment = new ChildRegistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ChildRegistActivity.RegistryStep = ChildRegistActivity.CHILD_REGISTRY_STEP.ADD;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child_regist, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.spinner_kid_order,
                (requireContext().getResources().getStringArray(R.array.txt_my_kids_count))
        );
        adapter.setDropDownViewResource(R.layout.spinner_kid_order);
        Spinner spinner = view.findViewById(R.id.sPnrKidOrder);
        addChildData("child_order", String.valueOf(0));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addChildData("child_order", String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EditText editText = view.findViewById(R.id.eTxtChidNick);
        editText.addTextChangedListener(watcher);

        RadioGroup radioGroup = view.findViewById(R.id.rGrpChildGender);
        addChildData("child_gender", String.valueOf(0));
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);

        CalendarView calendarView = view.findViewById(R.id.calendarChildBirth);
        addChildData("child_birth", new MakeDate().makeDateString(MakeDate.DATE_TYPE.BIRTH));
        calendarView.setOnDateChangeListener(onDateChangeListener);

        return view;
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            Toast.makeText(getContext(), String.valueOf(s), Toast.LENGTH_SHORT).show();
            String value = String.valueOf(s);
            if (value.length() < 1) {
                deleteChildData("child_nick");
            } else {
                addChildData("child_nick", String.valueOf(s));
            }
        }
    };

    @SuppressLint("NonConstantResourceId")
    private final RadioGroup.OnCheckedChangeListener onCheckedChangeListener = (group, checkedId) -> {
        switch (checkedId) {
            case R.id.rBtnChildMale:
                addChildData("child_gender", String.valueOf(0));
                break;
            case R.id.rBtnChildFemale:
                addChildData("child_gender", String.valueOf(1));
                break;
            default:
                break;
        }
    };

    private final CalendarView.OnDateChangeListener onDateChangeListener = (view, year, month, dayOfMonth) -> {
        String dateMonth = (month+1 >= 10) ? String.valueOf(month+1):"0"+(month+1);
        String dateDay = (dayOfMonth >= 10) ? String.valueOf(dayOfMonth):"0"+dayOfMonth;
//        Toast.makeText(getContext(), year +"/"+dateMonth+"/" + dateDay, Toast.LENGTH_SHORT).show();

        addChildData("child_birth", year +"-"+dateMonth+"-" + dateDay);
    };

    private void addChildData(String key, String value) {
        try {
            ChildRegistActivity.CHILD_DATA.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void deleteChildData(String key) {
        ChildRegistActivity.CHILD_DATA.remove(key);
    }

//    public static JSONObject getChildData() {
//
//        return childData;
//    }
}
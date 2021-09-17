package com.auroraworld.toypic.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auroraworld.toypic.MainActivity;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.list_code.ListAdapterCategoryBrand;
import com.auroraworld.toypic.list_code.ListItemCategoryBrand;
import com.auroraworld.toypic.list_code.RecyclerDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final ArrayList<CheckBox> CategoryBoxList = new ArrayList<>();

    private JSONArray brandList = new JSONArray();
    private RecyclerView brandListView;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        view.findViewById(R.id.iBtnCategoryBack).setOnClickListener(v -> {
            int fragmentCount = getParentFragmentManager().getBackStackEntryCount();
            MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(fragmentCount-1).getName());
            getParentFragmentManager().popBackStack();
            MainActivity.tabChanger(getParentFragmentManager());
        });

        CategoryBoxList.add(view.findViewById(R.id.cKbCategoryBoy));
        CategoryBoxList.add(view.findViewById(R.id.cKbCategoryGirl));
        CategoryBoxList.add(view.findViewById(R.id.cKbCategoryBaby));
        CategoryBoxList.add(view.findViewById(R.id.cKbCategoryBoard));
        CategoryBoxList.add(view.findViewById(R.id.cKbCategoryOutdoor));
        CategoryBoxList.add(view.findViewById(R.id.cKbCategoryLego));
        CategoryBoxList.add(view.findViewById(R.id.cKbCategoryStuffed));
        CategoryBoxList.add(view.findViewById(R.id.cKbCategoryCharacter));
        for (CheckBox cbox : CategoryBoxList) {
            cbox.setOnCheckedChangeListener((checkBox, isChecked) -> {
                if (isChecked) {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    CategoryListFragment categoryListFragment = CategoryListFragment.newInstance("0", checkBox.getText().toString());
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(MainActivity.CURRENT_PAGE.name());
                    fragmentTransaction.add(R.id.fLyMain, categoryListFragment).commit();
                    checkBox.setChecked(false);
                }
            });
        }
        brandListView = view.findViewById(R.id.rcyclVwCategoryBrand);
        setLayoutManager(brandListView);
        setBrandList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.CURRENT_PAGE = MainActivity.PAGES.CATEGORY;
    }

    private void setBrandList() {

        new DatabaseRequest(getContext(), result -> {
            try {
                brandList = new JSONArray(result[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (brandList.length() < 1) {
                return;
            }
            ArrayList<ListItemCategoryBrand> mList = new ArrayList<>();
            int count = brandList.length();

            int quotient = count/4;
            int remainder = count%4;

            // 나누어 떨어지면 4개씩 작업
            int index = 0;
            if (quotient > 0) {
                for (int x=0; x<quotient; x++) {
                    ListItemCategoryBrand item = new ListItemCategoryBrand();
                    item.setItemCount(4);
                    try {
                        JSONObject brandInfo = brandList.getJSONObject(index);
                        item.setBrandId1(brandInfo.getString("brandCd"));
                        item.setBrandNm1(brandInfo.getString("makerNm"));
                        index++;
                        brandInfo = brandList.getJSONObject(index);
                        item.setBrandId2(brandInfo.getString("brandCd"));
                        item.setBrandNm2(brandInfo.getString("makerNm"));
                        index++;
                        brandInfo = brandList.getJSONObject(index);
                        item.setBrandId3(brandInfo.getString("brandCd"));
                        item.setBrandNm3(brandInfo.getString("makerNm"));
                        index++;
                        brandInfo = brandList.getJSONObject(index);
                        item.setBrandId4(brandInfo.getString("brandCd"));
                        item.setBrandNm4(brandInfo.getString("makerNm"));
                        index++;
                        mList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 4개 이하 작업
            ListItemCategoryBrand item = new ListItemCategoryBrand();
            if (remainder > 0) item.setItemCount(remainder);
            JSONObject brandInfo;
            switch (remainder) {
                case 1:
                    try {
                        brandInfo = brandList.getJSONObject(index);
                        item.setBrandId1(brandInfo.getString("brandCd"));
                        item.setBrandNm1(brandInfo.getString("makerNm"));
                        mList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        brandInfo = brandList.getJSONObject(index);
                        item.setBrandId1(brandInfo.getString("brandCd"));
                        item.setBrandNm1(brandInfo.getString("makerNm"));
                        index++;
                        brandInfo = brandList.getJSONObject(index);
                        item.setBrandId2(brandInfo.getString("brandCd"));
                        item.setBrandNm2(brandInfo.getString("makerNm"));
                        mList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 3:
                    try {
                        brandInfo = brandList.getJSONObject(index);
                        item.setBrandId1(brandInfo.getString("brandCd"));
                        item.setBrandNm1(brandInfo.getString("makerNm"));
                        index++;
                        brandInfo = brandList.getJSONObject(index);
                        item.setBrandId2(brandInfo.getString("brandCd"));
                        item.setBrandNm2(brandInfo.getString("makerNm"));
                        index++;
                        brandInfo = brandList.getJSONObject(index);
                        item.setBrandId3(brandInfo.getString("brandCd"));
                        item.setBrandNm3(brandInfo.getString("makerNm"));
                        mList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            brandListView.setAdapter(new ListAdapterCategoryBrand(mList, (brandCd, brandNm) -> {
                FragmentManager fragmentManager = getParentFragmentManager();
                CategoryListFragment categoryListFragment = CategoryListFragment.newInstance(brandCd, brandNm);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(MainActivity.CURRENT_PAGE.name());
                fragmentTransaction.add(R.id.fLyMain, categoryListFragment).commit();
            }));
        }).execute(DBRequestType.GET_ALL_BRAND.name());
    }

    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
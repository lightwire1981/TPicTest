package com.wellstech.tpictest.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wellstech.tpictest.MainActivity;
import com.wellstech.tpictest.R;
import com.wellstech.tpictest.db.DBRequestType;
import com.wellstech.tpictest.db.DatabaseRequest;
import com.wellstech.tpictest.list_code.ListAdapterCategoryBrand;
import com.wellstech.tpictest.list_code.ListItemCategoryBrand;
import com.wellstech.tpictest.list_code.RecyclerDecoration;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        view.findViewById(R.id.iBtnCategoryBack).setOnClickListener(onClickListener);

        brandListView = view.findViewById(R.id.rcyclVwCategoryBrand);
//        setBrandListTemp(brandList);
        setBrandList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.CURRENT_PAGE = MainActivity.PAGES.CATEGORY;
    }

    private void setBrandListTemp(RecyclerView recyclerView) {
        ArrayList<ListItemCategoryBrand> mList = new ArrayList<>();

        // 브랜드 8개 가정하여 셋팅
        int index = 0;

        for (int i=0; i < 4; i++) {
            ListItemCategoryBrand item = new ListItemCategoryBrand();
            item.setItemCount(4);
            item.setBrandId1("temp_id"+index);
            item.setImgUrl1("temp_url"+index);
            index++;
            item.setBrandId2("temp_id"+index);
            item.setImgUrl2("temp_url"+index);
            index++;
            item.setBrandId3("temp_id"+index);
            item.setImgUrl3("temp_url"+index);
            index++;
            item.setBrandId4("temp_id"+index);
            item.setImgUrl4("temp_url"+index);
            index++;
            mList.add(item);
        }
        ListAdapterCategoryBrand listAdapterCategoryBrand = new ListAdapterCategoryBrand(mList, getParentFragmentManager());
        recyclerView.setAdapter(listAdapterCategoryBrand);
        setLayoutManager(recyclerView);
    }

    private void setBrandList() {

        new DatabaseRequest(getContext(), executeListener).execute(DBRequestType.GET_ALL_BRAND.name());
    }

    private final DatabaseRequest.ExecuteListener executeListener = result -> {
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
                    index++;
                    brandInfo = brandList.getJSONObject(index);
                    item.setBrandId2(brandInfo.getString("brandCd"));
                    index++;
                    brandInfo = brandList.getJSONObject(index);
                    item.setBrandId3(brandInfo.getString("brandCd"));
                    index++;
                    brandInfo = brandList.getJSONObject(index);
                    item.setBrandId4(brandInfo.getString("brandCd"));
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
                    mList.add(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    brandInfo = brandList.getJSONObject(index);
                    item.setBrandId1(brandInfo.getString("brandCd"));
                    index++;
                    brandInfo = brandList.getJSONObject(index);
                    item.setBrandId2(brandInfo.getString("brandCd"));
                    mList.add(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case 3:
                try {
                    brandInfo = brandList.getJSONObject(index);
                    item.setBrandId1(brandInfo.getString("brandCd"));
                    index++;
                    brandInfo = brandList.getJSONObject(index);
                    item.setBrandId2(brandInfo.getString("brandCd"));
                    index++;
                    brandInfo = brandList.getJSONObject(index);
                    item.setBrandId3(brandInfo.getString("brandCd"));
                    mList.add(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        brandListView.setAdapter(new ListAdapterCategoryBrand(mList, getParentFragmentManager()));
        setLayoutManager(brandListView);
    };

    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.iBtnCategoryBack:
                Log.i("<<<<<<<<<<<< Backstack Entry : ", getParentFragmentManager().getBackStackEntryCount()+"");
                Log.i("<<<<<<<<<<<< Backstack Entry : ", getParentFragmentManager().getBackStackEntryAt(0).getName());

                MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(0).getName());
                getParentFragmentManager().popBackStack();
                break;
        }
    };
}
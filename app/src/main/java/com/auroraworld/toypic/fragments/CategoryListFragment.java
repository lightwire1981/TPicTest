package com.auroraworld.toypic.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auroraworld.toypic.MainActivity;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.list_code.ListAdapterCtgDetailIndex;
import com.auroraworld.toypic.list_code.ListItemCtgDetailIndex;
import com.auroraworld.toypic.list_code.RecyclerDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryListFragment newInstance(String param1, String param2) {
        CategoryListFragment fragment = new CategoryListFragment();
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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_category_list, container, false);

        view.findViewById(R.id.iBtnCListBack).setOnClickListener(onClickListener);

        RecyclerView recyclerView = view.findViewById(R.id.rcyclVwCategoryIndex);
        setCategoryIndexList(recyclerView);
        return view;
    }

    private void setCategoryIndexList(RecyclerView recyclerView) {
        ArrayList<ListItemCtgDetailIndex> mList = new ArrayList<>();

        mList.add(addItem("0", "전체"));
        mList.add(addItem("1", "로봇"));
        mList.add(addItem("2", "자동차/기차"));
        mList.add(addItem("3", "액션"));
        mList.add(addItem("4", "기타"));

        recyclerView.setAdapter(new ListAdapterCtgDetailIndex(mList));
        setLayoutManager(recyclerView);
    }
    private ListItemCtgDetailIndex addItem(String... values) {
        ListItemCtgDetailIndex item = new ListItemCtgDetailIndex();
        item.setIndexId(values[0]);
        item.setIndexName(values[1]);
        return item;
    }

    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(5, 10));
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.iBtnCListBack:
                MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(0).getName());
                getParentFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    };
}
package com.auroraworld.toypic.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auroraworld.toypic.MainActivity;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.list_code.ListAdapterNull;
import com.auroraworld.toypic.list_code.RecyclerDecoration;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPageLikeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPageLikeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_ID = "USER_ID";

    // TODO: Rename and change types of parameters
    private String userId;

    private RecyclerView myLikeListVw;

    public MyPageLikeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId Parameter 1.
     * @return A new instance of fragment MyPageLikeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPageLikeFragment newInstance(String userId) {
        MyPageLikeFragment fragment = new MyPageLikeFragment();
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page_like, container, false);
        myLikeListVw = view.findViewById(R.id.rclVwMyLikeList);
        setLayoutManager(myLikeListVw);
        view.findViewById(R.id.iBtnMypageLikeBack).setOnClickListener(view1 -> {
            int fragmentCount = getParentFragmentManager().getBackStackEntryCount();
            MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(fragmentCount-1).getName());
            getParentFragmentManager().popBackStack();
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getMyLikes() {
        try {
            new DatabaseRequest(getContext(), result -> {
                if (result[0].equals("null")) {
                    myLikeListVw.setAdapter(new ListAdapterNull(getString(R.string.txt_like_null)));
                    return;
                }
            }).execute(DBRequestType.LIKE.name(), new JSONObject().put("id", userId).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
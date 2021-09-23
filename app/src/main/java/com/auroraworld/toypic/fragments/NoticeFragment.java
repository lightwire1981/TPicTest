package com.auroraworld.toypic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auroraworld.toypic.R;
import com.auroraworld.toypic.list_code.ListAdptNotice;
import com.auroraworld.toypic.list_code.ListItemNotice;
import com.auroraworld.toypic.list_code.RecyclerDecoration;
import com.auroraworld.toypic.utils.MakeDate;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        RecyclerView noticeListView = view.findViewById(R.id.rclVwNoticeList);
        setLayoutManager(noticeListView);
        setNotice(noticeListView);

        view.findViewById(R.id.iBtnNoticeBack).setOnClickListener(view1 -> getParentFragmentManager().popBackStack());
        return view;
    }

    private void getNotice() {

    }

    private void setNotice(RecyclerView recyclerView) {
        getNotice();
        ArrayList<ListItemNotice> noticeList = new ArrayList<>();
        ListItemNotice listItemNotice = new ListItemNotice();
        listItemNotice.setNoticeTitle(getString(R.string.txt_notice_title_sample));
        listItemNotice.setNoticeDate(MakeDate.makeDateString(MakeDate.DATE_TYPE.BIRTH));
        listItemNotice.setNoticeContent(getString(R.string.txt_notice_content_sample));
        noticeList.add(listItemNotice);
        recyclerView.setAdapter(new ListAdptNotice(noticeList));
    }


    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(0, 0));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
package com.auroraworld.toypic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.auroraworld.toypic.MainActivity;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.list_code.ListAdapterEvalChild;
import com.auroraworld.toypic.list_code.ListAdapterEvalGoods;
import com.auroraworld.toypic.list_code.ListItemEvalChild;
import com.auroraworld.toypic.list_code.ListItemEvalGoods;
import com.auroraworld.toypic.list_code.RecyclerDecoration;
import com.auroraworld.toypic.utils.ChildOrderConvert;
import com.auroraworld.toypic.utils.CustomDialog;
import com.auroraworld.toypic.utils.PreferenceSetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EvaluateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvaluateFragment extends Fragment {

    //region ValueSetting
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView evaluateChildList, evaluateGoodsList;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<CheckBox> ChildBoxList;
    private String childId;
    private ListAdapterEvalGoods listAdapterEvalGoods;
    private static Map<String, Float> ratingData = new HashMap<>();

    private enum RefreshType {
        REFRESH,
        CHANGE_CHILD
    }

    private final String TAG = getClass().getName();
    //endregion

    public EvaluateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvaluateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EvaluateFragment newInstance(String param1, String param2) {
        EvaluateFragment fragment = new EvaluateFragment();
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
        View view = inflater.inflate(R.layout.fragment_evaluate, container, false);
        evaluateChildList = view.findViewById(R.id.rcyclVwEvaluateChild);
        evaluateGoodsList = view.findViewById(R.id.rcyclVwEvaluateGoods);
        setLayoutManager(evaluateChildList);
        setLayoutManager(evaluateGoodsList);

        refreshLayout = view.findViewById(R.id.swRefEvaluateGoods);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_green_dark
        );
        refreshLayout.setOnRefreshListener(onRefreshListener);

        view.findViewById(R.id.iBtnEvaluateBack).setOnClickListener(button -> {
            int fragmentCount = getParentFragmentManager().getBackStackEntryCount();
            MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(fragmentCount-1).getName());
//            getParentFragmentManager().popBackStack();
            MainActivity.tabChanger(getParentFragmentManager());
        });
        return view;
    }
    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        if (recyclerView.getId() == R.id.rcyclVwEvaluateChild) {
            recyclerView.addItemDecoration(new RecyclerDecoration(15, 15));
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        } else {
            recyclerView.addItemDecoration(new RecyclerDecoration(0, 15));
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        }
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.CURRENT_PAGE = MainActivity.PAGES.EVALUATE;
        setChildList();
    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = () -> {
        ratingData = listAdapterEvalGoods.getRatingData();
        if (!ratingData.isEmpty()) {
            new CustomDialog(getContext(), CustomDialog.DIALOG_CATEGORY.EVALUATE_CONFIRM, (response, data) -> {
                if (response) {
                    saveEvaluatedGoods(RefreshType.REFRESH, 0);
                } else {
                    setGoodsList(childId);
                }
            }).show();
        } else {
            setGoodsList(childId);
        }
    };

    private void setChildList() {
        String userInfo = new PreferenceSetting(getContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO);
        try {
            JSONObject object = new JSONObject();
            object.put("id", (new JSONObject(userInfo)).get("id"));
            new DatabaseRequest(getContext(), result -> {
                if(result[0].equals("NO_CHILD")){
                    new CustomDialog(getActivity(), CustomDialog.DIALOG_CATEGORY.NO_CHILD_INFORM, (response, data) -> {
                        int fragmentCount = getParentFragmentManager().getBackStackEntryCount();
                        MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(fragmentCount-1).getName());
                        getParentFragmentManager().popBackStack();
                    }).show();
                    return;
                }
                ArrayList<ListItemEvalChild> mList = new ArrayList<>();
                try {
                    JSONArray list = new JSONArray(result[0]);
                    for(int index=0; index<list.length(); index++) {
                        JSONObject child = list.getJSONObject(index);
                        ListItemEvalChild item = new ListItemEvalChild();
                        item.setChildIdx(child.getString("idx"));
                        item.setChildOrder(ChildOrderConvert.ConvertIntToOrder(requireContext(), child.getString("child_order")));
                        item.setChildNick(child.getString("child_nick"));
                        mList.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ListAdapterEvalChild listAdapterEvalChild = new ListAdapterEvalChild(mList, result[0], checkBoxSelectListener);
                ChildBoxList = listAdapterEvalChild.getCheckBoxes();
                evaluateChildList.setAdapter(listAdapterEvalChild);
//                ChildBoxList.get(0).performClick();
            }).execute(DBRequestType.GET_CHILD.name(),
                    object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private final ListAdapterEvalChild.CheckBoxSelectListener checkBoxSelectListener = new ListAdapterEvalChild.CheckBoxSelectListener() {

        @Override
        public void onSelectedPosition(CheckBox checkBox, int position) {
            // region RadioButton Action
            int index = 0;
            for (CheckBox cbox : ChildBoxList) {
                if (index != position) {
                    cbox.setChecked(false);
                }
                index++;
            }
            //endregion
            if (listAdapterEvalGoods == null) {
                childId = ChildBoxList.get(position).getTag().toString();
                setGoodsList(childId);
                return;
            }
            ratingData = listAdapterEvalGoods.getRatingData();
            if (!ratingData.isEmpty()) {
                new CustomDialog(getContext(), CustomDialog.DIALOG_CATEGORY.EVALUATE_CONFIRM, (response, data) -> {
                    if (response) {
                        saveEvaluatedGoods(RefreshType.CHANGE_CHILD, position);
                    } else {
                        childId = ChildBoxList.get(position).getTag().toString();
                        setGoodsList(childId);
                    }
                }).show();
            } else {
                childId = ChildBoxList.get(position).getTag().toString();
                setGoodsList(childId);
            }
        }

        @Override
        public void onSelectedCheckBox(CheckBox checkBox, boolean isChecked) {

        }
    };

    /**
     * 평가할 상품 리스트 설정
     * @param childId 자녀가 평가하지 않은 상품을 선택하기 위한 자녀 ID
     */
    private void setGoodsList(String childId) {
        try {
            new DatabaseRequest(getContext(), executeListener).execute(DBRequestType.GET_EVALUATE_GOODS.name(),
                    (new JSONObject().put("idx", childId)).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    DatabaseRequest.ExecuteListener executeListener = result -> {
        ArrayList<ListItemEvalGoods> mList = new ArrayList<>();
        try {
            JSONArray goodsArray = new JSONArray(result[0]);
            for (int index=0; index<goodsArray.length(); index++) {
                JSONObject goodsInfo = goodsArray.getJSONObject(index);
                ListItemEvalGoods item = new ListItemEvalGoods();
                item.setGoodsId(goodsInfo.getString("goodsNo"));
                item.setImgUrl(goodsInfo.getString("detailImageData"));
                item.setCategory(null);
                item.setGoodsName(goodsInfo.getString("goodsNm"));
                item.setRatingPoint(0f);
                mList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listAdapterEvalGoods = new ListAdapterEvalGoods(mList);
        evaluateGoodsList.setAdapter(listAdapterEvalGoods);
        refreshLayout.setRefreshing(false);
    };

    /**
     * 리스트 갱신 진행 시 현재 별점 평가 서버 저장 시도
     */
    private void saveEvaluatedGoods(RefreshType type, int position) {

//        ratingData = listAdapterEvalGoods.getRatingData();
        Set<String> temp = ratingData.keySet();
        JSONArray dataArray = new JSONArray();

        for (String key : temp) {
            try {
                JSONObject evalData = new JSONObject();
                evalData.put("goods_id", key);
                evalData.put("goods_evaluate", ratingData.get(key));
                dataArray.put(evalData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject packetData = new JSONObject();
        try {
            packetData.put("idx", childId);
            packetData.put("data", dataArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new DatabaseRequest(getContext(), result -> {
            if (result[0].equals("INSERT_OK")) {
                Toast.makeText(getContext(), "평가가 반영되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "평가가 처리되지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
            switch (type) {
                case REFRESH:
                    setGoodsList(childId);
                    break;
                case CHANGE_CHILD:
                    childId = ChildBoxList.get(position).getTag().toString();
                    setGoodsList(childId);
                    break;
                default:
                    break;
            }
        }).execute(DBRequestType.INSERT_EVALUATE_GOODS.name(), packetData.toString());
    }
}
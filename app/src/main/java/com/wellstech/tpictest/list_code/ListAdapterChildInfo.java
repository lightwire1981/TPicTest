package com.wellstech.tpictest.list_code;

import static androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.wellstech.tpictest.ChildRegistActivity;
import com.wellstech.tpictest.R;
import com.wellstech.tpictest.fragments.MyPageChildEditFragment;

import java.util.ArrayList;

public class ListAdapterChildInfo extends RecyclerView.Adapter<ListAdapterChildInfo.ViewHolder> {

    private final ArrayList<ListItemChildInfo> mData;
    private FragmentManager fragmentManager;
    private String childData;
    public ListAdapterChildInfo(ArrayList<ListItemChildInfo> list, FragmentManager fragmentManager, String childData) {
        mData = list;
        this.fragmentManager = fragmentManager;
        this.childData = childData;
//        this.backStackListener = backStackListener;
    }

    public interface BackStackListener {
        void OnFragmentBack();
    }
    private BackStackListener backStackListener;

    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_child_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemChildInfo item = mData.get(position);

        switch (item.getItemCount()) {
            case 1:
                if (item.isAddAction1()) {
                    holder.childBtn1.setImageDrawable(item.getDrawableAddChild());
                } else {
                    holder.childOrder1.setText(item.getChildOrder1());
                    holder.childNick1.setText(item.getChildNick1());
                    holder.childBtn1.setTag(item.getChildIdx1());
                }
                holder.childBtn1.setOnClickListener(onClickListener);
                break;
            case 2:
                holder.childOrder1.setText(item.getChildOrder1());
                holder.childNick1.setText(item.getChildNick1());
                holder.childBtn1.setTag(item.getChildIdx1());

                holder.childCly2.setVisibility(View.VISIBLE);
                if (item.isAddAction2()) {
                    holder.childBtn2.setImageDrawable(item.getDrawableAddChild());
                } else {
                    holder.childOrder2.setText(item.getChildOrder2());
                    holder.childNick2.setText(item.getChildNick2());
                    holder.childBtn2.setTag(item.getChildIdx2());
                }
                holder.childBtn1.setOnClickListener(onClickListener);
                holder.childBtn2.setOnClickListener(onClickListener);
                break;
            case 3:
                holder.childOrder1.setText(item.getChildOrder1());
                holder.childNick1.setText(item.getChildNick1());
                holder.childBtn1.setTag(item.getChildIdx1());

                holder.childCly2.setVisibility(View.VISIBLE);
                holder.childOrder2.setText(item.getChildOrder2());
                holder.childNick2.setText(item.getChildNick2());
                holder.childBtn2.setTag(item.getChildIdx2());

                holder.childCly3.setVisibility(View.VISIBLE);
                if (item.isAddAction3()) {
                    holder.childBtn3.setImageDrawable(item.getDrawableAddChild());
                } else {
                    holder.childOrder3.setText(item.getChildOrder3());
                    holder.childNick3.setText(item.getChildNick3());
                    holder.childBtn3.setTag(item.getChildIdx3());
                }
                holder.childBtn1.setOnClickListener(onClickListener);
                holder.childBtn2.setOnClickListener(onClickListener);
                holder.childBtn3.setOnClickListener(onClickListener);
                break;
            default:
                break;
        }
    }

    private final View.OnClickListener onClickListener = view -> {
        if (view.getTag()==null) { // 아이 신규 등록
            Intent intent = new Intent(view.getContext(), ChildRegistActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.addToBackStack(MainActivity.CURRENT_PAGE.name());
//            fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//                @Override
//                public void onBackStackChanged() {
//                    if (MainActivity.CURRENT_PAGE == MainActivity.PAGES.MY_CHILD && backStackListener != null) {
//                        backStackListener.OnFragmentBack();
//                    }
//                }
//            });

//            fragmentTransaction.add(R.id.fLyMain, MyPageChildEditFragment.newInstance(view.getTag().toString(), childData)).commit();
            fragmentTransaction.setTransition(TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.replace(R.id.fLyMain, MyPageChildEditFragment.newInstance(view.getTag().toString(), childData)).commit();
        }
    };



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout childCly2, childCly3;
        ImageButton childBtn1, childBtn2, childBtn3;
        TextView childOrder1, childOrder2, childOrder3;
        TextView childNick1, childNick2, childNick3;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childCly2 = itemView.findViewById(R.id.cLyChild2);
            childCly3 = itemView.findViewById(R.id.cLyChild3);
            childBtn1 = itemView.findViewById(R.id.iBtnChildBg1);
            childBtn2 = itemView.findViewById(R.id.iBtnChildBg2);
            childBtn3 = itemView.findViewById(R.id.iBtnChildBg3);
            childOrder1 = itemView.findViewById(R.id.tVwChildOrder1);
            childOrder2 = itemView.findViewById(R.id.tVwChildOrder2);
            childOrder3 = itemView.findViewById(R.id.tVwChildOrder3);
            childNick1 = itemView.findViewById(R.id.tVwChildNick1);
            childNick2 = itemView.findViewById(R.id.tVwChildNick2);
            childNick3 = itemView.findViewById(R.id.tVwChildNick3);
        }
    }
}

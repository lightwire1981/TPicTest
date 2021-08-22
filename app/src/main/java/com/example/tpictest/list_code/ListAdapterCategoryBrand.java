package com.example.tpictest.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tpictest.MainActivity;
import com.example.tpictest.R;
import com.example.tpictest.fragments.CategoryListFragment;

import java.util.ArrayList;

public class ListAdapterCategoryBrand extends RecyclerView.Adapter<ListAdapterCategoryBrand.ViewHolder> {

    private final ArrayList<ListItemCategoryBrand> mData;
    private FragmentManager fragmentManager;

    public ListAdapterCategoryBrand(ArrayList<ListItemCategoryBrand> list, FragmentManager fragmentManager) {
        mData = list;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_category_brand, parent, false);
        return new ViewHolder(view);
    }

    private static class BrandInfo {
        final String branID;
        final String imgUrl;
        public BrandInfo(String id, String url) {
            this.branID = id;
            this.imgUrl = url;
        }

        public String getBranID() {
            return branID;
        }

        public String getImgUrl() {
            return imgUrl;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemCategoryBrand item = mData.get(position);

        switch (item.getItemCount()) {
            case 1:
                holder.brandImg1.setImageDrawable(item.getBrandDrawable1());
                holder.brandImg1.setTag(new BrandInfo(item.getBrandId1(), item.getImgUrl1()));
                holder.brandImg1.setOnClickListener(listener);
                break;
            case 2:
                holder.brandImg2.setVisibility(View.VISIBLE);
                holder.brandImg1.setTag(new BrandInfo(item.getBrandId1(), item.getImgUrl1()));
                holder.brandImg2.setTag(new BrandInfo(item.getBrandId2(), item.getImgUrl2()));
                holder.brandImg1.setOnClickListener(listener);
//                holder.brandImg1.setImageDrawable(item.getBrandDrawable1());
//                holder.brandImg2.setImageDrawable(item.getBrandDrawable2());
                break;
            case 3:
                holder.brandImg2.setVisibility(View.VISIBLE);
                holder.brandImg3.setVisibility(View.VISIBLE);
                holder.brandImg1.setTag(new BrandInfo(item.getBrandId1(), item.getImgUrl1()));
                holder.brandImg2.setTag(new BrandInfo(item.getBrandId2(), item.getImgUrl2()));
                holder.brandImg3.setTag(new BrandInfo(item.getBrandId3(), item.getImgUrl3()));
                holder.brandImg1.setOnClickListener(listener);
//                holder.brandImg1.setImageDrawable(item.getBrandDrawable1());
//                holder.brandImg2.setImageDrawable(item.getBrandDrawable2());
//                holder.brandImg3.setImageDrawable(item.getBrandDrawable3());
                break;
            case 4:
                holder.brandImg2.setVisibility(View.VISIBLE);
                holder.brandImg3.setVisibility(View.VISIBLE);
                holder.brandImg4.setVisibility(View.VISIBLE);
                holder.brandImg1.setTag(new BrandInfo(item.getBrandId1(), item.getImgUrl1()));
                holder.brandImg2.setTag(new BrandInfo(item.getBrandId2(), item.getImgUrl2()));
                holder.brandImg3.setTag(new BrandInfo(item.getBrandId3(), item.getImgUrl3()));
                holder.brandImg4.setTag(new BrandInfo(item.getBrandId4(), item.getImgUrl4()));
                holder.brandImg1.setOnClickListener(listener);
//                holder.brandImg1.setImageDrawable(item.getBrandDrawable1());
//                holder.brandImg2.setImageDrawable(item.getBrandDrawable2());
//                holder.brandImg3.setImageDrawable(item.getBrandDrawable3());
//                holder.brandImg4.setImageDrawable(item.getBrandDrawable4());
                break;
            default:
                break;
        }

    }
    private final View.OnClickListener listener = view -> {
        Toast.makeText(view.getContext(), ((BrandInfo)view.getTag()).getBranID(), Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager = this.fragmentManager;
        CategoryListFragment categoryListFragment = new CategoryListFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(MainActivity.PAGES.CATEGORY.name());
        fragmentTransaction.add(R.id.fLyMain, categoryListFragment).commit();
    };

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView brandImg1, brandImg2, brandImg3, brandImg4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandImg1 = itemView.findViewById(R.id.iBtnBrandImage1);
            brandImg2 = itemView.findViewById(R.id.iBtnBrandImage2);
            brandImg3 = itemView.findViewById(R.id.iBtnBrandImage3);
            brandImg4 = itemView.findViewById(R.id.iBtnBrandImage4);
        }
    }
}

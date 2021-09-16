package com.auroraworld.toypic.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auroraworld.toypic.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class ListAdapterCategoryBrand extends RecyclerView.Adapter<ListAdapterCategoryBrand.ViewHolder> {

    private final ArrayList<ListItemCategoryBrand> mData;

    public interface SelectBrandListener {
        void onSelectBrand(String brandCd, String brandNm);
    }
    private SelectBrandListener selectBrandListener;

    public ListAdapterCategoryBrand(ArrayList<ListItemCategoryBrand> list, SelectBrandListener selectBrandListener) {
        mData = list;
        this.selectBrandListener = selectBrandListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_category_brand, parent, false);
        return new ViewHolder(view);
    }

    private static class BrandInfo {
        final String brandId;
        final String imgUrl;
        final String brandNm;
        public BrandInfo(String id, String url, String brandNm) {
            this.brandId = id;
            this.imgUrl = url;
            this.brandNm = brandNm;
        }

        public String getBrandId() {
            return brandId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public String getBrandNm() { return brandNm; }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemCategoryBrand item = mData.get(position);

        switch (item.getItemCount()) {
            case 1:
                holder.brandImg1.setImageDrawable(item.getBrandDrawable1());
                holder.brandImg1.setTag(new BrandInfo(item.getBrandId1(), item.getImgUrl1(), item.getBrandNm1()));
                holder.brandImg1.setOnClickListener(listener);
                break;
            case 2:
                holder.brandImg2.setVisibility(View.VISIBLE);
                holder.brandImg1.setTag(new BrandInfo(item.getBrandId1(), item.getImgUrl1(), item.getBrandNm1()));
                holder.brandImg2.setTag(new BrandInfo(item.getBrandId2(), item.getImgUrl2(), item.getBrandNm2()));
                holder.brandImg1.setOnClickListener(listener);
                holder.brandImg2.setOnClickListener(listener);
                break;
            case 3:
                holder.brandImg2.setVisibility(View.VISIBLE);
                holder.brandImg3.setVisibility(View.VISIBLE);
                holder.brandImg1.setTag(new BrandInfo(item.getBrandId1(), item.getImgUrl1(), item.getBrandNm1()));
                holder.brandImg2.setTag(new BrandInfo(item.getBrandId2(), item.getImgUrl2(), item.getBrandNm2()));
                holder.brandImg3.setTag(new BrandInfo(item.getBrandId3(), item.getImgUrl3(), item.getBrandNm3()));
                holder.brandImg1.setOnClickListener(listener);
                holder.brandImg2.setOnClickListener(listener);
                holder.brandImg3.setOnClickListener(listener);
                break;
            case 4:
                holder.brandImg2.setVisibility(View.VISIBLE);
                holder.brandImg3.setVisibility(View.VISIBLE);
                holder.brandImg4.setVisibility(View.VISIBLE);
                holder.brandImg1.setTag(new BrandInfo(item.getBrandId1(), item.getImgUrl1(), item.getBrandNm1()));
                holder.brandImg2.setTag(new BrandInfo(item.getBrandId2(), item.getImgUrl2(), item.getBrandNm2()));
                holder.brandImg3.setTag(new BrandInfo(item.getBrandId3(), item.getImgUrl3(), item.getBrandNm3()));
                holder.brandImg4.setTag(new BrandInfo(item.getBrandId4(), item.getImgUrl4(), item.getBrandNm4()));
                holder.brandImg1.setOnClickListener(listener);
                holder.brandImg2.setOnClickListener(listener);
                holder.brandImg3.setOnClickListener(listener);
                holder.brandImg4.setOnClickListener(listener);
                break;
            default:
                break;
        }

    }
    private final View.OnClickListener listener = view -> selectBrandListener.onSelectBrand(((BrandInfo) view.getTag()).getBrandId(),
            ((BrandInfo) view.getTag()).getBrandNm());

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

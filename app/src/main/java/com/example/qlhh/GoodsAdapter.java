package com.example.qlhh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder>{
    private Context mContext;
    private List<Goods> mGoodsList;

    public static final int EDIT_GOODS_REQUEST_CODE = 1;

    public GoodsAdapter(Context context, List<Goods> goodsList) {
        mContext = context;
        mGoodsList = goodsList;
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goods_list_item, parent, false);
        return new GoodsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        final Goods goods = mGoodsList.get(position);
        holder.nameTextView.setText(goods.getTenloai());
        holder.categoryTextView.setText(goods.getTenloaikd());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goods selectedGoods = mGoodsList.get(holder.getAdapterPosition());
                Intent intent = new Intent(mContext, EditGoodsActivity.class);
                intent.putExtra("selectedGoods", selectedGoods);
                ((Activity) mContext).startActivityForResult(intent, EDIT_GOODS_REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGoodsList.size();
    }

    public void setData(List<Goods> goodsList) {
        this.mGoodsList = goodsList;
        notifyDataSetChanged();
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView categoryTextView;

        GoodsViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.goods_name);
            categoryTextView = view.findViewById(R.id.goods_category);
        }


    }


}

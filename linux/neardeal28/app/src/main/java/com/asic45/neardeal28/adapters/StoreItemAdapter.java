package com.asic45.neardeal28.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.asic45.neardeal28.R;
import com.asic45.neardeal28.api_responses.Store;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StoreItemAdapter extends RecyclerView.Adapter<StoreItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Store> mStoreList;
    private LayoutInflater mLayoutInflater;

    public StoreItemAdapter(Context context, List<Store> storeList){
        mContext = context;
        mStoreList = storeList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(mLayoutInflater.inflate(R.layout.item_store, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Store store = mStoreList.get(position);

        holder.nameTextView.setText(store.getName());
        holder.dealTextView.setText("Belum Ada Deal!");
        Picasso.with(mContext).load(store.getPhoto()).into((holder.imageView));
    }

    @Override
    public int getItemCount() {
        return mStoreList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView container;
        public ImageView imageView;
        public TextView nameTextView;
        public TextView dealTextView;

        public ViewHolder(View itemView){
            super(itemView);

            container = itemView.findViewById(R.id.container);
            imageView = itemView.findViewById(R.id.iv_gambar_toko);
            nameTextView = itemView.findViewById(R.id.tv_nama_toko);
            dealTextView = itemView.findViewById(R.id.tv_deal);

        }
    }
}
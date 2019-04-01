package com.asic45.neardeal28.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.asic45.neardeal28.R;
import com.asic45.neardeal28.models.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Cart> mCartList;
    private LayoutInflater mLayoutInflater;
    private ProductItemAdapter.OnItemClickListener mListener;


    public CartItemAdapter(Context context, List<Cart> cartList){
        mContext = context;
        mCartList = cartList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setListener(ProductItemAdapter.OnItemClickListener listener){
        mListener = listener;
    }


    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartItemAdapter.ViewHolder viewHolder = new CartItemAdapter.ViewHolder(mLayoutInflater.inflate(R.layout.item_cart, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CartItemAdapter.ViewHolder holder, int position) {
        final Cart cart = mCartList.get(position);

        holder.nameTextView.setText(cart.getProductName());
        holder.priceTextView.setText(Double.toString(cart.getPrice()));

        Picasso.with(mContext).load(cart.getPhoto()).into(holder.imageView);

        if (mListener != null){
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(cart.getProductId());
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public FrameLayout container;
        public ImageView imageView;
        public TextView nameTextView;
        public TextView priceTextView;

        public ViewHolder(View itemView){
            super(itemView);

            container = itemView.findViewById(R.id.container);
            imageView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.tv_name);
            priceTextView = itemView.findViewById(R.id.tv_price);

        }
    }
    public interface OnItemClickListener{
        public void onItemClick(String productId);
    }
}

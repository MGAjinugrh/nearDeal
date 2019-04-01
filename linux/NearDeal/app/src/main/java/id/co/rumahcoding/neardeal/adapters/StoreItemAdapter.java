package id.co.rumahcoding.neardeal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import id.co.rumahcoding.neardeal.R;
import id.co.rumahcoding.neardeal.responses.Store;

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
            imageView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.txtNamaToko);
            dealTextView = itemView.findViewById(R.id.txtDeal);

        }
    }
}
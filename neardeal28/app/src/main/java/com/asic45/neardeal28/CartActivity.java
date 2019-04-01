package com.asic45.neardeal28;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.asic45.neardeal28.adapters.CartItemAdapter;
import com.asic45.neardeal28.models.Cart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private CartItemAdapter mAdapter;
    private List<Cart> mCartList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mCartList = new ArrayList<>();
        mAdapter = new CartItemAdapter(this, mCartList);
        recyclerView.setAdapter(mAdapter);

        loadCarts();
    }

    private void loadCarts() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Cart> realmResults = realm.where(Cart.class).findAll();

        for(int i = 0; i < realmResults.size(); i++) {
            Cart cart = realmResults.get(i);
            mCartList.add(realm.copyFromRealm(cart));
        }

        mAdapter.notifyDataSetChanged();
        realm.close();
    }

    @OnClick({R.id.btn_checkout})
    public void onButtonClick(Button button){
        Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
        startActivity(intent);
    }
}

package com.asic45.neardeal28;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.asic45.neardeal28.R;
import com.asic45.neardeal28.api_responses.CheckoutResponse;
import com.asic45.neardeal28.api_services.ApiClient;
import com.asic45.neardeal28.api_services.ApiEndPoint;
import com.asic45.neardeal28.models.Cart;
import com.asic45.neardeal28.utils.ConnectivityUtil;
import com.asic45.neardeal28.utils.PopupUtil;
import com.asic45.neardeal28.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText nameEditText;

    @BindView(R.id.et_no_hp)
    EditText noHpEditText;

    @BindView(R.id.et_address)
    EditText addressEditText;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);
        sharedPrefManager = new SharedPrefManager(this);
    }

    @OnClick({R.id.btn_submit})
    public void onClick(Button button) {
        int id = button.getId();

        if(id == R.id.btn_submit) {
            if(ConnectivityUtil.isConnected(this)) {
                submit();
            }
            else {
                PopupUtil.showMsg(this, "No internet connection", PopupUtil.SHORT);
            }
        }
    }

    private void submit() {
        String userIds = sharedPrefManager.getUserId();
        String name = nameEditText.getText().toString();
        String noHp = noHpEditText.getText().toString();
        String address = addressEditText.getText().toString();
        List<String> productIdList = new ArrayList<>();
        List<String> priceList = new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Cart> realmResults = realm.where(Cart.class).findAll();

        for(int i = 0; i < realmResults.size(); i++) {
            Cart cart = realmResults.get(i);
            productIdList.add(cart.getProductId());
            priceList.add(Double.toString(cart.getPrice()));
        }

        realm.close();
        Realm.deleteRealm(realm.getConfiguration());

        PopupUtil.showLoading( this, "", "Please wait....");

        String productIds = TextUtils.join(",", productIdList);
        String prices = TextUtils.join(",", priceList);

        ApiEndPoint apiEndPoint = ApiClient.getClient(this).create(ApiEndPoint.class);
        Call<CheckoutResponse> call = apiEndPoint.checkout(userIds, name, noHp, address, productIds, prices);

        call.enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                PopupUtil.dismissDialog();
                final CheckoutResponse checkoutResponse = response.body();

                if (checkoutResponse.getSuccess()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            PopupUtil.showMsg(CheckoutActivity.this, "Checkout berhasil", PopupUtil.SHORT);
                            Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                PopupUtil.dismissDialog();
                System.out.println("Gagal");

            }
        });
    }
}

package com.asic45.neardeal28;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.asic45.neardeal28.api_responses.ProductDetailResponse;
import com.asic45.neardeal28.api_services.ApiClient;
import com.asic45.neardeal28.api_services.ApiEndPoint;
import com.asic45.neardeal28.models.Cart;
import com.asic45.neardeal28.utils.ConnectivityUtil;
import com.asic45.neardeal28.utils.PopupUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    String mProductId;
    int newPrice;
    ProductDetailResponse productDetailResponse;

    @BindView(R.id.iv_product_image)
    ImageView gambarProduk;

    @BindView(R.id.cv_image)
    ImageView gambarToko;

    @BindView(R.id.tv_product_name)
    TextView namaProduk;

    @BindView(R.id.tv_product_date)
    TextView tanggal;

    @BindView(R.id.et_product_desc)
    TextView deskripsi;

    @BindView(R.id.tv_product_price_new)
    TextView hargaBaru;

    @BindView(R.id.tv_product_price_old)
    TextView hargaLama;

    private ProductDetailResponse detailResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        mProductId = getIntent().getStringExtra("product_id");
        System.out.println("Detail Product Id : "+mProductId);
        newPrice = getIntent().getIntExtra("new_price",0);
        System.out.println("Detail Product Price : "+newPrice);
        ButterKnife.bind(this);
        if(ConnectivityUtil.isConnected(this)){
            loadDetail();
        }else{
            PopupUtil.showMsg( this, "No Internet connection", PopupUtil.SHORT);
        }
    }

    private void loadDetail(){
        PopupUtil.showLoading( this, "", "Loading stores....");

        ApiEndPoint apiEndPoint = ApiClient.getClient(this).create(ApiEndPoint.class);
        Call<ProductDetailResponse> call = apiEndPoint.getProductDetail(mProductId);

        call.enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                PopupUtil.dismissDialog();
                final ProductDetailResponse DetailResponse = response.body();

                if (DetailResponse != null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Picasso.with(ProductDetailActivity.this).load(DetailResponse.getProductDetail().getPhoto()).into(gambarProduk);
                            Picasso.with(ProductDetailActivity.this).load(DetailResponse.getProductDetail().getStorePhoto()).into(gambarToko);

                            namaProduk.setText(DetailResponse.getProductDetail().getName());
                            deskripsi.setText(DetailResponse.getProductDetail().getDescription());
                            hargaLama.setText(String.valueOf(DetailResponse.getProductDetail().getPrice()));
                            hargaBaru.setText(String.valueOf(newPrice));

                            hargaLama.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                            setTitle(DetailResponse.getProductDetail().getName());

                            detailResponse = DetailResponse;

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                PopupUtil.dismissDialog();
                System.out.println("Gagal cuy");

            }
        });

    }

    @OnClick({R.id.btn_buy})
    public void onClick(Button button) {
        Realm realm = Realm.getDefaultInstance();
        // get product detail
        final String name = detailResponse.getProductDetail().getName();
        final double price = Double.parseDouble(String.valueOf(newPrice));
        final String photo = detailResponse.getProductDetail().getPhoto();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // find last id
                RealmResults<Cart> carts = realm.where(Cart.class)
                        .findAllSorted("id", Sort.DESCENDING);

                int lastId = 0;

                if(carts.size() > 0) {
                    lastId = carts.first().getId();
                }

                Cart cart = new Cart();
                cart.setId(lastId + 1);
                cart.setProductId(mProductId);
                cart.setProductName(name);
                cart.setPrice(price);
                cart.setPhoto(photo);

                realm.copyToRealm(cart);

                PopupUtil.showMsg(ProductDetailActivity.this, "Berhasil ditambahkan ke keranjang belanja",
                        PopupUtil.SHORT);
            }
        });

        realm.close();
    }


}

package com.hq.projectads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hq.projectads.model.Request;
import com.hq.projectads.model.User;
import com.hq.projectads.retrofit.RetrofitAPI;
import com.hq.projectads.utils.valLocal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetMoney extends AppCompatActivity {
    EditText edt_Sotien;
    EditText edt_Thongtin1;
    EditText edt_Thongtin2;
    Button btn_Xacnhan;
    SharedPreferences sharedPreferences;
    int SoTien = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_money);
        sharedPreferences = getSharedPreferences(valLocal.containerLogin, MODE_PRIVATE);

        ImageView back = findViewById(R.id.btnBackFromNH);
        back.setOnClickListener(v -> {
            finish();
        });

        edt_Sotien = findViewById(R.id.edt_soTien_rutTien);
        edt_Thongtin1 = findViewById(R.id.edt_stk_rutTien);
        edt_Thongtin2 = findViewById(R.id.edt_nganHang_rutTien);
        btn_Xacnhan = findViewById(R.id.btn_Xacnhan_Rutien);

        loadUser();
        kiemTraSoTien();

        btn_Xacnhan.setOnClickListener(e -> {
            String tai_khoan = sharedPreferences.getString(valLocal.taikhoan_user, "");
            String sotien = edt_Sotien.getText().toString().trim();
            String tt1 = edt_Thongtin1.getText().toString().trim();
            String tt2 = edt_Thongtin2.getText().toString().trim();

            if (sotien.equals("") || tt1.equals("") || tt2.equals("")) {
                Toast.makeText(this, "Thông tin không được để trống!\nInformation empty!", Toast.LENGTH_SHORT).show();

            } else if (SoTien == -1 || Integer.parseInt(sotien) > SoTien) {
                Toast.makeText(GetMoney.this, "The amount you enter is more than the amount you currently have!", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Số tiền bạn nhập nhiều hơn số tiền bạn đang có!", Toast.LENGTH_SHORT).show();

            } else {
                Request request = new Request(tai_khoan, Integer.parseInt(sotien), tt1, tt2);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(valLocal.urlLocal)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
                Call<Integer> call = retrofitAPI.sendRequest(request);

                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.body() == 1) {
                            Toast.makeText(GetMoney.this, "Gửi yêu cầu thành công!", Toast.LENGTH_SHORT).show();
                            loadUser();
                        } else {
                            Toast.makeText(GetMoney.this, "Network error, please restart app!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(GetMoney.this, "Lỗi kết nối mạng, vui lòng mở lại ứng dụng!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(GetMoney.this, "Network error, please restart app!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(GetMoney.this, "Lỗi kết nối mạng, vui lòng mở lại ứng dụng!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void loadUser() {
        String tai_khoan = sharedPreferences.getString(valLocal.taikhoan_user, "");
        Request request = new Request(tai_khoan, 0, "", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(valLocal.urlLocal)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Request> call = retrofitAPI.getRequestObject(request);

        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (!response.body().getTai_khoan().equals("")) {
                    edt_Sotien.setText("Số tiền: " + response.body().getSo_tien());
                    edt_Thongtin1.setText("Số tài khoản: " + response.body().getThong_tin1());
                    edt_Thongtin2.setText("Tên ngân hàng:" + response.body().getThong_tin2());
                    btn_Xacnhan.setEnabled(false);
                    btn_Xacnhan.setText("Đã gửi yêu cầu");
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                Toast.makeText(GetMoney.this, "Network error, please restart app!", Toast.LENGTH_SHORT).show();
                Toast.makeText(GetMoney.this, "Lỗi kết nối mạng, vui lòng mở lại ứng dụng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void kiemTraSoTien() {
        btn_Xacnhan.setEnabled(false);
        btn_Xacnhan.setText("Đang tải...");

        String tai_khoan = sharedPreferences.getString(valLocal.taikhoan_user, "");
        User user = new User(tai_khoan, "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(valLocal.urlLocal)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<User> call = retrofitAPI.getUserObject(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    SoTien = response.body().getSo_tien();
                    btn_Xacnhan.setEnabled(true);
                    btn_Xacnhan.setText("Xác nhận");
                } else {
                    Toast.makeText(GetMoney.this, "Network error, please restart app!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(GetMoney.this, "Lỗi kết nối mạng, vui lòng mở lại ứng dụng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(GetMoney.this, "Network error, please restart app!", Toast.LENGTH_SHORT).show();
                Toast.makeText(GetMoney.this, "Lỗi kết nối mạng, vui lòng mở lại ứng dụng!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
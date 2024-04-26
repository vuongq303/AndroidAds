package com.hq.projectads;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hq.projectads.model.User;
import com.hq.projectads.retrofit.RetrofitAPI;
import com.hq.projectads.utils.valLocal;
import com.hq.projectads.utils.dialogUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignIn extends AppCompatActivity {
    private TextInputEditText edt_taikhoan, edt_matkhau;
    private TextInputLayout layout_taikhoan, layout_matkhau;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        edt_taikhoan = findViewById(R.id.edt_input_taikhoan_dn);
        edt_matkhau = findViewById(R.id.edt_input_matkhau_dn);

        layout_taikhoan = findViewById(R.id.edt_layout_taikhoan_dn);
        layout_matkhau = findViewById(R.id.edt_layout_matkhau_dn);
        loading = findViewById(R.id.loading_dn);

        edt_taikhoan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                layout_taikhoan.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edt_matkhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                layout_matkhau.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button dangnhap = findViewById(R.id.btn_dangnhap_dn);
        dangnhap.setOnClickListener(e -> {
            String taikhoan = Objects.requireNonNull(edt_taikhoan.getText()).toString().trim();
            String matkhau = Objects.requireNonNull(edt_matkhau.getText()).toString().trim();

            if (taikhoan.equals("") || matkhau.equals("")) {
                if (taikhoan.equals("")) {
                    layout_taikhoan.setError("Empty!");
                }
                if (matkhau.equals("")) {
                    layout_matkhau.setError("Empty!");
                }
            } else {
                dangnhap.setEnabled(false);
                loading.setVisibility(View.VISIBLE);

                User user = new User(taikhoan, matkhau);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(valLocal.urlLocal)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
                Call<Integer> call = retrofitAPI.signIn(user);

                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        loading.setVisibility(View.INVISIBLE);
                        dangnhap.setEnabled(true);

                        if (response.body() == 1) {
                            SharedPreferences.Editor editor = getSharedPreferences(valLocal.containerLogin, Context.MODE_PRIVATE).edit();
                            editor.putString(valLocal.taikhoan_user, taikhoan);
                            editor.apply();

                            Intent intent = new Intent(SignIn.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            layout_taikhoan.setError("Incorrect!");
                            layout_matkhau.setError("Incorrect!");
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        dialogUtils.dialogErrorNetwork(SignIn.this).show();
                    }
                });
            }

        });

        Button dangki = findViewById(R.id.btn_dangki_dn);
        dangki.setOnClickListener(e -> {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        });
    }
}
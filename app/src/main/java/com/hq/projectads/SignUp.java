package com.hq.projectads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hq.projectads.model.User;
import com.hq.projectads.retrofit.RetrofitAPI;
import com.hq.projectads.retrofit.RetrofitFunc;
import com.hq.projectads.utils.dialogUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    private TextInputEditText edt_taikhoan, edt_matkhau, edt_re_matkhau;

    private TextInputLayout layout_taikhoan, layout_matkhau, layout_re_matkhau;

    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        edt_taikhoan = findViewById(R.id.edt_input_taikhoan_dk);
        edt_matkhau = findViewById(R.id.edt_input_matkhau_dk);
        edt_re_matkhau = findViewById(R.id.edt_input_rematkhau_dk);

        layout_taikhoan = findViewById(R.id.edt_layout_taikhoan_dk);
        layout_matkhau = findViewById(R.id.edt_layout_matkhau_dk);
        layout_re_matkhau = findViewById(R.id.edt_layout_rematkhau_dk);
        loading = findViewById(R.id.loading_dk);

        Button dangki = findViewById(R.id.btn_dangki_dk);
        TextView dang_nhap = findViewById(R.id.txt_dangnhap_dk);

        ImageView back = findViewById(R.id.btn_back_dk);
        back.setOnClickListener(e -> finish());

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
        edt_re_matkhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                layout_re_matkhau.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dang_nhap.setOnClickListener(e -> {
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
        });

        dangki.setOnClickListener(e -> {
            String taikhoan = Objects.requireNonNull(edt_taikhoan.getText()).toString();
            String matkhau = Objects.requireNonNull(edt_matkhau.getText()).toString();
            String re_matkhau = Objects.requireNonNull(edt_re_matkhau.getText()).toString();

            if ((taikhoan.equals("") || matkhau.equals("") || re_matkhau.equals(""))) {
                if (taikhoan.equals("")) {
                    layout_taikhoan.setError("Empty!");
                }
                if (matkhau.equals("")) {
                    layout_matkhau.setError("Empty!");
                }
                if (re_matkhau.equals("")) {
                    layout_re_matkhau.setError("Empty!");
                }
            } else if (!matkhau.equals(re_matkhau)) {
                layout_matkhau.setError("Not match!");
                layout_re_matkhau.setError("Not match!");
            } else {
                dangki.setEnabled(false);
                loading.setVisibility(View.VISIBLE);

                User user = new User(taikhoan, matkhau);
                RetrofitAPI retrofitAPI = RetrofitFunc.createRetrofit();
                Call<Integer> call = retrofitAPI.signUp(user);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        dangki.setEnabled(true);
                        loading.setVisibility(View.INVISIBLE);

                        layout_taikhoan.setError("");
                        layout_matkhau.setError("");
                        layout_re_matkhau.setError("");

                        if (response.body() == 1) {
                            Toast.makeText(SignUp.this, "Sign up complete!", Toast.LENGTH_SHORT).show();
                        } else {
                            layout_taikhoan.setError("Exist!");
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        dialogUtils.dialogErrorNetwork(SignUp.this).show();
                    }
                });
            }
        });
    }
}
package com.tokioschool.flujologin;

import static com.tokioschool.flujologin.Constants.KEY_DATA;
import static com.tokioschool.flujologin.Constants.USER;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.tokioschool.flujologin.databinding.ActivityLoginBinding;
import com.tokioschool.flujologin.domain.User;

public class LoginAtivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private User user;
    private static int REQUEST_CODE_OBJECT_ACTIVITY=100;

    private final ActivityResultLauncher<Intent> register=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode()== Activity.RESULT_OK){
            if(result.getData()!=null){
                user=result.getData().getParcelableExtra((KEY_DATA));
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user= (User) getIntent().getParcelableExtra(USER);
        listeners();
    }

    private void listeners() {
        binding.loginForgotLink.setOnClickListener(view -> Snackbar.make(binding.loginSnackbar, R.string.login_snackbar_text, BaseTransientBottomBar.LENGTH_SHORT).show());
        binding.loginAccountLink.setOnClickListener(view -> {
            //nuevo
            activityRegister();
            /*Intent registerIntent=new Intent(this,RegisterActivity.class);
            registerIntent.putExtra(USER,user);
            startActivity(registerIntent);*/
        });
        binding.loginInputLoginText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableButton();
            }
        });
        binding.loginInputPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableButton();
            }
        });
        binding.loginButtonLogin.setOnClickListener(view -> userLogin(user));
    }

    private void activityRegister() {
        register.launch(new Intent(this, RegisterActivity.class));
    }

    private void userLogin(User user) {
        if(user==null){
            Toast.makeText(this, R.string.login_no_register, Toast.LENGTH_SHORT).show();
        }else{
            if(binding.loginInputLoginText.getText().toString().equals(user.getUsername()) && binding.loginInputPasswordText.getText().toString().equals(user.getPassword())){
                Intent homeIntent=new Intent(LoginAtivity.this,HomeActivity.class);
                homeIntent.putExtra(USER,user);
                startActivity(homeIntent);
            }else{
                Toast.makeText(this, R.string.login_validation_fail, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void enableButton() {
        if(binding.loginInputLoginText.getText().length()>0 && binding.loginInputPasswordText.getText().length()>0)
            binding.loginButtonLogin.setEnabled(true);
        else
            binding.loginButtonLogin.setEnabled(false);
    }

}
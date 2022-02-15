package com.tokioschool.flujologin;

import static com.tokioschool.flujologin.Constants.USER;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.tokioschool.flujologin.databinding.ActivityHomeBinding;
import com.tokioschool.flujologin.domain.User;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            showUser();
        }catch (Exception e){
            Log.e("Adrian",getString(R.string.home_user_error));
        }
    }

    private void showUser() {
        User user = (User) getIntent().getParcelableExtra(USER);
        Log.e("Adrian","El usuario es "+ user.getUsername()+" y la contraseña es "+ user.getPassword());
    }
}
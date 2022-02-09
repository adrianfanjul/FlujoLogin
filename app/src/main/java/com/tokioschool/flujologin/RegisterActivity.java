package com.tokioschool.flujologin;

import static com.tokioschool.flujologin.constants.USER;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.tokioschool.flujologin.databinding.ActivityRegisterBinding;
import com.tokioschool.flujologin.domain.User;

public class RegisterActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ActivityRegisterBinding binding;
    private final ActivityResultLauncher<Intent> register=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode()== Activity.RESULT_OK){
            if(result.getData()!=null){
                //Aquí se recogeria la imagen resultante
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadSpinner();
        listeners();
    }

    private void listeners() {
        binding.registerTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(binding.registerTextName.getText().toString().length()>0){
                    if(binding.registerTextName.getText().toString().contains("@")||binding.registerTextName.getText().toString().contains("!")){
                        binding.registerTextNameLayout.setError(getString(R.string.register_text_error));
                    }else{
                        binding.registerTextNameLayout.setError(null);
                    }
                }
                enableButton();
            }
        });
        binding.registerTextSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(binding.registerTextSurname.getText().toString().length()>0){
                    if(binding.registerTextSurname.getText().toString().contains("@")||binding.registerTextSurname.getText().toString().contains("!")){
                        binding.registerTextSurnameLayout.setError(getString(R.string.register_text_error));
                    }else{
                        binding.registerTextSurnameLayout.setError(null);
                    }
                }
                enableButton();
            }
        });
        binding.registerSpinnerAges.setOnItemClickListener((adapterView, view, position, id) -> {
            if(position<3){
                binding.registerSpinnerAgesLayout.setError((getString(R.string.register_age_error)));
            }else{
                binding.registerSpinnerAgesLayout.setError(null);
            }
        });
        binding.registerBtnOk.setOnClickListener(view -> {
            User user =new User();
            user.setUsername(binding.registerTextName.getText().toString());
            user.setPassword(binding.registerTextSurname.getText().toString());
            Intent loginIntent=new Intent(this,LoginAtivity.class);
            loginIntent.putExtra(USER,user);
            startActivity(loginIntent);
        });
    }

    private void enableButton() {
        binding.registerBtnOk.setEnabled(false);
        if(binding.registerTextName.getText().toString().length()>0 && binding.registerTextSurname.getText().toString().length()>0){
            binding.registerBtnOk.setEnabled(true);
        }
    }

    private void loadSpinner() {
        ArrayAdapter<String> listAgesAdapter= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.listAges));
        binding.registerSpinnerAges.setAdapter(listAgesAdapter);
    }

    public void btn_camera_click(View view){
        register.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }
    public void link_condiciones_click(View view){
        Uri uri=Uri.parse("https://developers.google.com/ml-kit/terms");
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

}
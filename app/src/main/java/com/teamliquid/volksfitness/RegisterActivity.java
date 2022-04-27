package com.teamliquid.volksfitness;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamliquid.volksfitness.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();

        //raise error when email address not valid
        binding.textInputEmail.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    validateEmail();
                }
            }
        });

        //raise error when password not consistent
        binding.textInputRepeatPassword.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    validateRepeatPassword();
                }
            }
        });

        //register new account
        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        //back to login page
        binding.textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean validateEmail(){
        String email = binding.textInputEmail.getEditText().getText().toString();
        //match the email
        boolean isEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (isEmail){
            binding.textInputEmail.setErrorEnabled(false);
        }else{
            binding.textInputEmail.setError("Not a valid email address");
        }
        return isEmail;
    }

    private boolean validateRepeatPassword (){
        String password = binding.textInputPassword.getEditText().getText().toString();
        String repeatPassword = binding.textInputRepeatPassword.getEditText().getText().toString();
        boolean isEqual = password.equals(repeatPassword);
        if (isEqual){
            binding.textInputRepeatPassword.setErrorEnabled(false);
        }else{
            binding.textInputRepeatPassword.setError("Passwords not consistent");
        }
        return isEqual;
    }

    private void register(){
        if (validateEmail() && validateRepeatPassword()){
            String email = binding.textInputEmail.getEditText().getText().toString();
            String password = binding.textInputPassword.getEditText().getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("success", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(RegisterActivity.this, "Register Successful",
                                        Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Fail", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Register failed, please check your information",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}

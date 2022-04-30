package com.teamliquid.volksfitness;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

        binding.textInputPassword.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    validPassword();
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

    private boolean validPassword(){

        String regex = "^(?![A-Za-z]+$)(?![A-Z0-9]+$)(?![a-z0-9]+$)(?![a-z\\W]+$)(?![A-Z\\W]+$)(?![0-9\\W]+$)[a-zA-Z0-9\\W]{8,16}$";
        String password = binding.textInputPassword.getEditText().getText().toString();
        if (password.matches(regex)){
            binding.textInputPassword.setErrorEnabled(false);
            return true;
        }else {
            binding.textInputPassword.setError("Password not valid");
            showDiyToast(RegisterActivity.this,"Password should contain 3 of: \n" +
                    "UPPERCASE letter \n" +
                    "lowercase letter \n" +
                    "Special character \n" +
                    "Number",R.drawable.ic_baseline_cancel_24);
            return false;
        }

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
        if (validateEmail() && validateRepeatPassword() && validPassword()){
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

    private void showDiyToast(Context context, String content, int imageId){
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        View layout = View.inflate(context,R.layout.toast_layout,null);

        ImageView imageView = layout.findViewById(R.id.image_toast);
        imageView.setImageResource(imageId);

        TextView textView = layout.findViewById(R.id.text_toast);
        textView.setText(content);

        toast.setView(layout);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}

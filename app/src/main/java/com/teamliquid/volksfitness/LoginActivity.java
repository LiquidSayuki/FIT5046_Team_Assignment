package com.teamliquid.volksfitness;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.teamliquid.volksfitness.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        binding.textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startHomeActivity();
        }
    }

    private void login () {
        String email = binding.textInputEmail.getEditText().getText().toString();
        String password = binding.textInputPassword.getEditText().getText().toString();

        if (!email.isEmpty() && !password.isEmpty()){
            //Use email and password to login
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                showDiyToast(LoginActivity.this,"Login Successful",R.drawable.ic_baseline_check_circle_24);
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                startHomeActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                showDiyToast(LoginActivity.this,"Login Failed \n Please check email and password",R.drawable.ic_baseline_cancel_24);
                            }
                        }
                    });
        }else {
            showDiyToast(LoginActivity.this,"Email and Password can't be empty",R.drawable.ic_baseline_cancel_24);
        }

    }

    private void startHomeActivity(){
        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    private void startRegisterActivity(){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
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

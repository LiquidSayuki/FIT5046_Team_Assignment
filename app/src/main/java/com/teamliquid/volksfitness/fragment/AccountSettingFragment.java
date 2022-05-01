package com.teamliquid.volksfitness.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamliquid.volksfitness.R;
import com.teamliquid.volksfitness.databinding.FragmentAccountSettingBinding;
import com.teamliquid.volksfitness.pojo.User;

import java.util.HashMap;
import java.util.Map;

public class AccountSettingFragment extends Fragment {
    private FragmentAccountSettingBinding binding;

    public AccountSettingFragment (){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountSettingBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        // Put everything in the if user not null
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();
            //Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            // boolean emailVerified = user.isEmailVerified();

            // Firebase database
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://fit5046-a61f5-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference reference = database.getReference("User");

            // Set the username and email
            binding.textUsername.setText(name);
            binding.textEmail.setText(email);

            // Initialize the gender drop down menu
            String[] genderArray = getResources().getStringArray(R.array.gender);
            ArrayAdapter adapter = new ArrayAdapter(getContext(),R.layout.item_dropdown,genderArray);
            adapter.setDropDownViewResource(R.layout.item_dropdown);
            binding.autoCompleteGender.setAdapter(adapter);


            reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()){
                        DataSnapshot dataSnapshot= task.getResult();

                        // set the default selected gender by data in the firebase
                        // TODO: fix the bug about the default value. It would make the dropdown menu unusable
                        // binding.autoCompleteGender.setText(String.valueOf(dataSnapshot.child("gender").getValue()));

                        // set the default value in the status box by data in the firebase
                        binding.textInputHeight.setText(String.valueOf(dataSnapshot.child("height").getValue()));
                        binding.textInputWeight.setText(String.valueOf(dataSnapshot.child("weight").getValue()));
                        binding.textInputAge.setText(String.valueOf(dataSnapshot.child("age").getValue()));
                    }else {

                    }
                }
            });

            // submit data
            binding.buttonUserinfoSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // get value of gender box
                    String genderString =  binding.autoCompleteGender.getText().toString();
                    // get value of Status box
                    String heightString = binding.textInputHeight.getText().toString();
                    String weightString = binding.textInputWeight.getText().toString();
                    String ageString = binding.textInputAge.getText().toString();
                    // Not empty
                    if (!genderString.isEmpty() && !heightString.isEmpty() && !weightString.isEmpty() && !ageString.isEmpty()){
                        // create user object
                        User userObj = new User(uid,name,email,genderString,
                                Integer.parseInt(ageString),
                                Integer.parseInt(heightString),
                                Integer.parseInt(weightString));
                        Map<String,User> userMap = new HashMap<>();
                        userMap.put(uid,userObj);

                        reference.setValue(userMap);

                        showDiyToast(getContext(),"Change profile success",R.drawable.ic_baseline_check_circle_24);
                        // Fragment navigation template
                        Navigation.findNavController(view).navigate(R.id.nav_home_fragment);
                    }else {
                        showDiyToast(getContext(),"Your info can't be empty",R.drawable.ic_baseline_cancel_24);
                    }
                }
            });

            // discard data
            binding.buttonUserinfoCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.nav_home_fragment);
                }
            });

        }
        return view;
    }
    private void showDiyToast(Context context, String content, int imageId){
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
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

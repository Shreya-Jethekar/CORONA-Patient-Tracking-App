package com.example.p3.ui.Account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.p3.R;
import com.example.p3.User;
import com.example.p3.databinding.FragmentSlideshowBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;
    DatabaseReference reference;
    FirebaseUser user;
    String userID;
    EditText h_name,h_email,h_number,h_password;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

//        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//        final TextView textView = binding.textSlideshow;
        View root=inflater.inflate(R.layout.fragment_slideshow,container,false);
          final TextView textView=root.findViewById(R.id.text_slideshow);

        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("User");
        userID=user.getUid();
        h_name=root.findViewById(R.id.editText_home_name);
        h_number=root.findViewById(R.id.editText_home_number);
        h_email=root.findViewById(R.id.editText_home_email);
        h_password=root.findViewById(R.id.editText_home_password);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               User userprofile=snapshot.getValue(User.class);
               if(userprofile != null){
                   String  Name=userprofile.Name;
                   String Email = userprofile.Email;
                   String Number = userprofile.Number;
                   String Password=userprofile.Password;

                   h_name.setText(Name);
                   h_number.setText(Number);
                   h_email.setText(Email);
                   h_password.setText(Password);

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
}
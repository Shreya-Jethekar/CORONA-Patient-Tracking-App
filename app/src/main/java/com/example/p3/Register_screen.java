package com.example.p3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register_screen extends AppCompatActivity {
EditText name,number,email,password,repassword,captcha;
TextView captcha_show,textView_loginpage;
Button btn_register;
ProgressBar progressBar_register;
FirebaseAuth fire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        getSupportActionBar().hide();

        name=(EditText) findViewById(R.id.editText_register_name);
        number=(EditText) findViewById(R.id.editText_register_phone);
        email=(EditText) findViewById(R.id.editText_register_email);
        password=(EditText) findViewById(R.id.editText_register_password);
        repassword=(EditText) findViewById(R.id.editText_register_password2);
        captcha=(EditText) findViewById(R.id.editText_register_captcha);
        captcha_show=(TextView) findViewById(R.id.textView_register_captcha_generated);
        textView_loginpage=(TextView) findViewById(R.id.textView_register_to_Login);
        btn_register=(Button) findViewById(R.id.button_register_new_user);
        progressBar_register=(ProgressBar)findViewById(R.id.progressBar_register);
        fire=FirebaseAuth.getInstance();

        textView_loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    OpenLogin();
                }
            }
        });

        //Keep Signin  Code//
        if(fire.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),Home_Of_Login.class));
            finish();

        }



//----------------------------------------------------*************CAPTCHA CODE ******************---------------------------
            String a;
            // chose a Character random from this String
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";

            // create StringBuffer size of AlphaNumericString
            StringBuilder sb = new StringBuilder(6);

            for (int i = 0; i < 6; i++) {
                // generate a random number between
                // 0 to AlphaNumericString variable length
                int index
                        = (int)(AlphaNumericString.length()
                        * Math.random());

                // add Character one by one in end of sb
                sb.append(AlphaNumericString
                        .charAt(index));
            }
            a=sb.toString();
            captcha_show.setText(a);
//----------------------------------------------------*******************************---------------------------

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String NAME,NUMBER,EMAIL,PASSWORD,REPASSWORD,CAPTCHA,CAPTCHA_SHOW;
                NAME = name.getText().toString().trim();
                NUMBER=number.getText().toString().trim();
                EMAIL=email.getText().toString().trim();
                PASSWORD=password.getText().toString().trim();
                REPASSWORD=repassword.getText().toString().trim();
                CAPTCHA=captcha.getText().toString().trim();
                CAPTCHA_SHOW=captcha_show.getText().toString().trim();

                if(TextUtils.isEmpty(NAME))
                {
                  name.setError("Name is required");
                }
                if(TextUtils.isEmpty(NUMBER))
                {
                    number.setError("Number is required");
                }
                if(NUMBER.length()<10)
                {
                   number.setError("Should be 10");
                }
                if(TextUtils.isEmpty(EMAIL))
                {
                    email.setError("Email is required");
                }
                if(TextUtils.isEmpty(PASSWORD))
                {
                    password.setError("Password is required");
                }
                if(PASSWORD.length()<6)
                {
                     password.setError("password should be greater than 6 character");
                }
                if(TextUtils.isEmpty(REPASSWORD))
                {
                    repassword.setError("password should be greater than 6 character");
                }
                if(PASSWORD.equals(REPASSWORD))
                {

                }
                else{
                    repassword.setError("password should be same");
                }
           if(CAPTCHA.equals(CAPTCHA_SHOW))
           {

               progressBar_register.setVisibility(View.VISIBLE);

               fire.createUserWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                       if(task.isSuccessful())
                       {
                           User us=new User(NAME,NUMBER,EMAIL,PASSWORD);
                           FirebaseDatabase.getInstance().getReference("User")
                                   .child(FirebaseAuth.getInstance().getUid())
                                   .setValue(us).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {
                                   if(task.isSuccessful()) {
                                       Toast.makeText(Register_screen.this, "User Created Sucessfully", Toast.LENGTH_SHORT).show();
                                       Intent intent=new Intent(Register_screen.this,Home_Of_Login.class);
                                       startActivity(intent);

                                   }
                                   else
                                   {
                                       Toast.makeText(Register_screen.this,"ERROR",Toast.LENGTH_SHORT).show();
                                   }

                               }
                           });
                       }
                   }
               });
           }
           else{
               captcha_show.setError("Captcha Should be Same");
           }


            }
        });

    }

    private void OpenLogin() {

        Intent intent=new Intent(Register_screen.this, Login_screen.class);
        startActivity(intent);

    }


}


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

public class Login_screen extends AppCompatActivity {
    EditText l_name,l_email,l_password,l_captcha;
    TextView l_captcha_show,textView_registerpage;
    Button btn_login;
    ProgressBar progressBar_login;
    FirebaseAuth fire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        getSupportActionBar().hide();

        l_email=(EditText) findViewById(R.id.editText_login_email);
        l_password=(EditText) findViewById(R.id.editText_login_password);
       l_captcha=(EditText) findViewById(R.id.editText_login_captcha);
        l_captcha_show=(TextView) findViewById(R.id.textView_login_captcha_generated);
        textView_registerpage=(TextView) findViewById(R.id.textView_login_to_register);
        btn_login=(Button) findViewById(R.id.button_login_User);
        progressBar_login=(ProgressBar)findViewById(R.id.progressBar_login);
        fire = FirebaseAuth.getInstance();


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
        l_captcha_show.setText(a);
//----------------------------------------------------*******************************---------------------------


        textView_registerpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_RegisterPage();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 String EMAIL,PASSWORD,CAPTCHA_SHOW,CAPTCHA;
                EMAIL=l_email.getText().toString().trim();
                PASSWORD=l_password.getText().toString().trim();
                CAPTCHA_SHOW=l_captcha_show.getText().toString().trim();
                CAPTCHA=l_captcha.getText().toString().trim();


                if(TextUtils.isEmpty(EMAIL))
                {
                    l_email.setError("Email is required");
                }

                if(TextUtils.isEmpty(PASSWORD))
                {
                    l_password.setError("Email is required");
                }
                if(l_password.length()<6)
                {
                    l_password.setError("Password must be greater than 6 characters");
                }

                if(CAPTCHA.equals(CAPTCHA_SHOW))
                {
                    progressBar_login.setVisibility(View.VISIBLE);
///////////////////-------------Aunthicate the USER---------------------///////////////////////////////////
                    fire.signInWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(Login_screen.this,"Login is Succesfull",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(Login_screen.this,Home_Of_Login.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Login_screen.this,"Login is not Succesfull Or Try Again",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }

                else{
                    l_captcha.setError("CAPTCHA should be same");
                }

            }
        });
    }

    private void Open_RegisterPage() {
        Intent intent=new Intent(this,Register_screen.class);
        startActivity(intent);
    }
}
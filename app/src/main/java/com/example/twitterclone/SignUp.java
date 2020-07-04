package com.example.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements  View.OnClickListener {

    private EditText edtEmail,edtUsername,edtpassword;
    private Button btnSignUp,btnLogIn;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //to change title bar
        setTitle("Sign Up");

        edtEmail=findViewById(R.id.edtEnterEmail);
        edtUsername=findViewById(R.id.edtusername);
        edtpassword=findViewById(R.id.edtEnterPassword);

        edtpassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER  &&  event.getAction()==KeyEvent.ACTION_DOWN)
                {
                    onClick(btnSignUp);
                }
                return false;
            }
        });
        btnSignUp=findViewById(R.id.btnSignUp);
        btnLogIn=findViewById(R.id.btnLogIn);

        btnLogIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        //due to token pronblem
        if(ParseUser.getCurrentUser()!=null) {
            //  ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnSignUp:

                if(edtEmail.getText().toString().equals("") || edtUsername.getText().toString().equals("")||edtpassword.getText().toString().equals(""))
                {
                    FancyToast.makeText(SignUp.this,"Email,Username,Password fields are required", Toast.LENGTH_SHORT,FancyToast.INFO,false).show();
                }
                else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtEmail.getText().toString());
                    appUser.setPassword(edtpassword.getText().toString());
                    appUser.setUsername(edtUsername.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing Up" + edtUsername.getText().toString());
                    progressDialog.show();
                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this, appUser.getUsername() + "is signed Up", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                transitionToSocialMediaActivity();
                            }
                            else {
                                FancyToast.makeText(SignUp.this, "There is an error: " + e.getMessage(), Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            }
                            progressDialog.dismiss();
                        }

                    });
                }

                break;
            case R.id.btnLogIn:
                Intent intent=new Intent(SignUp.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }


    //Not to display keyboard if user taps on Empty area

    public void rootLayoutTapped(View v) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void transitionToSocialMediaActivity()
    {
       // Intent intent=new Intent(SignUp.this,SocialMediaActivity.class);
        //finish();
        //startActivity(intent);

    }
}
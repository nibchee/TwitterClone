package com.example.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText edtLoginEmail,edtLoginpassword;
    private Button btnSignUpLoginActivity,btnLogInActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");
        edtLoginEmail=findViewById(R.id.edtEnterEmailLoginActivity);
        edtLoginpassword=findViewById(R.id.edtEnterPasswordLoginActivity);
        btnLogInActivity=findViewById(R.id.btnLogInLoginActivity);
        btnSignUpLoginActivity=findViewById(R.id.btnSignUpLoginActivity);

        btnSignUpLoginActivity.setOnClickListener(this);
        btnLogInActivity.setOnClickListener(this);

        edtLoginpassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER  &&  event.getAction()==KeyEvent.ACTION_DOWN)
                {
                    onClick(btnLogInActivity);
                }
                return false;
            }
        });


        //edtLoginpassword.setOnKeyListener((View.OnKeyListener) this);

        if(ParseUser.getCurrentUser()!=null)
        {
            ParseUser.getCurrentUser().logOut();
        }


    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnLogInLoginActivity:
                ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginpassword.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e)
                            {
                                if(user != null && e==null)
                                {
                                    FancyToast.makeText(LoginActivity.this,user.getUsername()+" is Logged In", Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                    transitionToSocialMediaActivity();
                                }
                            }
                        });

                break;
            case R.id.btnSignUpLoginActivity:
                Intent intent =new Intent(LoginActivity.this,SignUp.class);
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
       // Intent intent=new Intent(LoginAvtivity.this,SocialMediaActivity.class);
       // finish();
        //startActivity(intent);

        //to finish the current activity

    }
}


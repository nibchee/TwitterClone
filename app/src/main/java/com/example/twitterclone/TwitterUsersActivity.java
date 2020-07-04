package com.example.twitterclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class TwitterUsersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private ListView listview;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_users);
        FancyToast.makeText(TwitterUsersActivity.this, "Welcome!!! " + ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT, FancyToast.INFO, false);


        listview=findViewById(R.id.listView);

        arrayList=new ArrayList();
        arrayAdapter=new ArrayAdapter(TwitterUsersActivity.this,android.R.layout.simple_list_item_checked,arrayList);
        listview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);


        ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e==null)
                {
                    if(users.size()>0)
                    {
                        for(ParseUser user:users)
                        {
                            arrayList.add(user.getUsername());
                        }
                        listview.setAdapter(arrayAdapter);

                        listview.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }




        public boolean onCreateOptionsMenu(Menu menu)
        {

            getMenuInflater().inflate(R.menu.my_menu,menu);

            return super.onCreateOptionsMenu(menu);
        }

    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId()) {
            case R.id.logoutUserItem:
                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        Intent intent = new Intent(TwitterUsersActivity.this, SignUp.class);
                        startActivity(intent);
                        finish();

                    }
                });
        }
        return  super.onOptionsItemSelected(item);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

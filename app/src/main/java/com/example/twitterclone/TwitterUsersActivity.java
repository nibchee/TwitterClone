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
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class TwitterUsersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private ListView listview;
    private ArrayList<String> tUsers;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_users);
        FancyToast.makeText(TwitterUsersActivity.this, "Welcome!!! " + ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT, FancyToast.INFO, false);


        listview=findViewById(R.id.listView);

        tUsers=new ArrayList();
        arrayAdapter=new ArrayAdapter(TwitterUsersActivity.this,android.R.layout.simple_list_item_checked,tUsers);
        listview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listview.setOnItemClickListener(this);


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
                            tUsers.add(user.getUsername());
                        }
                        listview.setAdapter(arrayAdapter);

                        for(String twitterUsers:tUsers)
                        {
                            if(ParseUser.getCurrentUser().getList("fanOf")!=null) {
                                if (ParseUser.getCurrentUser().getList("fanOf").contains(twitterUsers)) {
                                    listview.setItemChecked(tUsers.indexOf(twitterUsers), true);

                                }
                            }
                        }

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
                break;
            case R.id.sendTweetItem:
                Intent intent=new Intent(TwitterUsersActivity.this,SendTweetActivity.class);
                startActivity(intent);
        }
        return  super.onOptionsItemSelected(item);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        CheckedTextView checkedTextView=(CheckedTextView) view;

        if(checkedTextView.isChecked())
        {
            FancyToast.makeText(TwitterUsersActivity.this,tUsers.get(position)+ " is now followed ",Toast.LENGTH_SHORT,FancyToast.INFO,false).show();
            ParseUser.getCurrentUser().add("fanOf",tUsers.get(position));

        }else
        {
            FancyToast.makeText(TwitterUsersActivity.this,tUsers.get(position)+ " is not followed ",Toast.LENGTH_SHORT,FancyToast.INFO,false).show();

            //removing Fan
            ParseUser.getCurrentUser().getList("fanOf").remove(tUsers.get(position));
           List currentUserfanOfList=ParseUser.getCurrentUser().getList("fanOf");
           ParseUser.getCurrentUser().remove("fanOf");
           ParseUser.getCurrentUser().put("fanOf",currentUserfanOfList);
        }

        //after changes save in Background
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e)
            {
                if(e==null)
                {
                    FancyToast.makeText(TwitterUsersActivity.this,"Saved!!",Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                }

            }
        });

    }
}

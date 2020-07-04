package com.example.twitterclone;

import android.app.Application;
import android.os.Bundle;

import com.parse.Parse;



public class App extends Application {
    public void onCreate()
    {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("0xKDyCbQROdz4oNWQP9BdxY6EVzaPNLKjYjzeqdl")
                // if defined
                .clientKey("sTv8g5IdTYzt1TVqNKtUE64OmLyQqnm45wMH6eGO")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
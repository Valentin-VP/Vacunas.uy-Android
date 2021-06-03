package com.example.vacunasuy;

import android.app.Application;

public class CookieClass extends Application {

    private String cookie = null;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}

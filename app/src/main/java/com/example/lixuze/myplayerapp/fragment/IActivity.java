package com.example.lixuze.myplayerapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Shinelon on 2017/2/26.
 */

public interface IActivity {
    public void onScroll(int scrollhigh , int scrollY);
    public void addFragement(Fragment fromFragment , Fragment tofragment);
}

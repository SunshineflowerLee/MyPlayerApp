package com.example.lixuze.myplayerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lixuze.myplayerapp.R;
import com.example.lixuze.myplayerapp.Tool.Util;

/**
 * Created by lixuze on 16-12-20.
 */

public class ViewpagerMainFragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_main2,null);
        View statubar = view.findViewById(R.id.viewpager_main2_statubar);
        Util.setsatsubar(getActivity(),statubar);
        return view;
    }
}

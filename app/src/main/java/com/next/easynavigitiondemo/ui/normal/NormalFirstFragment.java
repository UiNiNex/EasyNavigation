package com.next.easynavigitiondemo.ui.normal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.next.easynavigitiondemo.R;
import com.next.easynavigitiondemo.ui.AddActivity;
import com.next.easynavigitiondemo.ui.NormalActivity;
import com.next.easynavigitiondemo.ui.add.AddSecondFragment;

/**
 * Created by Administrator on 2018/6/2.
 */

public class NormalFirstFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_normal_first, null);
        return view;
    }


}

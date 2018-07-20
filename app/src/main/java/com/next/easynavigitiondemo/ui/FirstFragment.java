package com.next.easynavigitiondemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.next.easynavigitiondemo.R;

/**
 * Created by Administrator on 2018/6/2.
 */

public class FirstFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, null);

        Button bt01 = view.findViewById(R.id.bt01);
        bt01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof AddActivity) {
                    ((AddActivity) getActivity()).getNavigitionBar().setMsgPointCount(2, 109);
                    ((AddActivity) getActivity()).getNavigitionBar().setMsgPointCount(0, 5);
                    ((AddActivity) getActivity()).getNavigitionBar().setHintPoint(3, true);
                } else if (getActivity() instanceof NoAddActivity) {
                    ((NoAddActivity) getActivity()).getNavigitionBar().setMsgPointCount(2, 109);
                    ((NoAddActivity) getActivity()).getNavigitionBar().setMsgPointCount(0, 5);
                    ((NoAddActivity) getActivity()).getNavigitionBar().setHintPoint(3, true);
                }
            }
        });


        Button bt02 = view.findViewById(R.id.bt02);
        bt02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof AddActivity) {
                    ((AddActivity) getActivity()).getNavigitionBar().clearAllMsgPoint();
                    ((AddActivity) getActivity()).getNavigitionBar().clearAllHintPoint();
                } else if (getActivity() instanceof NoAddActivity) {
                    ((NoAddActivity) getActivity()).getNavigitionBar().clearAllMsgPoint();
                    ((NoAddActivity) getActivity()).getNavigitionBar().clearAllHintPoint();
                }

            }
        });


        return view;
    }


}

package example.com.android_mvc.controller.month_controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.android_mvc.R;

/**
 * Created by ChenSiyuan on 2018/12/4.
 */

public class Fragment_01 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.month_layout_01,container,false);

    }

}

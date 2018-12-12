package example.com.android_mvc.Viewer;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import example.com.android_mvc.R;

/**
 * Created by ChenSiyuan on 2018/12/2.
 */

public class Main_Viewer extends AppCompatActivity {



    public static int style_num;
    private LinearLayout total_layout;
    private LinearLayout top_layout;
    private ViewPager middle_layout;
    private LinearLayout bottom_layout;
    private NavigationView side_layout;
    private ViewPager viewPager;

    private Button sync;

    public Main_Viewer(LinearLayout lay_1, LinearLayout lay_2, ViewPager lay_3, LinearLayout lay_4, NavigationView lay_5, Button b1){

        total_layout=lay_1;
        top_layout=lay_2;
        middle_layout=lay_3;
        bottom_layout=lay_4;
        side_layout=lay_5;
        sync=b1;

    }


    public void setBlueStyle(Context context){
        total_layout.setBackgroundResource(R.color.blue_back);
        top_layout.setBackgroundResource(R.drawable.topregion_blue_1080p);
        middle_layout.setBackgroundResource(R.drawable.middleregion_blue_1080p);
        bottom_layout.setBackgroundResource(R.drawable.bottomregion_blue_1080p);
        side_layout.removeHeaderView(side_layout.getHeaderView(0));
        side_layout.inflateHeaderView(R.layout.header_blu);
        sync.setBackgroundResource(R.drawable.sync_back_blu);
        style_num=1;




    }
    public void setOrangeStyle(Context context){
        total_layout.setBackgroundResource(R.color.orange_back);
        top_layout.setBackgroundResource(R.drawable.topregion_org_1080p);
        middle_layout.setBackgroundResource(R.drawable.middleregion_org_1080p);
        bottom_layout.setBackgroundResource(R.drawable.bottomregion_org_1080p);
        side_layout.removeHeaderView(side_layout.getHeaderView(0));
        side_layout.inflateHeaderView(R.layout.nav_header_main);
        sync.setBackgroundResource(R.drawable.sync_back);
        style_num=0;

    }

    public static int getStyle_num(){return style_num;}




}

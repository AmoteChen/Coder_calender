package example.com.android_mvc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import example.com.android_mvc.Viewer.Main_Viewer;
import example.com.android_mvc.Model.RecordDAO;
import example.com.android_mvc.controller.mall_controller.Mall_activity;
import example.com.android_mvc.controller.month_controller.FragmentAdapter;
import example.com.android_mvc.controller.month_controller.Fragment_01;
import example.com.android_mvc.controller.month_controller.Fragment_11;
import example.com.android_mvc.controller.month_controller.Fragment_12;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private UIChangeReceiver mainreceiver;

    private ViewPager viewPager;
    private Fragment_12 Dec_fragment;
    private Fragment_01 Jan_fragment;
    private Fragment_11 Nov_fragment;

    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private FragmentAdapter fragmentAdapter;

    private LinearLayout total_layout;
    private LinearLayout top_layout;
    private ViewPager middle_layout;
    private LinearLayout bottom_layout;
    private NavigationView side_layout_1;

    private static Button sync;
    private static ProgressBar progressBar;
    private static EditText target_text;
    private static TextView finish_text;
    private TextView last_time;

    private Main_Viewer mainViewer;
    private int style_count=0;

    public RecordDAO dao;

    private static int today;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inite();
        fragment_manage();
        mainreceiver = new UIChangeReceiver(new Handler());
        registerReceiver(mainreceiver,new IntentFilter("com.example.broadcast.UI_CHANGE") );
//          simulation();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mall_bottom) {
            Intent tomall = new Intent (MainActivity.this, Mall_activity.class);
            startActivity(tomall);



        } else if (id == R.id.style_bottom) {
            if(style_count>1){style_count=0;}
            if(style_count==0){
                mainViewer.setBlueStyle(this);
                fragment_init();
                fragment_manage();
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_blu_drawable));
                style_count++;
            }
            else {
                mainViewer.setOrangeStyle(this);
                fragment_init();
                fragment_manage();
                style_count++;
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_org_drawable));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void inite(){
        dao=new RecordDAO(this);

        Calendar calendar= Calendar.getInstance();
        String today_st = String.valueOf(calendar.get(Calendar.YEAR))+String.valueOf(calendar.get(Calendar.MONTH)+1)+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        today = Integer.parseInt(today_st);


        fragment_init();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        total_layout = (LinearLayout)findViewById(R.id.total_layout);
        top_layout = (LinearLayout)findViewById(R.id.top_layout);
        middle_layout = (ViewPager)findViewById(R.id.middle_layout);
        bottom_layout = (LinearLayout)findViewById(R.id.bottom_layout);
        side_layout_1 = (NavigationView) findViewById(R.id.nav_view);

        side_layout_1.inflateHeaderView(R.layout.nav_header_main);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        sync=(Button)findViewById(R.id.button_sync);
        sync.setVisibility(View.GONE);
        sync.setOnClickListener(this);

        target_text=(EditText)findViewById(R.id.target_text);
        finish_text=(TextView)findViewById(R.id.finish_text);
        last_time=(TextView)findViewById(R.id.last_time);

        mainViewer =new Main_Viewer(total_layout,top_layout,middle_layout,bottom_layout,side_layout_1,sync);
        setSupportActionBar(toolbar);

        View decorView5 = getWindow().getDecorView();
        int uiOptions5 = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView5.setSystemUiVisibility(uiOptions5);
        getSupportActionBar().hide();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void fragment_manage(){
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(),fragmentList);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private View.OnFocusChangeListener mOnFC = new View.OnFocusChangeListener(){
        @Override
        public void onFocusChange(View view,boolean hasFocus){

            EditText textView = (EditText) view;
            textView.setEllipsize(TextUtils.TruncateAt.END);
            String hint;
            if (hasFocus) {
                hint = textView.getHint().toString();
                textView.setTag(hint);
                textView.setHint("");
            } else {
                hint = textView.getTag().toString();
                textView.setHint(hint);
            }
        }
    };

    private void fragment_init(){
        fragmentList.clear();
        viewPager=(ViewPager) findViewById(R.id.middle_layout);
        Dec_fragment = new Fragment_12();
        Nov_fragment = new Fragment_11();
        Jan_fragment = new Fragment_01();

        fragmentList.add(Nov_fragment);
        fragmentList.add(Dec_fragment);
        fragmentList.add(Jan_fragment);
    }
    private void simulation(){

        Random random = new Random();
        for (int i = 20181210;i<=20181212;i++){
            dao.insert(i,random.nextInt(9000)%(9000-1000+1)+1000,random.nextInt(9000)%(9000-1000+1)+1000);
        }
//        for (int i = 20181028;i<=20181031;i++){
//            dao.insert(i,random.nextInt(9000)%(9000-1000+1)+1000,random.nextInt(9000)%(9000-1000+1)+1000);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_sync:
                Random random = new Random();

                int list[]=dao.getRecordByDay(today);
                if(list[0]==0&&list[1]==0){
                    dao.insert(today,Integer.parseInt(finish_text.getText().toString()),Integer.parseInt(target_text.getText().toString()));
                    progressBar.setProgress(0);
                    Toast.makeText(MainActivity.this,"Insert success!",Toast.LENGTH_SHORT).show();
                }
                else {
                    int current = Integer.parseInt(finish_text.getText().toString())+random.nextInt(415)%(415-102+1)+102;
                    int target = Integer.parseInt(target_text.getText().toString());
                    finish_text.setText(String.valueOf(current));
                    dao.update(today,current,target);

                    double temp=100*((float)current/(float)target);
                    int progress= (int)temp;
                    progressBar.setProgress(progress);

                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);
                    last_time.setText(hour+":"+minute+":"+second);
                    Toast.makeText(MainActivity.this,"Update success!",Toast.LENGTH_SHORT).show();
                }
        }
    }

    public static class UIChangeReceiver extends BroadcastReceiver {

        private final Handler handler; // Handler used to execute code on the UI thread
        public UIChangeReceiver(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onReceive(final Context context, final Intent intent) {
            // Post the UI updating code to our Handler
            handler.post(new Runnable() {
                @Override
                public void run() {
                    target_text.setText(Integer.toString(intent.getIntExtra("target",0)));
                    finish_text.setText(Integer.toString(intent.getIntExtra("current",0)));
                    if (intent.getIntExtra("date",0)==today){
                        sync.setVisibility(View.VISIBLE);
                    }
                    else {
                        sync.setVisibility(View.GONE);
                        progressBar.setProgress(0);

                    }
//                    Toast.makeText(context, "Toast from broadcast receiver", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}

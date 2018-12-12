package example.com.android_mvc.controller.day_controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import example.com.android_mvc.Model.RecordDAO;
import example.com.android_mvc.R;
import example.com.android_mvc.Viewer.Main_Viewer;

/**
 * Created by ChenSiyuan on 2018/11/26.
 */

public class day_block extends LinearLayout{
    public RecordDAO dao;

    private View layout_main;
    private Button button;
    private EditText target_text;
    private TextView finish_text;
    private LinearLayout top_layout;
    private Button sync;
    private TypedArray typedArray;

    private String month_date;
    private String month;

    private int day_index;
    private Context context;


    public day_block(Context context){
        this(context,null);
    }
    public day_block(Context context,@Nullable AttributeSet attrs){
        this(context,attrs,-1);
    }
    @SuppressLint("ResourceType")
    public day_block(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);

        this.context=context;
        LayoutInflater main = LayoutInflater.from(context);
        layout_main = main.inflate(R.layout.content_main,null);


        Calendar calendar = Calendar.getInstance();
        LayoutInflater.from(getContext()).inflate(R.layout.day_block,this);
        typedArray=context.obtainStyledAttributes(attrs,R.styleable.day_block);

        init();


        button.setTextColor(typedArray.getColor(R.styleable.day_block_text_color, Color.parseColor("#000000")));
        button.setText(typedArray.getString(R.styleable.day_block_month_date),null);


        month_date = typedArray.getString(R.styleable.day_block_month_date);
        month = typedArray.getString(R.styleable.day_block_month);
        if (calendar.get(Calendar.YEAR)==2018){
            day_index=Integer.parseInt("2018"+month+month_date);
        }
        if(month.equals("1")){
            day_index=Integer.parseInt("2019"+month+month_date);
        }

        dao = new RecordDAO(context);
        int[]temp = dao.getRecordByDay(day_index);
        if(temp[1]!=0){
            if(dao.getRankByDay(day_index)==0&& Main_Viewer.getStyle_num()==0){
            button.setBackgroundResource(typedArray.getResourceId(R.styleable.day_block_back_color, R.drawable.daybutton_org1_1080p));
            }
            else if (dao.getRankByDay(day_index)==1&& Main_Viewer.getStyle_num()==0){
            button.setBackgroundResource(typedArray.getResourceId(R.styleable.day_block_back_color, R.drawable.daybutton_org2_1080p));
            }
            else if (dao.getRankByDay(day_index)==2&& Main_Viewer.getStyle_num()==0){
            button.setBackgroundResource(typedArray.getResourceId(R.styleable.day_block_back_color, R.drawable.daybutton_org3_1080p));
            }
            else if (dao.getRankByDay(day_index)==3&& Main_Viewer.getStyle_num()==0){
            button.setBackgroundResource(typedArray.getResourceId(R.styleable.day_block_back_color, R.drawable.daybutton_org4_1080p));
            }
            else if (dao.getRankByDay(day_index)==4&& Main_Viewer.getStyle_num()==0){
            button.setBackgroundResource(typedArray.getResourceId(R.styleable.day_block_back_color, R.drawable.daybutton_org5_1080p));
            }

            if(dao.getRankByDay(day_index)==0&& Main_Viewer.getStyle_num()==1){
            button.setBackgroundResource(typedArray.getResourceId(R.styleable.day_block_back_color, R.drawable.daybutton1_blue_1080p));
            }
            else if (dao.getRankByDay(day_index)==1&& Main_Viewer.getStyle_num()==1){
            button.setBackgroundResource(typedArray.getResourceId(R.styleable.day_block_back_color, R.drawable.daybutton2_blue_1080p));
            }
            else if (dao.getRankByDay(day_index)==2&& Main_Viewer.getStyle_num()==1){
            button.setBackgroundResource(typedArray.getResourceId(R.styleable.day_block_back_color, R.drawable.daybutton3_blue_1080p));
            }
            else if (dao.getRankByDay(day_index)==3&& Main_Viewer.getStyle_num()==1){
            button.setBackgroundResource(typedArray.getResourceId(R.styleable.day_block_back_color, R.drawable.daybutton4_blue_1080p));
            }
            else if (dao.getRankByDay(day_index)==4&& Main_Viewer.getStyle_num()==1){
            button.setBackgroundResource(typedArray.getResourceId(R.styleable.day_block_back_color, R.drawable.daybutton5_blue_1080p));
            }
        }
        else {
            button.setBackgroundResource(typedArray.getResourceId(R.styleable.day_block_back_color, R.drawable.daybutton_org1_1080p));
        }


        setListener();



    }
    private void init(){
        button=findViewById(R.id.day_back);
    }

    private void setListener(){

            button.setOnClickListener(new QueryListener(context));
    }


    class InsertListener extends AppCompatActivity implements OnClickListener{
        private Context context;
        public InsertListener(Context context){
            this.context=context;
        }
        @Override
        public void onClick(View v) {
        }
    }
    class QueryListener extends AppCompatActivity implements OnClickListener  {
        private Context context;
        public QueryListener(Context context){
            this.context=context;
        }
        @Override
        public void onClick(View v) {
            int date = day_index;

            int[] list = dao.getRecordByDay(date);

            if(list == null){}
            else {
                Intent QueryIntent= new Intent("com.example.broadcast.UI_CHANGE");
                QueryIntent.putExtra("date",day_index);
                QueryIntent.putExtra("current",list[0]);
                QueryIntent.putExtra("target",list[1]);
                context.sendBroadcast(QueryIntent);
            }
        }

    }
    class DeleteListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            int date = day_index;
            dao.delete(date);
        }
    }




}

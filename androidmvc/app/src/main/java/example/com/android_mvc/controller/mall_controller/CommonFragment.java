package example.com.android_mvc.controller.mall_controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import example.com.android_mvc.R;

/**
 * Created by ChenSiyuan on 2018/12/5.
 */

public class CommonFragment extends Fragment {
    private ImageView imageView;
    private String imageUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mall_fragment, null);
        imageView = (ImageView) rootView.findViewById(R.id.image);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);

        // 添加点击事件，暂定为打印信息
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(v.getContext(), "you clicked image", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }


    public void bindData(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

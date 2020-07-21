package com.example.xmum_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.xmum_app.city_manager.CityManagerActivity;
import com.example.xmum_app.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class WeatherMainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView addCityIv,moreIv;
    LinearLayout pointLayout;
    RelativeLayout outLayout;
    ViewPager mainVp;
    //    data source of ViewPager
    List<Fragment>fragmentList;
    //    the set of showed city
    List<String>cityList;
    //    showed page indicator set of ViewPager
    List<ImageView>imgList;
    private CityFragmentPagerAdapter adapter;
    private SharedPreferences pref;
    private int bgNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main);
        addCityIv = findViewById(R.id.main_iv_add);
        moreIv = findViewById(R.id.main_iv_more);
        pointLayout = findViewById(R.id.main_layout_point);
        outLayout = findViewById(R.id.main_out_layout);
        mainVp = findViewById(R.id.main_vp);

        addCityIv.setOnClickListener(this);
        moreIv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherMainActivity.this,Homepage.class);
                startActivity(intent);
                finish();
            }
        });

        fragmentList = new ArrayList<>();
        cityList = DBManager.queryAllCityName();  //gain the list of weather info in database
        imgList = new ArrayList<>();

        if (cityList.size()==0) {
            cityList.add("Beijing");
        }
        /* search page may jump into this page with passing value*/
        try {
            Intent intent = getIntent();
            String city = intent.getStringExtra("city");
            if (!cityList.contains(city)&&!TextUtils.isEmpty(city)) {
                cityList.add(city);
            }
        }catch (Exception e){
            Log.i("animee","Program has error！！");
        }
//        Initialize page of ViewPager
        initPager();
        adapter = new CityFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mainVp.setAdapter(adapter);
//        create point indicator
        initPoint();
//        set the info of last city
        mainVp.setCurrentItem(fragmentList.size()-1);
//        set the listener of ViewPager
        setPagerListener();
    }


    private void setPagerListener() {
        /* set on listener*/
        mainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imgList.size(); i++) {
                    imgList.get(i).setImageResource(R.mipmap.a1);
                }
                imgList.get(position).setImageResource(R.mipmap.a2);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initPoint() {
//        create point and the function of ViewPager indicator
        for (int i = 0; i < fragmentList.size(); i++) {
            ImageView pIv = new ImageView(this);
            pIv.setImageResource(R.mipmap.a1);
            pIv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) pIv.getLayoutParams();
            lp.setMargins(0,0,20,0);
            imgList.add(pIv);
            pointLayout.addView(pIv);
        }
        imgList.get(imgList.size()-1).setImageResource(R.mipmap.a2);


    }

    private void initPager() {
        /* Create Fragment，add to the data source of ViewPager*/
        for (int i = 0; i < cityList.size(); i++) {
            CityWeatherFragment cwFrag = new CityWeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city",cityList.get(i));
            cwFrag.setArguments(bundle);
            fragmentList.add(cwFrag);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.main_iv_add:
                intent.setClass(this,CityManagerActivity.class);
                break;
            case R.id.main_iv_more:

                break;
        }
        startActivity(intent);
    }

    /* call this function when page reloading, before page gains point,finish the update of pages in ViewPager*/
    @Override
    protected void onRestart() {
        super.onRestart();
//        gain the rest city set in database
        List<String> list = DBManager.queryAllCityName();
        if (list.size()==0) {
            list.add("Beijing");
        }
        cityList.clear();    //clear data source before overloading
        cityList.addAll(list);
//        create fragment page for rest city
        fragmentList.clear();
        initPager();
        adapter.notifyDataSetChanged();
//        when the number of page change, the number of indicator also change, override setting to add indicator
        imgList.clear();
        pointLayout.removeAllViews();   //clear all the element in layout
        initPoint();
        mainVp.setCurrentItem(fragmentList.size()-1);
    }
}

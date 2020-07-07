package com.example.xmum_app.city_manager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xmum_app.R;
import com.example.xmum_app.WeatherMainActivity;
import com.example.xmum_app.base.BaseActivity;
import com.example.xmum_app.bean.WeatherBean;
import com.google.gson.Gson;

public class SearchCityActivity extends BaseActivity implements View.OnClickListener{
    EditText searchEt;
    ImageView submitIv;
    GridView searchGv;
    String[]hotCitys = {"Beijing","Shanghai","Guangzhou","Shenzhen","Zhuhai","Foshan","Nanjing","Suzhou","Xiamen","Changsha","Chengdu","Fuzhou",
            "hangzhou","Wuhan","Qingdao","Xian","Taiyuan","Shenyang","Chongqing","Tianjin","Nanning"};
    private ArrayAdapter<String> adapter;
    String url1 = "http://api.map.baidu.com/telematics/v3/weather?location=";
    String url2 = "&output=json&ak=FkPhtMBK0HTIQNh7gG4cNUttSTyr0nzo";
    String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        searchEt = findViewById(R.id.search_et);
        submitIv = findViewById(R.id.search_iv_submit);
        searchGv = findViewById(R.id.search_gv);
        submitIv.setOnClickListener(this);
//      adapter
        adapter = new ArrayAdapter<>(this, R.layout.item_hotcity, hotCitys);
        searchGv.setAdapter(adapter);
        setListener();
    }
    //set listener
    private void setListener() {
        searchGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = hotCitys[position];
                String url = url1+city+url2;
                loadData(url);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_iv_submit:
                city = searchEt.getText().toString();
                if (!TextUtils.isEmpty(city)) {
//                      to check if can find such city
                    String url = url1+city+url2;
                    loadData(url);
                }else {
                    Toast.makeText(this,"Empty input is not allow",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onSuccess(String result) {
        WeatherBean weatherBean = new Gson().fromJson(result, WeatherBean.class);
        if (weatherBean.getError()==0) {
            Intent intent = new Intent(this, WeatherMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("city",city);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Sorry, we don't have weather information of this city",Toast.LENGTH_SHORT).show();
        }
    }
}

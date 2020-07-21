package com.example.xmum_app;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.xmum_app.base.BaseFragment;
import com.example.xmum_app.bean.WeatherBean;
import com.example.xmum_app.db.DBManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class CityWeatherFragment extends BaseFragment implements View.OnClickListener{
    TextView tempTv,cityTv,conditionTv,windTv,tempRangeTv,dateTv,clothIndexTv,carIndexTv,coldIndexTv,sportIndexTv,raysIndexTv;
    ImageView dayIv;
    LinearLayout futureLayout;
    String url1 = "http://api.map.baidu.com/telematics/v3/weather?location=";
    String url2 = "&output=json&ak=FkPhtMBK0HTIQNh7gG4cNUttSTyr0nzo";
    private List<WeatherBean.ResultsBean.IndexBean> indexList;
    String city;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_weather, container, false);
        initView(view);
//        gain the city of which current fragment is loading, by activity
        Bundle bundle = getArguments();
        city = bundle.getString("city");
        String url = url1+city+url2;
//      call the way of gaining data of parent class
        loadData(url);
        return view;
    }

    @Override
    public void onSuccess(String result) {
//        parse and show data
        parseShowData(result);
//         update data
        int i = DBManager.updateInfoByCity(city, result);
        if (i<=0) {
//            update data failed,indicate no such city info, add this city
            DBManager.addCityInfo(city,result);
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
//        in database, query city info last time, will show in fragment
        String s = DBManager.queryInfoByCity(city);
        if (!TextUtils.isEmpty(s)) {
            parseShowData(s);
        }
    }

    private void parseShowData(String result) {
//        gson parses data
        WeatherBean weatherBean = new Gson().fromJson(result, WeatherBean.class);
        WeatherBean.ResultsBean resultsBean = weatherBean.getResults().get(0);
//        the list of gained index info set
        indexList = resultsBean.getIndex();
//        set TextView
        dateTv.setText(weatherBean.getDate());
        cityTv.setText(resultsBean.getCurrentCity());
//        gain the weather info today
        WeatherBean.ResultsBean.WeatherDataBean todayDataBean = resultsBean.getWeather_data().get(0);
        windTv.setText(todayDataBean.getWind());
        tempRangeTv.setText(todayDataBean.getTemperature());
        conditionTv.setText(todayDataBean.getWeather());
//     gain real time weather info with executing string required
        String[] split = todayDataBean.getDate().split("：");
        String todayTemp = split[1].replace(")", "");
        tempTv.setText(todayTemp);
//        the imagine of showed relative weather info
        Picasso.with(getActivity()).load(todayDataBean.getDayPictureUrl()).into(dayIv);
//        gain the weather info in 3 days and load into layout
        List<WeatherBean.ResultsBean.WeatherDataBean> futureList = resultsBean.getWeather_data();
        futureList.remove(0);
        for (int i = 0; i < futureList.size(); i++) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_main_center, null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            futureLayout.addView(itemView);
            TextView idateTv = itemView.findViewById(R.id.item_center_tv_date);
            TextView iconTv = itemView.findViewById(R.id.item_center_tv_con);
            TextView itemprangeTv = itemView.findViewById(R.id.item_center_tv_temp);
            ImageView iIv = itemView.findViewById(R.id.item_center_iv);
//          gain the weather info of relative location
            WeatherBean.ResultsBean.WeatherDataBean dataBean = futureList.get(i);
            idateTv.setText(dataBean.getDate());
            iconTv.setText(dataBean.getWeather());
            itemprangeTv.setText(dataBean.getTemperature());
            Picasso.with(getActivity()).load(dataBean.getDayPictureUrl()).into(iIv);
        }
    }

    private void initView(View view) {
//        initialize control
        tempTv = view.findViewById(R.id.frag_tv_currenttemp);
        cityTv = view.findViewById(R.id.frag_tv_city);
        conditionTv = view.findViewById(R.id.frag_tv_condition);
        windTv = view.findViewById(R.id.frag_tv_wind);
        tempRangeTv = view.findViewById(R.id.frag_tv_temprange);
        dateTv = view.findViewById(R.id.frag_tv_date);
        clothIndexTv = view.findViewById(R.id.frag_index_tv_dress);
        carIndexTv = view.findViewById(R.id.frag_index_tv_washcar);
        coldIndexTv = view.findViewById(R.id.frag_index_tv_cold);
        sportIndexTv = view.findViewById(R.id.frag_index_tv_sport);
        raysIndexTv = view.findViewById(R.id.frag_index_tv_rays);
        dayIv = view.findViewById(R.id.frag_iv_today);
        futureLayout = view.findViewById(R.id.frag_center_layout);
//        set on clock listener
        clothIndexTv.setOnClickListener(this);
        carIndexTv.setOnClickListener(this);
        coldIndexTv.setOnClickListener(this);
        sportIndexTv.setOnClickListener(this);
        raysIndexTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (v.getId()) {
            case R.id.frag_index_tv_dress:
                builder.setTitle("Clad index");
                WeatherBean.ResultsBean.IndexBean indexBean = indexList.get(0);
                String msg = indexBean.getZs()+"\n"+indexBean.getDes();
                builder.setMessage(msg);
                builder.setPositiveButton("OK",null);
                break;
            case R.id.frag_index_tv_washcar:
                builder.setTitle("Carwash index");
                indexBean = indexList.get(1);
                msg = indexBean.getZs()+"\n"+indexBean.getDes();
                builder.setMessage(msg);
                builder.setPositiveButton("OK",null);
                break;
            case R.id.frag_index_tv_cold:
                builder.setTitle("Cold index");
                indexBean = indexList.get(2);
                msg = indexBean.getZs()+"\n"+indexBean.getDes();
                builder.setMessage(msg);
                builder.setPositiveButton("OK",null);
                break;
            case R.id.frag_index_tv_sport:
                builder.setTitle("Sport index");
                indexBean = indexList.get(3);
                msg = indexBean.getZs()+"\n"+indexBean.getDes();
                builder.setMessage(msg);
                builder.setPositiveButton("OK",null);
                break;
            case R.id.frag_index_tv_rays:
                builder.setTitle("UV index");
                indexBean = indexList.get(4);
                msg = indexBean.getZs()+"\n"+indexBean.getDes();
                builder.setMessage(msg);
                builder.setPositiveButton("OK",null);
                break;
        }
        builder.create().show();
    }
}
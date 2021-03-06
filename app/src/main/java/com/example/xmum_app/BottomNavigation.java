package com.example.xmum_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        initView();
        replaceFragment(new Fragment1()); //启动时用HomeFragment替换掉空Fragment
    }
    public void initView() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);//设置导航栏监听器
        bottomNavigationView.setSelectedItemId(R.id.tab_one);//设置默认选择的导航栏子项tab_one即首页
        bottomNavigationView.setItemIconTintList(null);//取消导航栏子项图片的颜色覆盖
    }
    @Override    //处理导航栏子项的点击事件
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        int itemId = menuItem.getItemId();//获取点击的位置以及对应的id
        switch (itemId) {
            case R.id.tab_one:
                replaceFragment(new Fragment1());//id为tab_one则第一项被点击，遂用HomeFragment替换空Fragment
                menuItem.setChecked(true);
                break;
            case R.id.tab_two:
                replaceFragment(new Fragment2());
                menuItem.setChecked(true);
                break;
            case R.id.tab_three:
                replaceFragment(new Fragment3());
                menuItem.setChecked(true);
                break;
        }
        return false;
    } //替换Fragment的方法
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_empty, fragment);
        transaction.commit();
    }
}
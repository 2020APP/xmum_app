package com.example.xmum_app.city_manager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xmum_app.R;
import com.example.xmum_app.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class DeleteCityActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView errorIv,rightIv;
    ListView deleteLv;
    List<String> mDatas;   //data source of listView
    List<String>deleteCitys;  //list of deleted city info
    private DeleteCityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_city);
        errorIv = findViewById(R.id.delete_iv_error);
        rightIv = findViewById(R.id.delete_iv_right);
        deleteLv = findViewById(R.id.delete_lv);
        mDatas = DBManager.queryAllCityName();
        deleteCitys = new ArrayList<>();
//        set on click listener
        errorIv.setOnClickListener(this);
        rightIv.setOnClickListener(this);
//        adapter
        adapter = new DeleteCityAdapter(this, mDatas, deleteCitys);
        deleteLv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_iv_error:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("tip").setMessage("Sure to Discard your modify?")
                        .setPositiveButton("Discard Modify", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();   //close current activity
                            }
                        });
                builder.setNegativeButton("Cancel",null);
                builder.create().show();
                break;
            case R.id.delete_iv_right:
                for (int i = 0; i < deleteCitys.size(); i++) {
                    String city = deleteCitys.get(i);
//                    call function to delete city
                    int i1 = DBManager.deleteInfoByCity(city);
                }
//                back to former page after delete
                finish();
                break;
        }
    }
}

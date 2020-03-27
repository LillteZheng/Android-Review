package com.zhengsr.androiddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.zhengsr.androiddemo.activity.NotificationActivity;
import com.zhengsr.androiddemo.adapter.LGAdapter;
import com.zhengsr.androiddemo.adapter.LGViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listview = findViewById(R.id.listview);
        final List<DemoBean> datas = new ArrayList<>();


        datas.add(new DemoBean("通知栏", NotificationActivity.class));


        listview.setAdapter(new LGAdapter<DemoBean>(datas,R.layout.demo_layout) {
            @Override
            public void convert(LGViewHolder viewHolder, DemoBean data, int position) {
                viewHolder.setText(R.id.button,data.msg);
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DemoBean bean = datas.get(position);
                Intent intent = new Intent(MainActivity.this,bean.targetClass);
                startActivity(intent);
            }
        });


    }

    class DemoBean{
        String msg;
        Class<?> targetClass;

        public DemoBean() {
        }

        public DemoBean(String msg, Class<?> targetClass) {
            this.msg = msg;
            this.targetClass = targetClass;
        }
    }
}

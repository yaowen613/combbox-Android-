package com.yaowen.combbox;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Combbox combbox;
    private TextView wendu,shidu,fengxiang,zhuangtai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        combbox = (Combbox) findViewById(R.id.combbox);
        wendu= (TextView) findViewById(R.id.tv_wendu1);
        shidu= (TextView) findViewById(R.id.tv_shidu1);
        fengxiang= (TextView) findViewById(R.id.tv_fengxiang1);
        zhuangtai= (TextView) findViewById(R.id.tv_zhuangtai1);
        final List<Wether>wether=new ArrayList<Wether>();
        wether.add(new Wether("广州","26°C","85%","东风","多云"));
        wether.add(new Wether("湛江","28°C","95%","东北风","暴雨"));
        wether.add(new Wether("深圳","24°C","85%","偏东风","多云"));
        wether.add(new Wether("北京","23°C","54%","东南风","晴"));
        MyAdapter adapter=new MyAdapter(this,wether);
        combbox.setAdapter(adapter);
        combbox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
//                Toast.makeText(MainActivity.this, "你选择的是：" + str, Toast.LENGTH_SHORT).show();
                Log.d("TAG", "你选择的是：" + str);
                wendu.setText(wether.get(position).getWendu());
                shidu.setText(wether.get(position).getShidu());
                fengxiang.setText(wether.get(position).getFengxiang());
                zhuangtai.setText(wether.get(position).getZhuangtai());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    class MyAdapter extends BaseAdapter {
        private Context context = null;
        private List<Wether> wether;

        public MyAdapter(Context context,List<Wether> wether) {
            this.context = context;
            this.wether=wether;
        }

        @Override
        public int getCount() {
            return wether.size();
        }

        @Override
        public Object getItem(int position) {
            return wether.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=LayoutInflater.from(context);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_layout, null);
            }
           TextView city = (TextView) convertView.findViewById(R.id.tv);
            // 设置item中indexText的文本
            city.setText(wether.get(position).getCity());
            return convertView;
        }
    }
}

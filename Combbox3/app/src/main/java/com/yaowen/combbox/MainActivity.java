package com.yaowen.combbox;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Combbox combbox;
    private SimpleCombobox combobox2;
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
        //wether.add(new Wether("","","","",""));
        wether.add(new Wether("广州","26°C","85%","东风","多云"));
        wether.add(new Wether("湛江","28°C","95%","东北风","暴雨"));
        wether.add(new Wether("深圳","24°C","85%","偏东风","多云"));
        wether.add(new Wether("北京","23°C","54%","东南风","晴"));
        MyAdapter adapter=new MyAdapter(this,wether);
        //combbox.setSelection(-1,true);
        //combbox.setSelection(0,true);
        combbox.setAdapter(adapter);
        combbox.setSelection(1);
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
        combobox2 = (SimpleCombobox) findViewById(R.id.simpleCombobox);
        ArrayList<JSONObject> store=new ArrayList<JSONObject>(3);
        JSONObject jsonObject1=new JSONObject();
        JSONObject jsonObject2=new JSONObject();
        JSONObject jsonObject3=new JSONObject();
        try {
            jsonObject1.putOpt("name", "1");
            jsonObject1.putOpt("code", "a");
            jsonObject2.putOpt("name", "2");
            jsonObject2.putOpt("code", "b");
            jsonObject3.putOpt("name", "3");
            jsonObject3.putOpt("code", "c");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        store.add(jsonObject1);
        store.add(jsonObject2);
        store.add(jsonObject3);
        String[] list = {"1", "2", "3"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item,R.id.item_tv, list);
        combobox2.loadData(store);
        combobox2.setSelection(-1);
        combobox2.setItemClickListeners(new SimpleCombobox.OnSelectedListener() {
            @Override
            public void onCombboxItemSelected(JSONObject data) {
                try {
                    Toast.makeText(MainActivity.this, data.get("code").toString(), Toast.LENGTH_LONG).show();
                    Log.d("MainActivity","name:"+data.get("name").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

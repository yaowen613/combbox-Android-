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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Combbox combbox;
    private List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        combbox = (Combbox) findViewById(R.id.combbox);
        getData();
        // combbox.setAdapter(adapter);
        combbox.setAdapter(new MyAdapter(this));
        combbox.setOnItemSelectedListener(this);
    }

    public void getData() {
        items = new ArrayList<String>();
        items.add("多云");
        items.add("阳光");
        items.add("小雨");
        items.add("暴雨");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String str = parent.getItemAtPosition(position).toString();
        Toast.makeText(MainActivity.this, "你选择的是：" + str, Toast.LENGTH_SHORT).show();
        Log.d("TAG", "你选择的是：" + str);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class MyAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private Context context = null;

        public MyAdapter(Context context) {
            this.context = context;
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TextView indexText;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.item_layout, null);
            }
            indexText = (TextView) convertView.findViewById(R.id.tv);
            // 设置item中indexText的文本
            indexText.setText(items.get(position).toString());
            return convertView;
        }
    }
}

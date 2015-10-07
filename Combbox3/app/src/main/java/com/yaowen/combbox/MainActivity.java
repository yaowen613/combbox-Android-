package com.yaowen.combbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Combbox combbox;
    private List<String> items;
    private ArrayAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        combbox = (Combbox) findViewById(R.id.combbox);
        getData();
        adapter = new ArrayAdapter(this, R.layout.item_layout, R.id.tv, items);
        combbox.setAdapter(adapter);
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
}

package com.yaowen.combbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SimpleCombobox combobox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        combobox2 = (SimpleCombobox) findViewById(R.id.simpleCombobox);
        ArrayList<JSONObject> store = new ArrayList<JSONObject>(3);
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject3 = new JSONObject();
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item, R.id.item_tv, list);
        combobox2.loadData(store);
        combobox2.setSelection(-1);
        combobox2.setItemClickListeners(new SimpleCombobox.OnSelectedListener() {
            @Override
            public void onCombboxItemSelected(JSONObject data) {
                try {
                    Toast.makeText(MainActivity.this, data.get("code").toString(), Toast.LENGTH_LONG).show();
                    Log.d("MainActivity", "name:" + data.get("name").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        combobox2.setDisplayField("name");
//        combobox2.setDisplayField("code");
        combobox2.setValue("2");
        combobox2.getValue();
    }

}

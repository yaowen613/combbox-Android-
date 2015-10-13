package com.yaowen.combbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private SimpleCombobox combobox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        combobox2 = (SimpleCombobox) findViewById(R.id.simpleCombobox);
        //combobox2.setValue("ASS");
        combobox2.setValue("北京");
        String s = combobox2.getValue();
        combobox2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                try {
                    JSONObject object = new JSONObject(str);
                    String name = object.getString("name");
                    Toast.makeText(MainActivity.this, "你选择的项目是：" + name, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Toast.makeText(MainActivity.this, "您没有做出不一样的选择，请核对后重试！", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "您没有做选择！", Toast.LENGTH_SHORT).show();
                //Log.d("MainActivity","debug text");
            }
        });
//        Log.d("TAG", "getValue:" + s);
//        ArrayList<JSONObject> store = new ArrayList<JSONObject>(3);
//        JSONObject jsonObject1 = new JSONObject();
//        JSONObject jsonObject2 = new JSONObject();
//        JSONObject jsonObject3 = new JSONObject();
//        try {
//            jsonObject1.putOpt("name", "莉莉");
//            jsonObject1.putOpt("code", "001");
//            jsonObject2.putOpt("name", "李丽");
//            jsonObject2.putOpt("code", "002");
//            jsonObject3.putOpt("name", "萌萌");
//            jsonObject3.putOpt("code", "003");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        store.add(jsonObject1);
//        store.add(jsonObject2);
//        store.add(jsonObject3);
//        String[] list = {"1", "2", "3"};
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item, R.id.item_tv, list);
//        combobox2.loadData(store);
//        combobox2.setSelection(-1);
//        combobox2.setItemClickListeners(new SimpleCombobox.OnSelectedListener() {
//            @Override
//            public void onCombboxItemSelected(JSONObject data) {
//                try {
//                    Toast.makeText(MainActivity.this, data.get("code").toString(), Toast.LENGTH_LONG).show();
//                    Log.d("MainActivity", "name:" + data.get("name").toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        combobox2.setDisplayField("name");
////        combobox2.setDisplayField("code");
//        combobox2.setValue("萌萌");
//        String str=combobox2.getValue();
//        JSONObject object3=combobox2.findRecordByValue("莉莉");
//        //JSONObject object=combobox2.findRecordByValue("meng");
//        //JSONObject object2=combobox2.findRecordByValue("李丽");
////        Log.d("TAG","object:"+object);
////        Log.d("TAG","object2:"+object2);
//        Log.d("TAG","object3:"+object3);
//        Log.d("TAG","getValue:"+str);

    }

}

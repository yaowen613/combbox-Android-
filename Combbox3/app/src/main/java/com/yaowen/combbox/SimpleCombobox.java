package com.yaowen.combbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tendy on 2015/10/7.
 */
public class SimpleCombobox extends Spinner implements AdapterView.OnItemSelectedListener {
    private String displayField;
    private String[] otherValueFields;
    private ComboboxStore mStore;
    private OnSelectedListener mSelectedListener;
    private String testText;
    private int dataMode;
    private int dataStore;
    private CharSequence[] strings;
    private ArrayList<JSONObject> baseData = new ArrayList<JSONObject>();

    public SimpleCombobox(Context context) {
        super(context);
    }


    public SimpleCombobox(Context context, int mode) {
        super(context, mode);
    }

    public SimpleCombobox(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
         * TODO: 2015/10/12
         * Update by HelloWorld
         */
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Combobox);
        testText = typedArray.getString(R.styleable.Combobox_displayField);
        dataMode = typedArray.getInteger(R.styleable.Combobox_dataMode, -1);
        dataStore = typedArray.getResourceId(R.styleable.Combobox_dataStore, -1);
        //CharSequence[] strings=getResources().getTextArray(i);
        strings = typedArray.getResources().getTextArray(dataStore);
        //String s=strings.toString();
        if (dataMode == 0) {//0代表dataMode是json数据
            try {
                JSONObject ob = new JSONObject(String.valueOf(strings));
                JSONArray array = ob.getJSONArray("name");
                Log.d("TAG", "array:" + array);
                for (int i = 0; i < strings.length; i++) {
                    JSONObject object = (JSONObject) array.get(i);
                    baseData.add(object);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (dataMode == 1) {//1代表dataMode是string类型数组
            displayField = "name";
            for (int a = 0; a < strings.length; a++) {
//                System.out.println(strings[a]);
                JSONObject ob2 = new JSONObject();
                try {
                    ob2.put(displayField, strings[a]);
                    baseData.add(ob2);
                    Log.d("TAG", "ob2;" + ob2.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        loadData(baseData);
        Log.d("SimpleCombobox", "testText:" + testText + ",dataMode:"
                + dataMode + ",dataStore:" + dataStore + ",strings:" + strings);
        //切记要recycle（）
        typedArray.recycle();
    }

    public SimpleCombobox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SimpleCombobox(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public String getDisplayField() {
        return displayField;
    }

    public void setOtherValueFields(String[] otherValueFields) {
        this.otherValueFields = otherValueFields;
    }

    public String[] getOtherValueFields() {
        return otherValueFields;
    }

    public void loadData(ArrayList<JSONObject> jsonObjects) {
        ComboboxStore store = new ComboboxStore(this, jsonObjects);
        setStore(store);
    }

    public void loadData(String[] strings) {
        ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
        try {
            for (int i = 0; i < strings.length; i++) {
                JSONObject item = new JSONObject();
                item.putOpt(displayField, strings[i]);
                arrayList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loadData(arrayList);
    }

    public void setStore(ComboboxStore store) {
        mStore = store;
        setAdapter(mStore);
        setOnItemSelectedListener(this);
    }

    public ComboboxStore getStore() {
        return mStore;
    }

    //事件监听响应方法函数；139行-151行
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        JSONObject jsonObject = (JSONObject) getStore().getItem(position);
        if (mSelectedListener != null) {
            mSelectedListener.onCombboxItemSelected(jsonObject);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //ItemClickListeners响应方法；
    public void setItemClickListeners(OnSelectedListener listeners) {
        mSelectedListener = listeners;
    }

    public void setDisplayField(String displayField) {//自定义函数setDisplayField
        this.displayField = displayField;
        mStore.setDisplayField(displayField);
    }

    public interface OnSelectedListener {
        public void onCombboxItemSelected(JSONObject data);
    }

    public void setValue(String value) {//自定义函数
        int pos = mStore.getIndex(value);
        this.setSelection(pos);
    }

    public String getValue() {//自定义函数
        //return mStore.getDisplayField();
        return mStore.toString();
    }

    public JSONObject findRecordByValue(String value) {//自定义函数

        JSONObject ob = (JSONObject) mStore.getItem(mStore.getIndex(value));
        return ob;
    }

}

//内部类ComboboxStore继承于BaseAdapter
class ComboboxStore extends BaseAdapter {
    private Context mContext;
    private ArrayList<JSONObject> mData;

    public String getDisplayField() {
        return displayField;
    }

    public void setDisplayField(String displayField) {
        this.displayField = displayField;
    }

    private String displayField;

    public ComboboxStore(SimpleCombobox simpleCombobox, ArrayList<JSONObject> data) {
        mContext = simpleCombobox.getContext();
        mData = data;
        displayField = simpleCombobox.getDisplayField();
    }

    public ComboboxStore(Context context, ArrayList<JSONObject> data) {
        mContext = context;
        mData = data;
    }

    public int getIndex(String value) {
        int index = -1;
        for (int i = 0; i < mData.size(); i++) {
            JSONObject item = mData.get(i);
            if (value.equals(item.optString(displayField, ""))) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.support_simple_spinner_dropdown_item, parent, false);
        }
        ((TextView) convertView).setText((CharSequence) mData.get(position).opt(displayField));
        return convertView;
    }
}
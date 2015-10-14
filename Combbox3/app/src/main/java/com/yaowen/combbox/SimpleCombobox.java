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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tendy on 2015/10/7.
 */
public class SimpleCombobox extends Spinner implements AdapterView.OnItemSelectedListener {
    private String displayField;
    private String[] otherValueFields;
    private String initValue;//setValue的初始值；
    private SimpleComboboxStore mStore;
    private OnSelectedListener mSelectedListener;
    private int dataMode;
    private int dataStore;
    //SimpleCombobox的监听函数 ，判断是否初始加载SimpleCombobox控件
    private Boolean isFirst = false;
    private CharSequence[] strings;
    //实例化一个JSONObject的基础数据baseData
    private ArrayList<JSONObject> baseData = new ArrayList<JSONObject>();

    public SimpleCombobox(Context context) {
        super(context);
    }


    public SimpleCombobox(Context context, int mode) {
        super(context, mode);
    }

    public SimpleCombobox(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Combobox);
        displayField = typedArray.getString(R.styleable.Combobox_displayField);
        dataMode = typedArray.getInteger(R.styleable.Combobox_dataMode, -1);
        dataStore = typedArray.getResourceId(R.styleable.Combobox_dataStore, -1);
        strings = typedArray.getResources().getTextArray(dataStore);
        initValue = typedArray.getString(R.styleable.Combobox_value);
        if (dataMode == 0) {//dataMode的值为0时 代表strings是json数据类型
            try {
                for (int i = 0; i < strings.length; i++) {
                    String json = (String) strings[i];
                    JSONObject object = new JSONObject(json);
                    baseData.add(object);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (dataMode == 1) { //dataMode的值为1时 代表strings是string数据类型
            displayField = "name";
            for (int a = 0; a < strings.length; a++) {
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
        //调用loadData函数方法
        loadData(baseData);
        //设置初始值setValue方法的初始值
        setValue(initValue);
        //调试语句，可以删去
        Log.d("SimpleCombobox", "testText:" + displayField + ",dataMode:"
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

    /**
     * 获取显示字段的是值
     *
     * @return displayField
     * 返回显示字段的值
     **/
    public String getDisplayField() {
        return displayField;
    }

    /**
     * 设置完整的显示字段
     *
     * @param otherValueFields String[]
     **/
    public void setOtherValueFields(String[] otherValueFields) {
        this.otherValueFields = otherValueFields;
    }

    /**
     * 获取完整的显示字段的值
     *
     * @return otherValueFields
     **/
    public String[] getOtherValueFields() {
        return otherValueFields;
    }

    /**
     * 该函数主要是用来加载数据源的数据的
     *
     * @param jsonObjects JSONObject
     **/
    public void loadData(ArrayList<JSONObject> jsonObjects) {
        SimpleComboboxStore store = new SimpleComboboxStore(this, jsonObjects);
        store.setDisplayField(displayField);
        setStore(store);

    }

    /**
     * 加载数据源
     *
     * @param strings String[]
     * @throws JSONException
     **/
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

    /**
     * 改函数是设置数据源的
     *
     * @param store SimpleComboxStore
     **/
    public void setStore(SimpleComboboxStore store) {
        mStore = store;
        setAdapter(mStore);
        setOnItemSelectedListener(this);
    }

    /**
     * 获取mStore的值
     *
     * @return mStore
     **/
    public SimpleComboboxStore getStore() {
        return mStore;
    }

    //事件监听响应方法函数；139行-151行
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*
        * 用来判断控件是否第一加载的循环体
        *
        * */
        if (!isFirst) {
            isFirst = true;
            return;
        }
        JSONObject jsonObject = (JSONObject) getStore().getItem(position);
        if (mSelectedListener != null) {
            mSelectedListener.onCombboxItemSelected(this, jsonObject);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * SimpleCombobox控件的监听事件
     *
     * @param listeners OnSelectedListener
     **/
    public void setItemSelectedListener(OnSelectedListener listeners) {
        mSelectedListener = listeners;
    }

    /**
     * 设置显示字段
     *
     * @param displayField
     */
    public void setDisplayField(String displayField) {//自定义函数setDisplayField
        this.displayField = displayField;
        mStore.setDisplayField(displayField);
    }

    /**
     * 调用OnSelectedListener
     **/
    public interface OnSelectedListener {
        public void onCombboxItemSelected(SimpleCombobox combo, JSONObject data);
    }

    /**
     * 该函数只要是设置displayField属性的值
     *
     * @param value String类型
     */
    public void setValue(String value) {//自定义函数
        int pos = mStore.getIndex(value);
        this.setSelection(pos);
    }


    /**
     * 获取数据源mStore的值
     *
     * @return value
     * 返回当前值 value为null时，返回null；
     **/
    public String getValue() {//自定义函数
        //return mStore.getDisplayField();
        JSONObject value = (JSONObject) this.getSelectedItem();
        if (value == null) {
            return null;
        }
        return value.optString(displayField);
    }

    /**
     * 当查找的值不存在时，返回null
     *
     * @param value 查找的显示值
     * @return JSONObject 返回当前值,不存在时返回null
     */
    public JSONObject findRecordByValue(String value) {//自定义函数
        JSONObject ob = (JSONObject) mStore.getItem(mStore.getIndex(value));
        return ob;
    }
}

//内部类ComboboxStore继承于BaseAdapter
class SimpleComboboxStore extends BaseAdapter {
    private Context mContext;
    private ArrayList<JSONObject> mData;

    /**
     * 获得显示字段的值
     *
     * @return displayField的值
     **/
    public String getDisplayField() {
        return displayField;
    }

    /**
     * 设置显示字段的值
     *
     * @param displayField String类型的值
     **/
    public void setDisplayField(String displayField) {
        this.displayField = displayField;
    }

    private String displayField;

    public SimpleComboboxStore(SimpleCombobox simpleCombobox, ArrayList<JSONObject> data) {
        mContext = simpleCombobox.getContext();
        mData = data;
        displayField = simpleCombobox.getDisplayField();
    }

    public SimpleComboboxStore(Context context, ArrayList<JSONObject> data) {
        mContext = context;
        mData = data;
    }

    /**
     * 获取数据源的id值
     *
     * @param value String类型
     * @return index int类型
     * @deprecated 如果index为-1时，表示该值不存在，程序直接退出，没有值的变化
     **/
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
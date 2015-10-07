package com.yaowen.combbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by HelloWorld on 2015/9/30.
 */
public class Combbox extends Spinner {
    public Combbox(Context context) {
        super(context);
    }

    public Combbox(Context context, int mode) {
        super(context, mode);
    }

    public Combbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.mylabel);
        String fieldName = typedArray.getString(R.styleable.mylabel_displayfield);
        if (fieldName != null) {
        }

    }
    public Combbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Combbox(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

}

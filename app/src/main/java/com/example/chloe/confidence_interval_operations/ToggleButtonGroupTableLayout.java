package com.example.chloe.confidence_interval_operations;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class ToggleButtonGroupTableLayout extends TableLayout implements View.OnClickListener {
    public interface ToggleActionListener {
        public void OnToogleClicked(int radioButtonId);
    }

    private static final String TAG = "ToggleButtonGroupTableLayout";
    private RadioButton activeRadioButton;
    private ToggleActionListener _listener;

    /**
     * @param context
     */
    public ToggleButtonGroupTableLayout(Context context) {
        super(context);

    }

    /**
     * @param context
     * @param attrs
     */
    public ToggleButtonGroupTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override public void onClick(View v) {
        final RadioButton rb = (RadioButton) v;
        if ( activeRadioButton != null ) {
            activeRadioButton.setChecked(false);
        }
        rb.setChecked(true);
        activeRadioButton = rb;

        if (_listener != null) {
            _listener.OnToogleClicked(rb.getId());
        }
    }


    @Override public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        setChildrenOnClickListener((TableRow)child);
    }



    @Override public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        setChildrenOnClickListener((TableRow)child);
    }


    private void setChildrenOnClickListener(TableRow tr) {
        final int c = tr.getChildCount();
        for (int i=0; i < c; i++) {
            final View v = tr.getChildAt(i);
            if ( v instanceof RadioButton ) {
                v.setOnClickListener(this);
            }
        }
    }

    public int getCheckedRadioButtonId() {
        if ( activeRadioButton != null ) {
            return activeRadioButton.getId();
        }

        return -1;
    }

    public void setToggleListener(ToggleActionListener listener) {
        _listener = listener;
    }
}

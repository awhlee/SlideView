package com.awhlee.slideview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener,
            CompoundButton.OnCheckedChangeListener {

    boolean enableXMove = true;
    boolean enableYMove = true;
    int duration = 0;

    // Keep track of the coordinates where the animation needs to start
    float dX, dY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView)findViewById(R.id.dragging_text_view);
        tv.setOnTouchListener(this);

        CheckBox xBox = (CheckBox)findViewById(R.id.enableX);
        xBox.setChecked(true);
        xBox.setOnCheckedChangeListener(this);

        CheckBox yBox = (CheckBox)findViewById(R.id.enableY);
        yBox.setChecked(true);
        yBox.setOnCheckedChangeListener(this);

        EditText durationText = (EditText)findViewById(R.id.duration);
        durationText.setText("0");
        durationText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String newText = editable.toString();
                if (!TextUtils.isEmpty(newText)) {
                    duration = Integer.parseInt(newText);
                }
            }
        });
    }

    public void onCheckedChanged(CompoundButton checkBox, boolean checked) {
        if (checkBox.getId() == R.id.enableX) {
            enableXMove = checked;
        } else if (checkBox.getId() == R.id.enableY) {
            enableYMove = checked;
        }
    }

    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                ViewPropertyAnimator animator = view.animate();
                if (enableXMove) {
                    animator.x(event.getRawX() + dX);
                }
                if (enableYMove) {
                    animator.y(event.getRawY() + dY);
                }
                animator.setDuration(duration);
                animator.start();
                break;
            default:
                return false;
        }
        return true;
    }
}


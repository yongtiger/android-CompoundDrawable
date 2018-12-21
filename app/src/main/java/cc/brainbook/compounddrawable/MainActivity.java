package cc.brainbook.compounddrawable;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.tv_text_view);
        textView.setOnTouchListener(new CompoundDrawableClickListener() {
            @Override
            protected void onDrawableClick(View v, int drawableIndex) {
                switch(drawableIndex) {
                    case CompoundDrawableTouchListener.LEFT:
                        // Todo ...
                        Log.d(TAG, "onDrawableClick: CompoundDrawableTouchListener.LEFT: ");
                        break;
                }
            }
        });

        final CheckedTextView checkedTextView = findViewById(R.id.ctv_checked_text_view);
        checkedTextView.setOnTouchListener(new CompoundDrawableClickListener() {
            @Override
            protected void onDrawableClick(View v, int drawableIndex) {
                switch(drawableIndex) {
                    case CompoundDrawableTouchListener.LEFT:
                        // Todo ...
                        Log.d(TAG, "onDrawableClick: CompoundDrawableTouchListener.LEFT: ");
                        break;
                }
            }
        });

        final EditText editText = findViewById(R.id.et_edit_text);
        editText.setOnTouchListener(new CompoundDrawableClickListener() {
            @Override
            protected void onDrawableClick(View v, int drawableIndex) {
                switch(drawableIndex) {
                    case CompoundDrawableTouchListener.LEFT:
                        // Todo ...
                        Log.d(TAG, "onDrawableClick: CompoundDrawableTouchListener.LEFT: ");
                        break;
                }
            }
        });
    }
}

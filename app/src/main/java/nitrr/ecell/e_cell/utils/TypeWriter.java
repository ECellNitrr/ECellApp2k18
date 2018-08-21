package nitrr.ecell.e_cell.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class TypeWriter extends TextView {
    private CharSequence mText;
    private int mIndex;
    private long mDelay = 150;

    public TypeWriter(Context context) {
        super(context);
    }

    public TypeWriter(Context context, AttributeSet attrs){
        super(context, attrs);
    }


    private Handler handler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex++));

            if(mIndex <= mText.length()){
                handler.postDelayed(characterAdder, mDelay);
            }
        }
    };

    public void animateText(CharSequence text){
        mText = text;
        mIndex = 0;

        setText("");

        handler.removeCallbacks(characterAdder);

        handler.postDelayed(characterAdder, mDelay);
    }

    public void setCharacterDelay(long m){
        mDelay = m;
    }
}

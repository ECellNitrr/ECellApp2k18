package nitrr.ecell.e_cell.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.utils.AppConstants;
import nitrr.ecell.e_cell.utils.CustomGestureDetector;

public class HomeEsummitFragment extends Fragment implements View.OnClickListener {

    private GestureDetectorCompat detector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.esummit_fragment, container, false);

        if (getActivity() != null)
            detector = new GestureDetectorCompat(getActivity(), new CustomGestureDetector(getActivity().getSupportFragmentManager(), AppConstants.ESUMMIT_SHEET));

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return !detector.onTouchEvent(motionEvent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        Typeface bebasNeue = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.ttf");

        ImageView imageView = getView().findViewById(R.id.esImageView);
        Glide.with(getActivity())
                .load(AppConstants.IMAGE_LOCATIONS[0])
                .into(imageView);


        TextView textView = getView().findViewById(R.id.es_custom_view_text);

        textView.setText(AppConstants.HOME_TITLES[0]);
        textView.setTypeface(bebasNeue);

        textView.setOnClickListener(this);
        ImageView ivSwipeUp = getView().findViewById(R.id.swipeUpGif);
        Glide.with(this).asGif().load(R.drawable.swipe_up_gif).into(ivSwipeUp);
    }

    @Override
    public void onClick(final View view) {
        ESBottomSheetFragment fragment = new ESBottomSheetFragment();
        fragment.show(getActivity().getSupportFragmentManager(), "e-summit");
        view.setClickable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view != null) {
                    view.setClickable(true);
                }
            }
        }, 1000);
    }
}

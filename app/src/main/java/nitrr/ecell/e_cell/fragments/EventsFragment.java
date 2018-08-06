package nitrr.ecell.e_cell.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GestureDetectorCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.activities.HomeActivity;
import nitrr.ecell.e_cell.utils.AppConstants;
import nitrr.ecell.e_cell.utils.CustomGestureDetector;

public class EventsFragment extends Fragment {

    private GestureDetectorCompat detector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events_fragment, container, false);

        if (getActivity() != null)
            detector = new GestureDetectorCompat(getActivity(), new CustomGestureDetector(getActivity().getSupportFragmentManager(), AppConstants.EVENTS_SHEET));

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

    private void init(){
        Typeface bebasNeue = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.ttf");

        ImageView imageView = getView().findViewById(R.id.eventsImageView);
        Glide.with(getActivity())
                .load(AppConstants.IMAGE_LOCATIONS[1])
                .into(imageView);

        TextView textView = getView().findViewById(R.id.events_custom_view_text);
        textView.setText(AppConstants.HOME_TITLES[1]);
        textView.setTypeface(bebasNeue);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity) (getActivity());
                FragmentManager fm = activity.getSupportFragmentManager();
                nitrr.ecell.e_cell.events.activity.EventsFragment eventsFragment = nitrr.ecell.e_cell.events.activity.EventsFragment.newInstance();
                eventsFragment.show(fm, "events fragment");
            }
        });

    }
}

package nitrr.ecell.e_cell.fragments;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.events.EventsData;

public class EventsDetailFragment extends DialogFragment {

    EventsData events_data;
    private ImageView eventsCoverPic, arrowDismiss;
    private TextView eventDetailsDesc, eventDetailsLoc, eventDetailsName, eventDetailsDate, eventDetailsTime;
    private AVLoadingIndicatorView loadingIndicatorView;
    private ArrayList<EventsData> data = new ArrayList<>();

    public EventsDetailFragment() {
        // Required empty public constructor
    }

    public static EventsDetailFragment newInstance() {
        EventsDetailFragment fragment = new EventsDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.app.DialogFragment.STYLE_NO_FRAME, R.style.AppTheme);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null && d.getWindow() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            d.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_detail, container, false);
        eventsCoverPic = (ImageView) view.findViewById(R.id.eventImage);
        eventDetailsDate = (TextView) view.findViewById(R.id.eventDate);
        eventDetailsDesc = (TextView) view.findViewById(R.id.eventBody);
        eventDetailsLoc = (TextView) view.findViewById(R.id.eventLocation);
        eventDetailsName = (TextView) view.findViewById(R.id.eventTitle);
        eventDetailsTime = (TextView) view.findViewById(R.id.eventTime);
        arrowDismiss = (ImageView) view.findViewById(R.id.arrow_dismiss_event_details);
        loadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.indicator_progress_bar_event_details);

        eventDetailsName.setText(events_data.getName_response());
        eventDetailsTime.setText(events_data.getTime_response());
        eventDetailsLoc.setText(events_data.getVenue_response());
        eventDetailsDesc.setText(events_data.getDetails_response());
        eventDetailsDate.setText(events_data.getDate_response());
        Glide.with(getContext()).load(events_data.getCover_pic_response())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        loadingIndicatorView.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        loadingIndicatorView.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(eventsCoverPic);

        arrowDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsDetailFragment.this.dismiss();
            }
        });
        return view;
    }

    public void setData(EventsData data) {
        events_data = data;
    }
}

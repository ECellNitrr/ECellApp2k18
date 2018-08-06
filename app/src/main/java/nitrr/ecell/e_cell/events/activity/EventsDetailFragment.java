package nitrr.ecell.e_cell.events.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.events.Model.EventsData;

public class EventsDetailFragment extends DialogFragment {

    EventsData events_data;
    private ImageView eventsCoverPic;
    private TextView eventDetailsDesc, eventDetailsLoc, eventDetailsName, eventDetailsDate, getEventDetailsTime;
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
        getEventDetailsTime = (TextView) view.findViewById(R.id.eventTime);

        eventDetailsName.setText(events_data.getName_response());
        getEventDetailsTime.setText(events_data.getTime_response());
        eventDetailsLoc.setText(events_data.getVenue_response());
        eventDetailsDesc.setText(events_data.getDetails_response());
        eventDetailsDate.setText(events_data.getDate_response());
        Glide.with(getContext()).load(events_data.getCover_pic_response()).into(eventsCoverPic);
        return view;
    }

    void setData(EventsData data) {
        events_data = data;
    }
}

package nitrr.ecell.e_cell.events.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView eventsCoverPic;
    private TextView eventDetailsDesc,eventDetailsLoc,eventDetailsName,eventDetailsDate,getEventDetailsTime;
    private ArrayList<EventsData> data = new ArrayList<>();
    EventsData events_data;

    private OnFragmentInteractionListener mListener;

    public EventsDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventsDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsDetailFragment newInstance(String param1, String param2) {
        EventsDetailFragment fragment = new EventsDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_events_detail, container, false);
        eventsCoverPic=(ImageView) view.findViewById(R.id.eventImage);
        eventDetailsDate=(TextView) view.findViewById(R.id.eventDate);
        eventDetailsDesc=(TextView) view.findViewById(R.id.eventBody);
        eventDetailsLoc=(TextView) view.findViewById(R.id.eventLocation);
        eventDetailsName=(TextView) view.findViewById(R.id.eventTitle);
        getEventDetailsTime=(TextView) view.findViewById(R.id.eventTime);

        eventDetailsName.setText(events_data.getName_response());
        getEventDetailsTime.setText(events_data.getTime_response());
        eventDetailsLoc.setText(events_data.getVenue_response());
        eventDetailsDesc.setText(events_data.getDetails_response());
        eventDetailsDate.setText(events_data.getDate_response());
        Glide.with(getContext()).load(events_data.getCover_pic_response()).into(eventsCoverPic);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    void setData(EventsData data)
    {
        events_data=data;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
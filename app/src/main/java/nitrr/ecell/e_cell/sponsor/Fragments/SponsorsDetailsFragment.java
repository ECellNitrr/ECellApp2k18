package nitrr.ecell.e_cell.sponsor.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.sponsor.model.SponsorDetail;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SponsorsDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SponsorsDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SponsorsDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String check;
    private SponsorDetail sponsorDetail;
    private OnFragmentInteractionListener mListener;
    private TextView nameSpons,wenSpons,contactSpons,detailSpons;
    private ImageView picSpons;
    private  Context context;

    public SponsorsDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SponsorsDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SponsorsDetailsFragment newInstance(String param1, String param2) {
        SponsorsDetailsFragment fragment = new SponsorsDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    void setSponsorsData(SponsorDetail sponsorDetail,Context context){
        this.context=context;
        this.sponsorDetail = sponsorDetail;

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
        View view =inflater.inflate(R.layout.fragment_sponsors_details, container, false);

        nameSpons=(TextView)view.findViewById(R.id.sponsdetailname);
        detailSpons=(TextView)view.findViewById(R.id.sponsdetaildetails);
        contactSpons=(TextView)view.findViewById(R.id.sponsdetailcontact);
        wenSpons=(TextView)view.findViewById(R.id.sponsdetailwebsite);
        picSpons=(ImageView)view.findViewById(R.id.sponsdetailpic);
      /*  check=sponsorDetail.getDetails_s();
        Toast.makeText(getContext(), check, Toast.LENGTH_SHORT).show();*/




        wenSpons.setText(sponsorDetail.getWebsite_s());
        nameSpons.setText(sponsorDetail.getName_s());
         detailSpons.setText(sponsorDetail.getDetails_s());
         contactSpons.setText(sponsorDetail.getContact_s());
        Glide.with(context)
                .load(sponsorDetail.getPic_s()).into(picSpons);


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

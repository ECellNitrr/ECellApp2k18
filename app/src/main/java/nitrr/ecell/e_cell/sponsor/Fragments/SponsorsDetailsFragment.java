package nitrr.ecell.e_cell.sponsor.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

public class SponsorsDetailsFragment extends DialogFragment {

    private String check;
    private SponsorDetail sponsorDetail;
    private TextView nameSpons, wenSpons, contactSpons, detailSpons;
    private ImageView picSpons;
    private Context context;

    public SponsorsDetailsFragment() {
        // Required empty public constructor
    }

    public static SponsorsDetailsFragment newInstance(String param1, String param2) {
        SponsorsDetailsFragment fragment = new SponsorsDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    void setSponsorsData(SponsorDetail sponsorDetail, Context context) {
        this.context = context;
        this.sponsorDetail = sponsorDetail;

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
        View view = inflater.inflate(R.layout.fragment_sponsors_details, container, false);

        nameSpons = (TextView) view.findViewById(R.id.sponsTitle);
        detailSpons = (TextView) view.findViewById(R.id.sponsdetaildesc);
        contactSpons = (TextView) view.findViewById(R.id.sponsdetailcontact);
        wenSpons = (TextView) view.findViewById(R.id.sponsdetailwebsite);
        picSpons = (ImageView) view.findViewById(R.id.sponsImage);

        wenSpons.setText(sponsorDetail.getWebsite_s());
        nameSpons.setText(sponsorDetail.getName_s());
        detailSpons.setText(sponsorDetail.getDetails_s());
        contactSpons.setText(sponsorDetail.getContact_s());
        Glide.with(context)
                .load(sponsorDetail.getPic_s()).into(picSpons);
        return view;
    }
}

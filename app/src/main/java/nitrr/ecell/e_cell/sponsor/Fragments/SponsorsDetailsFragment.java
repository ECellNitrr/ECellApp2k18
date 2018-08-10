package nitrr.ecell.e_cell.sponsor.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wang.avi.AVLoadingIndicatorView;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.events.activity.EventsDetailFragment;
import nitrr.ecell.e_cell.sponsor.model.SponsorDetail;

public class SponsorsDetailsFragment extends DialogFragment {

    private String check;
    private SponsorDetail sponsorDetail;
    private TextView nameSpons, contactSpons, detailSpons;
    private Button sponsorWebsite;
    private ImageView picSpons, arrowDismiss;
    private Context context;
    private AVLoadingIndicatorView loadingIndicatorView;

    public SponsorsDetailsFragment() {
        // Required empty public constructor
    }

    public static SponsorsDetailsFragment newInstance() {
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
        picSpons = (ImageView) view.findViewById(R.id.sponsImage);
        arrowDismiss = (ImageView) view.findViewById(R.id.arrow_dismiss_sponsor_details);
        loadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.indicator_progress_bar_sponsor_details);
        sponsorWebsite = (Button) view.findViewById(R.id.sponsdetailwebsite);

        nameSpons.setText(sponsorDetail.getName_s());
        detailSpons.setText(sponsorDetail.getDetails_s());
        contactSpons.setText(sponsorDetail.getContact_s());
        Glide.with(context)
                .load(sponsorDetail.getPic_s())
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
                .into(picSpons);

        sponsorWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSponsorWebsiteUrl(sponsorDetail.getWebsite_s());
            }
        });

        arrowDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SponsorsDetailsFragment.this.dismiss();
            }
        });
        return view;
    }

    private void openSponsorWebsiteUrl(String url) {
        if (getContext() != null) {
            Uri uri = Uri.parse(url);
            CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
            intentBuilder.setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            intentBuilder.setStartAnimations(getContext(), R.anim.slide_up, R.anim.slide_down);
            intentBuilder.setExitAnimations(getContext(), android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            CustomTabsIntent customTabsIntent = intentBuilder.build();
            customTabsIntent.launchUrl(getContext(), uri);
        }
    }
}

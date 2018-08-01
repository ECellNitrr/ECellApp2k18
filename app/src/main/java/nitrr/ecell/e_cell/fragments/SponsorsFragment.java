package nitrr.ecell.e_cell.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.activities.HomeActivity;
import nitrr.ecell.e_cell.utils.AppConstants;

public class SponsorsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sponsors_fragment, container, false);
    }

    public static SponsorsFragment newInstance(String text){
        SponsorsFragment f = new SponsorsFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init(){
        Typeface bebasNeue = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.ttf");

        ImageView imageView = getView().findViewById(R.id.sponsImageView);
        Glide.with(getContext())
                .load(AppConstants.IMAGE_LOCATIONS[3])
                .into(imageView);

        TextView textView = getView().findViewById(R.id.spons_custom_view_text);
        textView.setText(AppConstants.HOME_TITLES[3]);
        textView.setTypeface(bebasNeue);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)getContext()).addFragment(new nitrr.ecell.e_cell.sponsor.Fragments.SponsorsFragment(), "Sponsor Fragment");
                Toast.makeText(getContext(), "sponser Fragment", Toast.LENGTH_LONG).show();
            }
        });

    }

}

package nitrr.ecell.e_cell.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.utils.AppConstants;

public class EsummitFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.esummit_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init(){
        Typeface bebasNeue = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.ttf");
        LinearLayout esLayout = getView().findViewById(R.id.es_lay);

        ImageView imageView = getView().findViewById(R.id.esImageView);
        Glide.with(getContext())
                .load(AppConstants.IMAGE_LOCATIONS[0])
                .into(imageView);


        TextView textView = getView().findViewById(R.id.es_custom_view_text);

        textView.setText(AppConstants.HOME_TITLES[0]);
        textView.setTypeface(bebasNeue);

        textView.setOnClickListener(this);
        esLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ESBottomSheetFragment fragment = new ESBottomSheetFragment();
        fragment.show(getActivity().getSupportFragmentManager(), "e-summit");
    }
}

package nitrr.ecell.e_cell.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nitrr.ecell.e_cell.R;

public class AimFragment extends Fragment {

    private TextView aimText, aimContent, aimLabel, visionText, visionContent, visionLabel;

    public AimFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.aim_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();
    }

    private void initialize(){
        Typeface raleway = Typeface.createFromAsset(getActivity().getAssets(), "fonts/raleway.ttf");
        Typeface bebasNeue = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.ttf");

        aimText = getView().findViewById(R.id.aimText);
        aimContent = getView().findViewById(R.id.aimContent);
        aimLabel = getView().findViewById(R.id.aimLabel);

        visionText = getView().findViewById(R.id.visionText);
        visionContent = getView().findViewById(R.id.visionContent);
        visionLabel = getView().findViewById(R.id.visionLabel);

        aimLabel.setTypeface(bebasNeue);
        visionLabel.setTypeface(bebasNeue);
        aimText.setTypeface(bebasNeue);
        visionText.setTypeface(bebasNeue);
    }
}

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
        Typeface bebasNeue = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.ttf");

        TextView aimText = getView().findViewById(R.id.aimText);
        TextView aimLabel = getView().findViewById(R.id.aimLabel);

        TextView visionText = getView().findViewById(R.id.visionText);
        TextView visionLabel = getView().findViewById(R.id.visionLabel);
        aimLabel.setTypeface(bebasNeue);
        visionLabel.setTypeface(bebasNeue);
        aimText.setTypeface(bebasNeue);
        visionText.setTypeface(bebasNeue);
    }
}

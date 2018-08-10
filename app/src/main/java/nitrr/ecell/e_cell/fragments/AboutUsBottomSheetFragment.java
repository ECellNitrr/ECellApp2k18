package nitrr.ecell.e_cell.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.adapters.aboutus.AboutUsViewPagerAdapter;

public class AboutUsBottomSheetFragment extends android.support.v4.app.DialogFragment {

    public AboutUsBottomSheetFragment() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Black);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getDialog().getWindow() != null)
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.aboutus_bottom_sheet, container, false);

        ViewPager pager = view.findViewById(R.id.aboutUsViewPager);
        setUpViewPager(pager);

        return view;
    }

    private void setUpViewPager(ViewPager viewPager) {
        AboutUsViewPagerAdapter adapter = new AboutUsViewPagerAdapter(getChildFragmentManager(), getContext());

        adapter.addFragment(new AimFragment());
        adapter.addFragment(new TeamFragment());
        adapter.addFragment(new ContactUsFragment());

        viewPager.setAdapter(adapter);
    }
}

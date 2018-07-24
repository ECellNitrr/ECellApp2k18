package nitrr.ecell.e_cell.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import nitrr.ecell.e_cell.fragments.AboutUsFragment;
import nitrr.ecell.e_cell.fragments.BQuizFragment;
import nitrr.ecell.e_cell.fragments.EsummitFragment;
import nitrr.ecell.e_cell.fragments.EventsFragment;
import nitrr.ecell.e_cell.fragments.SponsorsFragment;

public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EsummitFragment();
            case 1:
                return new EventsFragment();
            case 2:
                return new BQuizFragment();
            case 3:
                return new SponsorsFragment();
            case 4:
                return new AboutUsFragment();
        }
        return null;
    }
}

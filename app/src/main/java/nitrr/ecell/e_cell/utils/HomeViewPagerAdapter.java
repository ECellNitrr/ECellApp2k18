package nitrr.ecell.e_cell.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import nitrr.ecell.e_cell.fragments.HomeAboutUsFragment;
import nitrr.ecell.e_cell.fragments.HomeBQuizFragment;
import nitrr.ecell.e_cell.fragments.HomeEsummitFragment;
import nitrr.ecell.e_cell.fragments.HomeEventsFragment;
import nitrr.ecell.e_cell.fragments.HomeSponsorsFragment;

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
                return new HomeEsummitFragment();
            case 1:
                return new HomeEventsFragment();
            case 2:
                return new HomeBQuizFragment();
            case 3:
                return new HomeSponsorsFragment();
            case 4:
                return new HomeAboutUsFragment();
        }
        return null;
    }
}

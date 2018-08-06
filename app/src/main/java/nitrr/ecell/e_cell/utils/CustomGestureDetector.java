package nitrr.ecell.e_cell.utils;

import android.support.v4.app.FragmentManager;
import android.view.GestureDetector;
import android.view.MotionEvent;

import nitrr.ecell.e_cell.events.activity.EventsFragment;
import nitrr.ecell.e_cell.fragments.AboutUsBottomSheetFragment;
import nitrr.ecell.e_cell.fragments.ESBottomSheetFragment;
import nitrr.ecell.e_cell.sponsor.Fragments.SponsorsFragment;

public class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private String sheetName;
    private FragmentManager manager;

    public CustomGestureDetector(FragmentManager manager, String sheetName) {
        this.sheetName = sheetName;
        this.manager = manager;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {

        if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
                && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

            switch (sheetName) {
                case AppConstants.ABOUT_US_SHEET:
                    AboutUsBottomSheetFragment fragment = new AboutUsBottomSheetFragment();
                    fragment.show(manager, "about_us");
                    break;

                case AppConstants.ESUMMIT_SHEET:
                    ESBottomSheetFragment bottomSheetFragment = new ESBottomSheetFragment();
                    bottomSheetFragment.show(manager, "es");
                    break;

                case AppConstants.EVENTS_SHEET:
                    EventsFragment eventsFragment = EventsFragment.newInstance();
                    eventsFragment.show(manager, "ev");
                    break;

                case AppConstants.SPONSORS_SHEET:
                    SponsorsFragment sponsorsFragment = SponsorsFragment.newInstance();
                    sponsorsFragment.show(manager, "spons");
                    break;

                default:
                    break;
            }
        }

        return true;
    }
}

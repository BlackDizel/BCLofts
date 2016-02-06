package ru.byters.bclofts.controllers.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Locale;

import ru.byters.bclofts.R;
import ru.byters.bclofts.ui.fragments.DetailsImagesFragment;
import ru.byters.bclofts.ui.fragments.DetailsTextInfoFragment;

public class DetailsPagesAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private int loft_id;

    public DetailsPagesAdapter(Context context, FragmentManager fm, int loft_id) {
        super(fm);
        this.context = context;
        this.loft_id = loft_id;
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0
                ? DetailsTextInfoFragment.newInstance(loft_id)
                : DetailsImagesFragment.newInstance(loft_id);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //todo check
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.title_details_section1).toUpperCase(l);
            case 1:
                return context.getString(R.string.title_details_section2).toUpperCase(l);
        }
        return null;
    }
}

package ru.byters.bclofts.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import ru.byters.bclofts.R;
import ru.byters.bclofts.controllers.Core;
import ru.byters.bclofts.controllers.utils.DataUpdateListener;

public class DetailsImagesFragment extends Fragment
        implements DataUpdateListener {

    private final static String INTENT_LOFT_ID = "loft_id";
    private int loftId;
    ImageView imgView;

    public static DetailsImagesFragment newInstance(int loft_id) {
        DetailsImagesFragment fragment = new DetailsImagesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(INTENT_LOFT_ID, loft_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details_images,
                container, false);

        imgView = (ImageView) rootView.findViewById(R.id.imgView);
        loftId = getArguments().getInt(INTENT_LOFT_ID);

        setData();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Core) getContext().getApplicationContext()).addListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((Core) getContext().getApplicationContext()).removeListener(this);
    }

    @Override
    public void updateData(String tablename) {
        if (tablename.equals(getString(R.string.db_table_images)))
            if (isAdded())
                setData();
    }

    void setData() {
        ArrayList<String> imgUris = ((Core) getContext().getApplicationContext()).getControllerLofts().getImages(loftId);
        if (imgUris == null) return;
        ImageLoader.getInstance().displayImage(imgUris.get(0), imgView);
    }
}

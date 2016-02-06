package ru.byters.bclofts.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.byters.bclofts.R;
import ru.byters.bclofts.controllers.Core;
import ru.byters.bclofts.controllers.utils.DataUpdateListener;
import ru.byters.bclofts.model.Loft;
import ru.byters.bclofts.ui.activities.ActivityMain;
import ru.byters.bclofts.ui.utils.LoftBalloonItem;
import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.balloon.OnBalloonListener;
import ru.yandex.yandexmapkit.overlay.location.MyLocationItem;
import ru.yandex.yandexmapkit.overlay.location.OnMyLocationListener;
import ru.yandex.yandexmapkit.utils.GeoPoint;

public class MapFragment extends Fragment
        implements OnBalloonListener, OnMyLocationListener, DataUpdateListener {

    MapController mapController;
    OverlayManager mOverlayManager;
    Overlay overlay;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        initMap(rootView);
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

    void initMap(View v) {
        MapView mapView = (MapView) v.findViewById(R.id.map);
        mapController = mapView.getMapController();
        mOverlayManager = mapController.getOverlayManager();
        mapController.getOverlayManager().getMyLocation().addMyLocationListener(this);
        overlay = new Overlay(mapController);
    }

    public void setData() {
        if (overlay.getOverlayItems().size() == 0) {
            // Load required resources

            ArrayList<Loft> lofts = ((Core) getContext().getApplicationContext()).getControllerLofts().getLofts();
            if (lofts == null) return;
            for (Loft l : lofts) {
                //todo edit icon
                OverlayItem oItem = new OverlayItem(new GeoPoint(l.pointx, l.pointy), getResources().getDrawable(R.drawable.ymk_balloon_black));

                LoftBalloonItem bItem = new LoftBalloonItem(getContext(), oItem.getGeoPoint());
                bItem.setText(l.name);

                bItem.loftId = l.id;
                bItem.setOnBalloonListener(this);

                oItem.setBalloonItem(bItem);

                overlay.addOverlayItem(oItem);
            }

            mOverlayManager.addOverlay(overlay);
        }

    }

    @Override
    public void onMyLocationChange(MyLocationItem myLocationItem) {
        Activity activity = getActivity();
        if (activity instanceof ActivityMain) {
            ((ActivityMain) activity).setUserPoint(myLocationItem.getGeoPoint());
        }
    }

    @Override
    public void onBalloonHide(BalloonItem arg0) {
        Activity activity = getActivity();
        if (activity instanceof ActivityMain) {
            ((ActivityMain) activity).clearSelectedLoftId();
            activity.invalidateOptionsMenu();
        }
    }

    @Override
    public void onBalloonShow(BalloonItem arg0) {
        Activity activity = getActivity();
        if (activity instanceof ActivityMain) {
            ((ActivityMain) activity).setSelectedLoftId(((LoftBalloonItem) arg0).loftId);
            activity.invalidateOptionsMenu();
        }
    }

    @Override
    public void onBalloonAnimationEnd(BalloonItem arg0) {
    }

    @Override
    public void onBalloonAnimationStart(BalloonItem arg0) {
    }

    @Override
    public void onBalloonViewClick(BalloonItem arg0, View arg1) {
    }

    @Override
    public void updateData(String tablename) {
        if (tablename.equals(getString(R.string.db_table_list)))
            if (isAdded())
                setData();
    }
}

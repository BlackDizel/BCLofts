package ru.byters.bclofts.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.List;

import ru.byters.bclofts.R;
import ru.byters.bclofts.controllers.Core;
import ru.byters.bclofts.ui.fragments.ListFragment;
import ru.byters.bclofts.ui.fragments.MapFragment;
import ru.yandex.yandexmapkit.utils.GeoPoint;

public class ActivityMain extends ActivityBase implements
        ActionBar.OnNavigationListener {

    private final static int NO_VALUE = -1;
    private int selectedLoftId;
    private GeoPoint userPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            actionBar.setListNavigationCallbacks(
                    new ArrayAdapter<>(actionBar.getThemedContext(),
                            android.R.layout.simple_list_item_1,
                            android.R.id.text1, new String[]{
                            getString(R.string.title_section1),
                            getString(R.string.title_section2)}), this);
        }
        selectedLoftId = NO_VALUE;
    }

    public void clearSelectedLoftId() {
        selectedLoftId = NO_VALUE;
    }

    public void setSelectedLoftId(int selectedLoftId) {
        this.selectedLoftId = selectedLoftId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (selectedLoftId != NO_VALUE)
            getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_navigate:
                if (selectedLoftId == NO_VALUE || userPoint == null)
                    return false;

                Intent intent = new Intent("ru.yandex.yandexnavi.action.BUILD_ROUTE_ON_MAP");
                intent.setPackage("ru.yandex.yandexnavi");

                PackageManager pm = getPackageManager();
                List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);

                if (infos == null || infos.size() == 0) {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=ru.yandex.yandexnavi"));
                } else {
                    GeoPoint g = ((Core) getApplicationContext()).getControllerLofts().getLoftCoordsById(selectedLoftId);
                    if (g != null) {
                        intent.putExtra("lat_from", userPoint.getLat());
                        intent.putExtra("lon_from", userPoint.getLon());
                        intent.putExtra("lat_to", g.getLat());
                        intent.putExtra("lon_to", g.getLon());
                    }
                }

                startActivity(intent);
                return true;

            case R.id.action_details:
                if (selectedLoftId == NO_VALUE) return false;
                ActivityDetails.display(this, selectedLoftId);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Fragment f;
        if (itemPosition == 0) f = MapFragment.newInstance();
        else f = ListFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, f).commit();

        selectedLoftId=NO_VALUE;
        invalidateOptionsMenu();

        return true;
    }

    public void setUserPoint(GeoPoint userPoint) {
        this.userPoint = userPoint;
    }
}




package ru.byters.bclofts.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.byters.bclofts.R;
import ru.byters.bclofts.controllers.Core;
import ru.byters.bclofts.controllers.utils.DataUpdateListener;
import ru.byters.bclofts.model.LoftDetails;

public class DetailsTextInfoFragment extends Fragment
        implements DataUpdateListener {
    private final static String INTENT_LOFT_ID = "loft_id";
    ListView lvDetails;
    ArrayList<LoftDetails> details_cache;
    private int loftId;

    public DetailsTextInfoFragment() {
    }

    public static DetailsTextInfoFragment newInstance(int loft_id) {
        DetailsTextInfoFragment fragment = new DetailsTextInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(INTENT_LOFT_ID, loft_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details_text_info,
                container, false);

        lvDetails = (ListView) rootView.findViewById(R.id.lvDetails);
        this.loftId = getArguments().getInt(INTENT_LOFT_ID);

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
        if (tablename.equals(getString(R.string.db_table_details)))
            if (isAdded())
                setData();
    }

    public void setData() {

        details_cache = ((Core) getContext().getApplicationContext()).getControllerLofts().getDetails(loftId);
        if (details_cache == null) return;

        @SuppressWarnings("unchecked")
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, details_cache) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(details_cache.get(position).content);
                text2.setText(details_cache.get(position).value);
                return view;
            }
        };
        lvDetails.setAdapter(adapter);
    }
}

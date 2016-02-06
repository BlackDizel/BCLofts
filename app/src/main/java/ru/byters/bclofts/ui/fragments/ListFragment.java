package ru.byters.bclofts.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import ru.byters.bclofts.R;
import ru.byters.bclofts.controllers.Core;
import ru.byters.bclofts.controllers.utils.DataUpdateListener;
import ru.byters.bclofts.model.LoftListItem;
import ru.byters.bclofts.ui.activities.ActivityDetails;

public class ListFragment extends Fragment
        implements DataUpdateListener, AdapterView.OnItemClickListener {

    private ListView lvMain;
    private ProgressBar pb;
    private ArrayList<LoftListItem> list_cache;

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        pb = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        lvMain = (ListView) rootView.findViewById(R.id.lvLofts);
        lvMain.setOnItemClickListener(this);

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
        if (tablename.equals(getString(R.string.db_table_list))
                || tablename.equals(getString(R.string.db_table_details)))
            if (isAdded())
                setData();
    }


    void setData() {

        list_cache = ((Core) getContext().getApplicationContext()).getControllerLofts().getListData();
        if (list_cache == null) return;

        @SuppressWarnings({"rawtypes", "unchecked"})
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, list_cache) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(list_cache.get(position).name);
                text2.setText(list_cache.get(position).address);
                return view;
            }
        };

        lvMain.setAdapter(adapter);
        pb.setVisibility(View.GONE);
        lvMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ActivityDetails.display(getContext(), list_cache.get(position).id);
    }
}

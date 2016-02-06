package ru.byters.bclofts.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import ru.byters.bclofts.R;
import ru.byters.bclofts.controllers.adapters.DetailsPagesAdapter;

public class ActivityDetails extends ActivityBase {
    private static final String INTENT_LOFT_ID = "loft_id";
    private static final int NO_VALUE = -1;

    public static void display(Context context, int selectedLoftId) {
        Intent intent = new Intent(context, ActivityDetails.class);
        intent.putExtra(INTENT_LOFT_ID, selectedLoftId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int loftId = getIntent().getIntExtra(INTENT_LOFT_ID, NO_VALUE);

        if (loftId != NO_VALUE)
            ((ViewPager) findViewById(R.id.pager)).setAdapter(new DetailsPagesAdapter(this, getSupportFragmentManager(), loftId));
    }
}

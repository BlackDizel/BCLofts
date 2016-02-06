package ru.byters.bclofts.ui.utils;

import android.content.Context;

import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;

public class LoftBalloonItem extends BalloonItem {
    public int loftId;

    public LoftBalloonItem(Context arg0, GeoPoint arg1) {
        super(arg0, arg1);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
package ru.byters.bclofts.controllers;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.byters.bclofts.model.Loft;
import ru.byters.bclofts.model.LoftDetails;
import ru.byters.bclofts.model.LoftListItem;
import ru.byters.bclofts.model.ModelLofts;
import ru.yandex.yandexmapkit.utils.GeoPoint;

public class ControllerLofts {

    private ModelLofts model;

    public ControllerLofts(Context context) {
        model = new ModelLofts(context);
    }

    @Nullable
    public GeoPoint getLoftCoordsById(int id) {
        return model.getLoftCoordsById(id);
    }

    public <T> void writeData(Context context, String tablename, List<T> result) {
        model.writeData(context, tablename, result);
    }

    @Nullable
    public ArrayList<Loft> getLofts() {
        return model.getLofts();
    }

    @Nullable
    public ArrayList<LoftListItem> getListData() {
        return model.getListData();
    }

    @Nullable
    public ArrayList<String> getImages(int loftId) {
        return model.getImages(loftId);
    }

    @Nullable
    public ArrayList<LoftDetails> getDetails(int loftId) {
        return model.getDetails(loftId);
    }
}

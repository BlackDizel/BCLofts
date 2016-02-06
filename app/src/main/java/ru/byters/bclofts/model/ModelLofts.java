package ru.byters.bclofts.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import ru.byters.bclofts.R;
import ru.byters.bclofts.controllers.ControllerStorage;
import ru.yandex.yandexmapkit.utils.GeoPoint;

public class ModelLofts {

    @Nullable
    private ArrayList<Loft> lLofts;
    @Nullable
    private ArrayList<LoftImage> lImages;
    @Nullable
    private ArrayList<LoftDetails> lDetails;

    public ModelLofts(@NonNull Context context) {
        lLofts = (ArrayList<Loft>) ControllerStorage.readObjectFromFile(context, ControllerStorage.LOFTS);
        lImages = (ArrayList<LoftImage>) ControllerStorage.readObjectFromFile(context, ControllerStorage.IMAGES);
        lDetails = (ArrayList<LoftDetails>) ControllerStorage.readObjectFromFile(context, ControllerStorage.DETAILS);
    }

    @Nullable
    public GeoPoint getLoftCoordsById(int id) {
        if (lLofts == null) return null;

        for (Loft l : lLofts)
            if (l.id == id)
                return new GeoPoint(l.pointx, l.pointy);

        return null;
    }

    public <T> void writeData(@NonNull Context context, @Nullable String tablename, @Nullable List<T> result) {
        if (TextUtils.isEmpty(tablename) || result == null) return;

        if (tablename.equals(context.getString(R.string.db_table_list))) {
            lLofts = (ArrayList<Loft>) result;
            ControllerStorage.writeObjectToFile(context, lLofts, ControllerStorage.LOFTS);
        } else if (tablename.equals(context.getString(R.string.db_table_images))) {
            lImages = (ArrayList<LoftImage>) result;
            ControllerStorage.writeObjectToFile(context, lImages, ControllerStorage.IMAGES);
        } else if (tablename.equals(context.getString(R.string.db_table_details))) {
            lDetails = (ArrayList<LoftDetails>) result;
            ControllerStorage.writeObjectToFile(context, lDetails, ControllerStorage.DETAILS);
        }

    }

    @Nullable
    public ArrayList<Loft> getLofts() {
        return lLofts;
    }

    @Nullable
    public ArrayList<LoftListItem> getListData() {
        if (lLofts == null || lDetails == null) return null;

        ArrayList<LoftListItem> items = new ArrayList<>();
        for (Loft loft : lLofts) {
            LoftListItem item = new LoftListItem(loft.id, loft.name);
            for (LoftDetails details : lDetails)
                if (details.loftId == loft.id
                        && details.content.equals("Адрес")) { //todo move string to resources
                    item.address = details.value;
                    break;
                }

            items.add(item);
        }

        return items.size() > 0 ? items : null;
    }

    @Nullable
    public ArrayList<String> getImages(int loftId) {
        if (lImages == null)
            return null;

        ArrayList<String> items = new ArrayList<>();
        for (LoftImage image : lImages)
            if (image.loftId == loftId)
                items.add(image.path);

        return items.size() > 0 ? items : null;
    }

    @Nullable
    public ArrayList<LoftDetails> getDetails(int loftId) {
        if (lDetails == null) return null;

        ArrayList<LoftDetails> items = new ArrayList<>();
        for (LoftDetails item : lDetails)
            if (item.loftId == loftId)
                items.add(item);

        return items.size() > 0 ? items : null;
    }
}

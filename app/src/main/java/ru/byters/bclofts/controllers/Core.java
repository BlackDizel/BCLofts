package ru.byters.bclofts.controllers;

import android.app.Application;
import android.support.annotation.NonNull;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import ru.byters.azure.AzureConnect;
import ru.byters.azure.AzureThrowListener;
import ru.byters.bclofts.R;
import ru.byters.bclofts.controllers.utils.DataUpdateListener;
import ru.byters.bclofts.model.Loft;
import ru.byters.bclofts.model.LoftDetails;
import ru.byters.bclofts.model.LoftImage;

public class Core extends Application implements AzureThrowListener {

    private ControllerLofts controllerLofts;
    private AzureConnect azureClient;
    private ArrayList<DataUpdateListener> listeners;

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

        listeners = new ArrayList<>();

        azureClient = new AzureConnect(this
                , getString(R.string.azure_address)
                , getString(R.string.azure_key));

        controllerLofts = new ControllerLofts(this);

        azureClient.addListener(this);

        //fixme optimize, add swipe refresh layout
        azureClient.getTableTop(getString(R.string.db_table_list), Loft.class, 500);
        azureClient.getTableTop(getString(R.string.db_table_images), LoftImage.class, 500);
        azureClient.getTableTop(getString(R.string.db_table_details), LoftDetails.class, 500);

    }

    public ControllerLofts getControllerLofts() {
        return controllerLofts;
    }

    @Override
    public <T> void OnDownloadCompleted(String tablename, List<T> result, Boolean error) {
        controllerLofts.writeData(this, tablename, result);
        for (DataUpdateListener listener : listeners)
            listener.updateData(tablename);
    }


    public void addListener(@NonNull DataUpdateListener listener) {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    public void removeListener(@NonNull DataUpdateListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void OnUploadCompleted(String tablename, Boolean error) {

    }
}

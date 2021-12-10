package com.example.recipe;

import android.app.Application;

public class myApp extends Application {
    private NetworkingService networkingService = new NetworkingService();

    private JsonService jsonService = new JsonService();
    public JsonService getJsonService() {
        return jsonService;
    }




    public NetworkingService getNetworkingService() {
        return networkingService;
    }
}

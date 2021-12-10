package com.example.recipe;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingService {
    // FOR THE FIRST API
    private String titleURL = "https://api.spoonacular.com/recipes/findByIngredients?apiKey=eb802bfb23e4425d98a2db69dad02ea0&ingredients=";
    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(4);
    public static Handler networkingHandler = new Handler(Looper.getMainLooper());

    // FOR THE SECOND API
    private String recipeURL = "https://api.spoonacular.com/recipes/";
    private String recipeURLtwo = "/information?apiKey=eb802bfb23e4425d98a2db69dad02ea0&includeNutrition=false";
    //public static ExecutorService networkExecutorServiceTwo = Executors.newFixedThreadPool(4);
   // public static Handler networkingHandlerTwo = new Handler(Looper.getMainLooper());

    interface NetworkingListener{
        void dataListener(String josnString);
    }

    public NetworkingListener listener;

    public void searchTitles(String ingredients) {
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                    String jsonData = "";

                    String urlString = titleURL + ingredients;
                    Log.d("networking", urlString);
                    URL urlObj = new URL(urlString);
                    httpURLConnection = (HttpURLConnection) urlObj.openConnection();
                    httpURLConnection.setRequestMethod("GET");// post, delete, put
                    httpURLConnection.setRequestProperty("Conent-Type","application/json");

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int inputSteamData = 0;

                    while ( (inputSteamData = reader.read()) != -1){// there is data in this stream
                        char current = (char)inputSteamData;
                        jsonData += current;
                    }
                    final String finalData = jsonData;
                    // the data is ready
                    Log.d("final data", finalData);
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // any code here will run in main thread
                            listener.dataListener(finalData);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    httpURLConnection.disconnect();
                }

            }
        });



    }
    public void searchRecipes(String id) {
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                    String jsonData = "";
                    String url = recipeURL + id + recipeURLtwo;

                    URL urlObj = new URL(url);
                    httpURLConnection = (HttpURLConnection) urlObj.openConnection();
                    httpURLConnection.setRequestMethod("GET");// post, delete, put
                    httpURLConnection.setRequestProperty("Conent-Type", "application/json");

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int inputSteamData = 0;

                    while ((inputSteamData = reader.read()) != -1) {// there is data in this stream
                        char current = (char) inputSteamData;
                        jsonData += current;
                    }
                    final String finalData = jsonData;
                    // the data is ready
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // any code here will run in main thread
                            listener.dataListener(finalData);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    httpURLConnection.disconnect();
                }

            }
        });



    }
}

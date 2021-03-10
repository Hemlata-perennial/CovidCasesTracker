package com.hemlata.corona;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DataFetcher {
    public String worldData() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://covid-19-tracking.p.rapidapi.com/v1"))
                .header("x-rapidapi-key", "d389aa5358msh4674e22ec76993ep1fb164jsnd244ab94763a")
                .header("x-rapidapi-host", "covid-19-tracking.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return response.body();
    }
    public String CountryWiseData() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://covid-19-coronavirus-statistics.p.rapidapi.com/v1/stats"))
                .header("x-rapidapi-key", "d389aa5358msh4674e22ec76993ep1fb164jsnd244ab94763a")
                .header("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return response.body();
    }

}

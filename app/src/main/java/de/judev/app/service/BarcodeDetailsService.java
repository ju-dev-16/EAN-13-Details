package de.judev.app.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;

import de.judev.app.config.Config;
import de.judev.app.model.BarcodeDetails;

public class BarcodeDetailsService {

    Config config = new Config();

    public BarcodeDetails getBarcodeDetails(String barcode) {
        
        HttpClient client = HttpClient.newHttpClient();

        try {

            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(config.getBaseUrl() + barcode + "?apikey=" + config.getApiToken()))
                .GET()
                .build();

            Map<String, String> items = new HashMap<String, String>();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            String removeBrackets = response.body().replaceAll("[\\[\\](){}]", "");

            String removeQuotationMarks = removeBrackets.replaceAll("\"", "");

            String removeMetadata = removeQuotationMarks.replace("metadata:", "");

            String formattedResponse = removeMetadata.replaceAll("\\s", "");

            System.out.println(formattedResponse);

            String[] pairs = formattedResponse.split(",");

            for (int i = 0; i < pairs.length; i++) {

                String pair = pairs[i];

                String[] keyValue = pair.split(":");

                items.put(keyValue[0], keyValue[1]);

            }


        } catch (URISyntaxException | IOException | InterruptedException e) {

            e.printStackTrace();

        }

        return null;
    }
}
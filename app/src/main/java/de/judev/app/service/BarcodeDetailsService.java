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

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            String formattedResponseBody = formatResponseBody(response.body());

            Map<String, String> details = getDetailsFromBody(formattedResponseBody);

            return new BarcodeDetails(
                details.get("title"), 
                details.get("alias"), 
                details.get("description"), 
                details.get("brand"), 
                details.get("category"), 
                details.get("msrp"),
                details.get("barcode")
            );

        } catch (URISyntaxException | IOException | InterruptedException e) {

            e.printStackTrace();

        }

        return null;
    }

    private String formatResponseBody(String responseBody) {

        String removeBrackets = responseBody.replaceAll("[\\[\\](){}]", "");

        String removeQuotationMarks = removeBrackets.replaceAll("\"", "");

        String removeMetadata = removeQuotationMarks.replace("metadata:", "");

        return removeMetadata.replaceAll("\\s", "");
    }

    private Map<String, String> getDetailsFromBody(String formattedResponseBody) {

        Map<String, String> details = new HashMap<String, String>();

        String[] pairs = formattedResponseBody.split(",");

        for (int i = 0; i < pairs.length; i++) {

            String pair = pairs[i];

            String[] keyValue = pair.split(":");

            try {

                details.put(keyValue[0], keyValue[1]);

            } catch (ArrayIndexOutOfBoundsException e) {
                
                details.put(keyValue[0], "-");

            }
        }

        return details;
    }
}
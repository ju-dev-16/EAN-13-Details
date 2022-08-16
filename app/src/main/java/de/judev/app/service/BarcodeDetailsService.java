package de.judev.app.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.judev.app.data.BarcodeDetails;

@Service
public class BarcodeDetailsService {

    @Value("${base.url}")
    private String baseUrl;
    
    @Value("${api.key}")
    private String apiKey;

    public BarcodeDetails getBarcodeDetails(String barcode) {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + barcode + "?apikey=" + apiKey))
            .GET()
            .build();

        try {

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            Map<String, String> details = getDetailsFromBody(formatResponseBody(response.body()));

            return new BarcodeDetails(
                details.get("title"), 
                details.get("alias"), 
                details.get("description"), 
                details.get("brand"), 
                details.get("category"), 
                details.get("msrp"),
                details.get("barcode")
            );

        } catch (IOException | InterruptedException e) {

            e.printStackTrace();

        }

        return new BarcodeDetails(
            "-", 
            "-", 
            "-", 
            "-", 
            "-", 
            "-",
            "-"
        );
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

                if (keyValue[1] == "null") {

                    details.put(keyValue[0], "-");

                } else {

                    details.put(keyValue[0], keyValue[1]);
                    
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                
                details.put(keyValue[0], "-");

            }
        }

        return details;
    }
}
package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class ApiService {
    private static final Logger logger = Logger.getLogger(ApiService.class.getName());

    public String fetchApiResponse(String apiUrl){
        StringBuilder response = new StringBuilder();
        try {
            logger.info("Making API request: " +apiUrl);
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            logger.info("API response code: "+responseCode);

            if(responseCode == HttpURLConnection.HTTP_OK){
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                    String line;
                    while((line = reader.readLine()) != null){
                        response.append(line);
                    }
                }logger.info("Successfully retrieved");
            }else {
                logger.warning("API request failed with response code: "+responseCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.toString();
    }

}

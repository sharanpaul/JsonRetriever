package api;

import api.exceptions.ApiConnectionException;
import api.exceptions.InvalidJsonException;
import api.exceptions.MissingFieldException;
import api.exceptions.ProcessingException;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import java.io.IOException;
import java.util.logging.Logger;

public class Extractor {
    private static final Logger logger = Logger.getLogger(Extractor.class.getName());

    public static void main(String[] args) {
        String apiUrl = "https://jsonplaceholder.typicode.com/posts";
        ApiService apiService = new ApiService();

        logger.info("Starting API call");
        String jsonResponse = apiService.fetchApiResponse(apiUrl);

        try {
            if (jsonResponse == null || jsonResponse.isEmpty()) {
                throw new ApiConnectionException("Received null or empty response from API endpoint: " + apiUrl);
            }

            try {
                JSONArray responseArray = JSONArray.parse(jsonResponse);
                for (Object responseElement : responseArray) {
                    JSONObject responseObject = (JSONObject) responseElement;

                    try {
                        validatePostData(responseObject);
                        processPostData(responseObject);
                        logger.info("Successfully processed post with ID: " + responseObject.get("id"));
                    } catch (MissingFieldException e) {
                        logger.warning("Validation error for Post: " + e.getMessage());
                    } catch (ProcessingException e) {
                        logger.severe("Critical error during processing: " + e.getMessage());
                    }
                }
                logger.info("Finished processing API response");
            } catch (IOException e) {
                throw new InvalidJsonException("Unable to parse API response. Root cause: " + e.getMessage());
            }
        } catch (ApiConnectionException e) {
            logger.severe("Connection error: " + e.getMessage());
        } catch (InvalidJsonException e) {
            logger.severe("Invalid JSON data: " + e.getMessage());
        }
    }

    private static void validatePostData(JSONObject responseObject) {
        String id = responseObject.get("id").toString();
        if(responseObject.get("id") == null || responseObject.get("title") == null || responseObject.get("body") == null || "7".equals(id)) {
            throw new MissingFieldException("Post missing fields. Data: " + responseObject);
        }
    }

    private static void processPostData(JSONObject responseObject) {

        String id = responseObject.get("id").toString();
        if ("5".equals(id)) {
            throw new ProcessingException("Processing failed for post ID: "+ id);
        }
        System.out.println("Processing post: "+responseObject);
    }
}

package api;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import java.io.IOException;
import java.util.logging.Logger;

public class Extractor {
    private static final Logger logger = Logger.getLogger(Extractor.class.getName());

    public static void main(String[] args) throws IOException {
        String apiUrl = "https://jsonplaceholder.typicode.com/posts";
        ApiService apiService = new ApiService();

        logger.info("Starting API call");
        String jsonResponse = apiService.fetchApiResponse(apiUrl);

        if(jsonResponse!=null){
            try {
                JSONArray responseArray = JSONArray.parse(jsonResponse);
                for(Object responseElement : responseArray){
                    JSONObject responseObject = (JSONObject) responseElement;
                    System.out.println(responseObject);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            logger.warning("No response received");
        }
    }
}

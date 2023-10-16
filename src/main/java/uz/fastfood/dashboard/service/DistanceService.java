package uz.fastfood.dashboard.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class DistanceService {


    @Value("${google.api.key}")
    private String apiKey;

    public double calculateDistance(double originLat, double originLng, double destLat, double destLng) {


        try {
            String origin = originLat + "," + originLng;
            String destination = destLat + "," + destLng; // Replace with your destination


            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="
                    + origin + "&destinations=" +destination  + "&key=" + apiKey;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("Matrix response : " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return 1;
    }


}

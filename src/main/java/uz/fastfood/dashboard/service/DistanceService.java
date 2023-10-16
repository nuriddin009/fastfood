package uz.fastfood.dashboard.service;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.response.DistanceInfo;
import uz.fastfood.dashboard.dto.response.RouteData;
import uz.fastfood.dashboard.repository.BranchRepository;

import java.io.IOException;
import java.util.TreeMap;
import java.util.UUID;

@Service
public class DistanceService {


    private final BranchRepository branchRepository;
    @Value("${map.api.key}")
    private String apiKey;

    public DistanceService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public double calculateDistance(double originLat, double originLng, double destLat, double destLng) {

        double distance = 0;

        try {
            double[][] coordinates = {
                    {originLat, originLng},
                    {destLat, destLng}
            };

            String mode = "walk";

            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{\"mode\":\"").append(mode).append("\",\"sources\":[");

            for (double[] coordinate : coordinates) {
                jsonBuilder.append("{\"location\":[").append(coordinate[0]).append(",").append(coordinate[1]).append("]},");
            }

            jsonBuilder.setCharAt(jsonBuilder.length() - 1, ']');


            jsonBuilder.append(",\"targets\":[");

            for (double[] coordinate : coordinates) {
                jsonBuilder.append("{\"location\":[").append(coordinate[0]).append(",").append(coordinate[1]).append("]},");
            }

            jsonBuilder.setCharAt(jsonBuilder.length() - 1, ']');
            jsonBuilder.append("}");

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, jsonBuilder.toString());

            Request request = new Request.Builder()
                    .url("https://api.geoapify.com/v1/routematrix?apiKey=" + apiKey)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            String response = client.newCall(request).execute().body().string();


            System.out.println(response);
            Gson gson = new Gson();
            RouteData routeData = gson.fromJson(response, RouteData.class);
            distance = routeData.getSources_to_targets().get(0).get(1).getDistance();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return distance;
    }

    public TreeMap<UUID, Double> coordination(double clientLongitude, double clientLatitude) {
        TreeMap<UUID, Double> map = new TreeMap<>();
        branchRepository.findAllByDeletedFalse().forEach(
                branch -> map.put(branch.getId(), validateCoordinates(branch.getLongitude(), branch.getLatitude(), clientLongitude, clientLatitude))
        );
        return map;
    }


    private Double validateCoordinates(double branchLongitude, double branchLatitude, double agentLongitude, double agentLatitude) {

        final int radius = 6373477;

        double difLat = Math.toRadians(branchLatitude - agentLatitude);

        double difLong = Math.toRadians(branchLongitude - agentLongitude);

        double a = Math.sin(difLat / 2) * Math.sin(difLat / 2) +
                Math.cos(Math.toRadians(branchLatitude)) * Math.cos(Math.toRadians(agentLatitude)) *
                        Math.sin(difLong / 2) * Math.sin(difLong / 2);
        return 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) * radius;
    }
}

package uz.fastfood.dashboard.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.repository.BranchRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;
import java.util.UUID;

@Service
public class DistanceService {


    private final BranchRepository branchRepository;
    @Value("${google.api.key}")
    private String apiKey;

    public DistanceService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

//    public double calculateDistance(double originLat, double originLng, double destLat, double destLng) {
//
//
//        try {
//            String origin = originLat + "," + originLng;
//            String destination = destLat + "," + destLng; // Replace with your destination
//
//
//            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="
//                    + origin + "&destinations=" + destination + "&key=" + apiKey;
//
//            URL obj = new URL(url);
//            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//            con.setRequestMethod("GET");
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            System.out.println("Matrix response : " + response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        return 1;
//    }

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

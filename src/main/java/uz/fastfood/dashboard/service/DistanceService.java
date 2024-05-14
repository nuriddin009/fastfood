package uz.fastfood.dashboard.service;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.fastfood.dashboard.dto.response.DistanceInfo;
import uz.fastfood.dashboard.dto.response.DistanceResponse;
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

        String apiUrl = "https://api-v2.distancematrix.ai/maps/api/distancematrix/json?origins=" + originLat + ","
                + originLng + "&destinations=" + destLat + "," + destLng + "&key=" + apiKey;

        ResponseEntity<DistanceResponse> forEntity = new RestTemplate().getForEntity(apiUrl, DistanceResponse.class);
        DistanceResponse body = forEntity.getBody();
        assert body != null;
        int value = body.getRows()[0].getElements()[0].getDistance().getValue();


        return Double.parseDouble(String.valueOf(value))/1000;
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

        double a = Math.sin(difLat/2)*Math.sin(difLat/2) +
                Math.cos(Math.toRadians(branchLatitude))*Math.cos(Math.toRadians(agentLatitude))*
                        Math.sin(difLong/2)*Math.sin(difLong/2);
        return 2*Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))*radius;
    }
}

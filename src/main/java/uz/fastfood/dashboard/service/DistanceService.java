package uz.fastfood.dashboard.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.fastfood.dashboard.dto.response.YandexDistanceResponse;

@Service
public class DistanceService {


    @Value("${yandex.api.key}")
    private String apiKey;

    public double calculateDistance(double originLat, double originLng, double destLat, double destLng) {
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "https://api-maps.yandex.ru/services/route/v1/distancematrix";
        String url = apiUrl + "?origins=" + originLat + "," + originLng +
                "&destinations=" + destLat + "," + destLng +
                "&apikey=" + apiKey;

        YandexDistanceResponse response = restTemplate.getForObject(url, YandexDistanceResponse.class);

        if (response != null && response.getStatus().equals("OK")) {
            return response.getDistance();
        } else {
            return -1;
        }
    }


}

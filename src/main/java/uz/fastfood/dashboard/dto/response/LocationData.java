package uz.fastfood.dashboard.dto.response;

import lombok.Data;


@Data
public class LocationData {

    private double[] original_location;
    private double[] location;

}

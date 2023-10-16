package uz.fastfood.dashboard.dto.response;


import lombok.Data;

import java.util.List;

@Data
public class RouteData {
    private List<LocationData> sources;
    private List<LocationData> targets;
    private List<List<DistanceInfo>> sources_to_targets;
    private String units;
    private String distance_units;
    private String mode;
}

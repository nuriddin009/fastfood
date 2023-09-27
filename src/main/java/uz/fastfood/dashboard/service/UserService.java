package uz.fastfood.dashboard.service;

import uz.fastfood.dashboard.dto.request.ClientRequest;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.entity.User;
import uz.fastfood.dashboard.entity.enums.Status;

import java.util.UUID;

public interface UserService {

    User registerClient(ClientRequest request);

    ApiResponse getCustomers(String search, Integer page, Integer size, String nameSort, String orderSort, Boolean activeSort);

    ApiResponse changeUserStatus(UUID userId, Status status);

    void changeUserRole();
}

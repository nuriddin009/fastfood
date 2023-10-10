package uz.fastfood.dashboard.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.request.ClientRequest;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.entity.User;
import uz.fastfood.dashboard.entity.enums.OrderStatus;
import uz.fastfood.dashboard.entity.enums.Status;
import uz.fastfood.dashboard.filter.UserFilter;
import uz.fastfood.dashboard.mapper.UserMapper;
import uz.fastfood.dashboard.repository.UserRepository;
import uz.fastfood.dashboard.service.UserService;
import uz.fastfood.dashboard.service.UserSession;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserSession userSession;
    private final UserMapper userMapper;


    @Override
    public User registerClient(ClientRequest request) {
        User user = null;
        if (!userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            user = userRepository.save(User.builder()
                    .phoneNumber(request.getPhoneNumber())
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .orderVolume(request.getOrderVolume())
                    .build());
        } else {
            throw new EntityExistsException("Phone number already exist");
        }

        return user;
    }

    @Override
    public ApiResponse getCustomers(UserFilter filter) {
        return new ApiResponse(true, userRepository.findAllByFilter(filter).map(userMapper::toDto));
    }


    @Override
    public ApiResponse changeUserStatus(UUID userId, Status status) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with " + userId));
        user.setStatus(status);
        userRepository.save(user);
        return new ApiResponse(true, "User updated");
    }

    @Override
    public void changeUserRole() {

    }


}

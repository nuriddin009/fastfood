package uz.fastfood.dashboard.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.request.ClientRequest;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.entity.User;
import uz.fastfood.dashboard.repository.UserRepository;
import uz.fastfood.dashboard.service.UserService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private EntityManager entityManager;


    @Override
    public User registerClient(ClientRequest request) {

        User build = User.builder()
                .phoneNumber(request.getPhoneNumber())
                .firstname(request.getFirstname())
                .lastname(request.getLastname()).build();


        return null;
    }

    @Override
    public ApiResponse getCustomers(String search, Integer page, Integer size, String nameSort, String orderSort, Boolean activeSort) {



        return null;
    }
}

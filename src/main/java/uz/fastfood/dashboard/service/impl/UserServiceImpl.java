package uz.fastfood.dashboard.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.request.ClientRequest;
import uz.fastfood.dashboard.entity.User;
import uz.fastfood.dashboard.repository.UserRepository;
import uz.fastfood.dashboard.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User registerClient(ClientRequest request) {


        User build = User.builder()
                .phoneNumber(request.getPhoneNumber())
                .firstname(request.getFirstname())
                .lastname(request.getLastname()).build();


        return null;
    }
}

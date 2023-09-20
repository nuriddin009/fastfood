package uz.fastfood.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.fastfood.dashboard.dto.request.OrderRequest;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderController {


    private final OrderService service;

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<BaseResponse<?>> makeOrder(@RequestBody @Valid OrderRequest request) {
        BaseResponse<?> response = new BaseResponse<>();
        response = service.makeOrder(request, response);
        HttpStatus status = response.getError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.ACCEPTED;
        return new ResponseEntity<>(response, status);
    }
}

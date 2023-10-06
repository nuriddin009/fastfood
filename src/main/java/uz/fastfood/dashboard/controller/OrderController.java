package uz.fastfood.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.fastfood.dashboard.dto.request.OrderRequest;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.entity.enums.OrderStatus;
import uz.fastfood.dashboard.filter.OrderFilter;
import uz.fastfood.dashboard.service.OrderService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService service;


    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_CURRIER', 'ROLE_OPERATOR','ROLE_SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<BaseResponse<?>> makeOrder(@RequestBody @Valid OrderRequest request) {
        BaseResponse<?> response = new BaseResponse<>();
        response = service.makeOrder(request, response);
        HttpStatus status = response.getError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.ACCEPTED;
        return new ResponseEntity<>(response, status);
    }

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @PostMapping("by_admin")
    public ResponseEntity<BaseResponse<?>> makeOrderByAdmin(@RequestBody @Valid OrderRequest request) {
        BaseResponse<?> response = new BaseResponse<>();
        response = service.makeOrderByAdmin(request, response);
        HttpStatus status = response.getError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.ACCEPTED;
        return new ResponseEntity<>(response, status);
    }

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @PatchMapping(value = "update")
    public ResponseEntity<BaseResponse<?>> changeStatus(
            @RequestParam(name = "orderId") UUID orderId,
            @RequestParam(name = "status") OrderStatus orderStatus
    ) {
        BaseResponse<?> response = new BaseResponse<>();
        response = service.changeStatusOrder(orderId, orderStatus, response);
        HttpStatus status = response.getError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK;
        return new ResponseEntity<>(response, status);
    }

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @GetMapping("/byColumns")
    public ResponseEntity<ApiResponse> getOrdersByColumns(
            @RequestParam(defaultValue = "PENDING") OrderStatus status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        return ResponseEntity.ok(service.getOrders(status, page, size));
    }


    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @GetMapping("/byRow")
    public ResponseEntity<ApiResponse> getOrdersByRows(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        return ResponseEntity.ok(service.getOrdersV2(page, size));
    }


    @GetMapping("detail")
    public ResponseEntity<?> orderDetails(@RequestParam UUID orderId) {
        return ResponseEntity.ok(service.orderDetail(orderId));
    }

    //    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam UUID orderId) {
        return ResponseEntity.ok(service.deleteOrder(orderId));
    }


}

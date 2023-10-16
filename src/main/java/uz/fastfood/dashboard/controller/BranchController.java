package uz.fastfood.dashboard.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.fastfood.dashboard.dto.request.BranchDto;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.entity.Branch;
import uz.fastfood.dashboard.service.BranchService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/branch")
public class BranchController {

    private final BranchService branchService;


//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse> getBranches(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(branchService.getBranches(search, page, size));
    }

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteBranch(
            @RequestParam UUID branchId
    ) {
        return ResponseEntity.ok(branchService.delete(branchId));
    }

    //    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<BaseResponse<?>> createBranch(
            @RequestBody @Valid BranchDto request
    ) {
        BaseResponse<Branch> response = new BaseResponse<>();
        response = branchService.createBranch(request, response);
        HttpStatus status = response.getError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.CREATED;
        return new ResponseEntity<>(response, status);
    }

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @PutMapping(value = "update")
    public ResponseEntity<ApiResponse> updateBranch(
            @RequestBody @Valid BranchDto request
    ) {
        return ResponseEntity.ok(branchService.updateBranch(request));
    }
}

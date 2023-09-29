package uz.fastfood.dashboard.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/currier")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN','ROLE_CURRIER')")
public class CurrierController {


    


}

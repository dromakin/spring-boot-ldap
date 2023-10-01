package com.dromakin.springbootldap.controllers;

import com.dromakin.springbootldap.config.SwaggerConfig;
import com.dromakin.springbootldap.dto.PrivateJsonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    @Operation(
            summary = "Get private json from private/secured endpoint",
            security = {@SecurityRequirement(name = SwaggerConfig.AUTH_SECURITY_SCHEME)})
    @GetMapping("/private")
    public PrivateJsonDTO getPrivateString(Principal principal) {
        return PrivateJsonDTO.builder().privateMsg(principal.getName() + ", it is private.").build();
    }

}

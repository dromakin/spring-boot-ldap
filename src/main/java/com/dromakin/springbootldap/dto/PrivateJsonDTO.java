/*
 * File:     PrivateJsonDTO
 * Package:  com.dromakin.springbootldap.dto
 * Project:  spring-boot-ldap
 *
 * Created by dromakin as 12.09.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.09.12
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.springbootldap.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class PrivateJsonDTO {
    @NotBlank
    String privateMsg;
}

/*
 * File:     GroupDTO
 * Package:  com.dromakin.springbootldap.dto
 * Project:  spring-boot-ldap
 *
 * Created by dromakin as 26.09.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.09.26
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.springbootldap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupDTO {
    private String name;
    private List<String> members;
    private String permissions;
}

package com.mateus.reviews.dtos;

import com.mateus.reviews.domain.Role;

public class RoleDTO {
    private Long id;
    private String authority;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public RoleDTO(Role role) {
        id = role.getId();
        authority = role.getAuthority();
    }

    public String getAuthority() {
        return authority;
    }

    public Long getId() {
        return id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

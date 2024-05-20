package com.example.demoapp.Models.Dto.Response;

import java.util.Set;

public class RoleResponse {
    private String name;
    private String description;
    private Set<PermissionResponse> permissions;

    public RoleResponse(String name, String description, Set<PermissionResponse> permissions) {
        this.name = name;
        this.description = description;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PermissionResponse> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionResponse> permissions) {
        this.permissions = permissions;
    }
}

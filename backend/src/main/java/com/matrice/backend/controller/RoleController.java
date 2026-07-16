package com.matrice.backend.controller;

import com.matrice.backend.entity.Role;
import com.matrice.backend.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Role> getRoles() {
        return service.getAllRoles();
    }

    @PostMapping
    public Role addRole(@RequestBody Role role) {
        return service.save(role);
    }
}
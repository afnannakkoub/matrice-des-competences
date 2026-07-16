package com.matrice.backend.service;
import com.matrice.backend.entity.Role;
import com.matrice.backend.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class RoleService {
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> getAllRoles() {
        return repository.findAll();
    }

    public Role save(Role role) {
        return repository.save(role);
    }

}

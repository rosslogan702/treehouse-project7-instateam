package com.rosslogan.instateam.dao;

import com.rosslogan.instateam.model.Role;

import java.util.List;

/**
 * Created by Ross on 20/02/2018.
 */
public interface RoleDao {
    List<Role> findAll();
    Role findById(Long id);
    void save(Role role);
    void delete(Role role);
}

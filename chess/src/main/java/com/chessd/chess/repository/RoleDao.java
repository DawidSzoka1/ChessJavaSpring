package com.chessd.chess.repository;

import com.chessd.chess.entity.Role;

public interface RoleDao {
    public Role findByRole(String role);
}

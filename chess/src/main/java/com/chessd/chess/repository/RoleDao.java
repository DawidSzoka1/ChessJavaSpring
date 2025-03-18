package com.chessd.chess.repository;

import com.chessd.chess.entity.Role;

public interface RoleDao {
    Role findByName(String role);
}

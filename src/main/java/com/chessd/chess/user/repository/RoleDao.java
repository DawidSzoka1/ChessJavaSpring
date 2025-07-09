package com.chessd.chess.user.repository;

import com.chessd.chess.user.entity.Role;

public interface RoleDao {
    Role findByName(String role);
}

package com.talentica.ecommerce.product_order.repository;

import com.talentica.ecommerce.product_order.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}

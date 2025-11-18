package com.suresh.ecommerce.app.user;

import com.suresh.ecommerce.commons.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String name;
}

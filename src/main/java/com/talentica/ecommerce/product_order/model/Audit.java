package com.talentica.ecommerce.product_order.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@Audited(withModifiedFlag = true, targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public abstract class Audit {

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @CreatedBy
  @Column(name = "created_by", updatable = false)
  private Integer createdBy;

  @LastModifiedBy
  @Column(name = "updated_by")
  private Integer updatedBy;
}

package com.talentica.ecommerce.product_order.model;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity extends Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	protected boolean deleted;

	@Version
	private Integer version;

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof BaseEntity)) {
			return false;
		}

		final BaseEntity other = (BaseEntity) o;

		return id != null && id.equals(other.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}

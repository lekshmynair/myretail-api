package com.myretail.api.repository;

import com.myretail.api.repository.entity.PriceEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Repoistory to for product price
 */
@Repository
public interface ProductRepository extends CassandraRepository<PriceEntity, Integer> {
}

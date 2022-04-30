package com.myretail.api.repository;

import com.myretail.api.repository.entity.Price;
import jnr.ffi.annotations.In;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CassandraRepository<Price, Integer> {


}

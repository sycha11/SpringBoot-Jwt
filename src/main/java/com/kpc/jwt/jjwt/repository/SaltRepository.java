package com.kpc.jwt.jjwt.repository;

import com.kpc.jwt.jjwt.model.entity.Salt;
import org.springframework.data.repository.CrudRepository;

public interface SaltRepository extends CrudRepository<Salt, Long> {
}

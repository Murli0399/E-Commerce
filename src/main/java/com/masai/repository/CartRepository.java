package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

}
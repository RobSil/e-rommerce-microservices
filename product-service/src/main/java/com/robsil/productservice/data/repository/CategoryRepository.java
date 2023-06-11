package com.robsil.productservice.data.repository;

import com.robsil.productservice.data.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByParentId(Long parentId);

}

package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.ColorImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<ColorImpl, Long> {
}
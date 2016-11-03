package com.ffwatl.dao.items;


import com.ffwatl.admin.entities.items.color.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {
}
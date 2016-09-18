package com.ffwatl.dao.items;


import com.ffwatl.manage.entities.items.color.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {
}
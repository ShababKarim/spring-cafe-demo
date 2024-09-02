package com.shabab.spring_cafe_demo.domain.repository;

import com.shabab.spring_cafe_demo.domain.MenuItem;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MenuItemRepository extends CrudRepository<MenuItem, Long> {

    @Query(value = "UPDATE menu_item SET quantity = :quantity WHERE id = :id")
    @Modifying
    Integer updateMenuItemQuantity(@Param("id") Long id, @Param("quantity") Long quantity);
}

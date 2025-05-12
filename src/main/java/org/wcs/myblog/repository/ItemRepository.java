package org.wcs.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wcs.myblog.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

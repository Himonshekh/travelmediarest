package com.example.travelmediarest.repository;

import com.example.travelmediarest.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
    Location findByName(String location);
}

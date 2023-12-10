package com.example.backendlab.repository;

import com.example.backendlab.model.CarLotImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarLotImagesRepository extends JpaRepository<CarLotImage, Long> {
    @Query("""
            SELECT CLI FROM CarLotImage CLI
            WHERE CLI.carLot.id = ?1
            """)
    List<CarLotImage> findCarLotImageByCarLotId(Long carLotId);

    @Modifying
    @Query("""
            DELETE FROM CarLotImage CLI
            WHERE CLI.carLot.id = ?1
            """)
    void deleteAllByCarLotId(Long id);
}

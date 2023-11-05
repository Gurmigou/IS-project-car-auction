package com.example.backendlab.service;

import com.example.backendlab.model.CarLotImage;
import com.example.backendlab.repository.CarLotImagesRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarLotImageService {
    private final CarLotImagesRepository carLotImagesRepository;

    @Autowired
    public CarLotImageService(CarLotImagesRepository carLotImagesRepository) {
        this.carLotImagesRepository = carLotImagesRepository;
    }

    @Transactional
    public List<CarLotImage> saveCarLotImages(List<MultipartFile> images) {
        var list = new ArrayList<CarLotImage>();
        for (MultipartFile image : images) {
            list.add(saveImage(image));
        }
        return list;
    }

    private CarLotImage saveImage(MultipartFile file) {
        CarLotImage carLotImage = new CarLotImage();
        try {
            carLotImage.setData(BlobProxy.generateProxy(file.getInputStream(), file.getSize()));
            return carLotImagesRepository.save(carLotImage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }
}

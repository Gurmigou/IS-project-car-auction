package com.example.backendlab.service;

import com.example.backendlab.model.CarAuction;
import com.example.backendlab.model.CarAuctionStatus;
import com.example.backendlab.model.CarLotImage;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;

public class CarCommonUtil {
    public static String getAuctionTimeLeft(LocalDateTime now,
                                            LocalDateTime auctionStart,
                                            Integer auctionDurationHours) {
        // Calculate the end time of the auction
        LocalDateTime auctionEnd = auctionStart.plusHours(auctionDurationHours);

        // Check if the auction has already ended
        if (now.isAfter(auctionEnd)) {
            return "Auction has ended";
        } else if (now.isBefore(auctionStart)) {
            // Check if the auction has not started yet
            return "Auction has not started yet";
        }

        // Calculate the duration between now and the auction end time
        Duration duration = Duration.between(now, auctionEnd);
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        return days + " days " + hours + " hours " + minutes + " minutes";
    }

    public static String getCarImagedBase64(CarLotImage image) {
        try {
            byte[] bytes = image.getData().getBytes(1, (int) image.getData().length());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean carAuctionIsNotEnded(CarAuction carAuction) {
        boolean isNotEnded = Objects.nonNull(carAuction.getIsFinished()) && !carAuction.getIsFinished() &&
                carAuction.getAuctionStart()
                        .plusHours(carAuction.getAuctionDurationHours())
                        .isAfter(LocalDateTime.now());
        if (!isNotEnded && !carAuction.getIsFinished()) {
            carAuction.setIsFinished(true);
            carAuction.setStatus(CarAuctionStatus.WAITING_FOR_APPROVAL);
        }
        return isNotEnded;
    }
}

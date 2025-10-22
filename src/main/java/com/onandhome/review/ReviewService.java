package com.onandhome.review;

import com.onandhome.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepo;

    public List<Review> getAllReviews() {
        return reviewRepo.findAll();
    }

    public Optional<Review> getReview(Long id) {
        return reviewRepo.findById(id);
    }

    public void deleteReview(Long id) {
        reviewRepo.deleteById(id);
    }

    public Review save(Review review) {
        return reviewRepo.save(review);
    }

}

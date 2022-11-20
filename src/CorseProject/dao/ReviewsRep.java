package CorseProject.dao;

import CorseProject.models.Reviews;

import java.util.List;

public interface ReviewsRep {

    void createReview (Reviews reviews);

    List<Reviews> getAllReviews();

    Reviews getReviewByClientId (int id);

}

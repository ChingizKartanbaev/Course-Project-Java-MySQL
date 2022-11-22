package CorseProject.dao.impl;

import CorseProject.dao.Const;
import CorseProject.dao.DbHelper;
import CorseProject.dao.ReviewsRep;
import CorseProject.models.Reviews;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewsRepImpl implements ReviewsRep {

    DbHelper dbHelper = DbHelper.INSTANCE;

    @Override
    public void createReview(Reviews reviews) {

        String insert = "INSERT INTO " + Const.REVIEWS_TABLE + "(" +
                Const.REVIEWS_REVIEW + "," + Const.REVIEWS_IDCLIENT + ")" + "VALUES(?,?)";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(insert);
            preparedStatement.setString(1, reviews.getReview());
            preparedStatement.setInt(2, reviews.getIdClient());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reviews> getAllReviews() {

        List<Reviews> reviewsList = new ArrayList<>();
        String select = "SELECT * FROM " + Const.REVIEWS_TABLE;

        try (PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Reviews reviews = new Reviews();
                reviews.setIdReviews(resultSet.getLong("idReviews"));
                reviews.setReview(resultSet.getNString("review"));
                reviews.setIdClient(resultSet.getInt("idClient"));
                reviewsList.add(reviews);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviewsList;
    }

    @Override
    public Reviews getReviewByClientId(int id) {

        String select = "SELECT * FROM " + Const.REVIEWS_TABLE + " WHERE " + Const.REVIEWS_IDCLIENT + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select);
            preparedStatement.setInt(1, Math.toIntExact(id));

            ResultSet resultSet = preparedStatement.executeQuery();
            Reviews reviews = new Reviews();

            while (resultSet.next()){
                reviews.setIdReviews(resultSet.getLong("idReviews"));
                reviews.setReview(resultSet.getNString("review"));
                reviews.setIdClient(resultSet.getInt("idClient"));
            }
            return reviews;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

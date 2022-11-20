package CorseProject.models;

public class Reviews {

    private long idReviews;
    private String review;
    private int idClient;

    public Reviews() {
    }

    public Reviews(String review, int idClient) {
        this.review = review;
        this.idClient = idClient;
    }

    public long getIdReviews() {
        return idReviews;
    }

    public void setIdReviews(long idReviews) {
        this.idReviews = idReviews;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
}

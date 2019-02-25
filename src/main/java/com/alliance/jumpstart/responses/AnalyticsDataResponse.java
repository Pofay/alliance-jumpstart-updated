package com.alliance.jumpstart.responses;

public class AnalyticsDataResponse {

    private long numberOfHired;
    private long numberOfMoved;
    private long numberOfCandidates;
    private long numberOfViews;

    public AnalyticsDataResponse(long numberOfViews, long numberOfCandidates, long numberOfMoved, long numberofHired) {
        this.setNumberOfViews(numberOfViews);
        this.setNumberOfCandidates(numberOfCandidates);
        this.setNumberOfMoved(numberOfMoved);
        this.setNumberOfHired(numberofHired);
    }

    /**
     * @return the numberOfHired
     */
    public long getNumberOfHired() {
        return numberOfHired;
    }

    /**
     * @param numberOfHired the numberOfHired to set
     */
    public void setNumberOfHired(long numberOfHired) {
        this.numberOfHired = numberOfHired;
    }

    /**
     * @return the numberOfMoved
     */
    public long getNumberOfMoved() {
        return numberOfMoved;
    }

    /**
     * @param numberOfMoved the numberOfMoved to set
     */
    public void setNumberOfMoved(long numberOfMoved) {
        this.numberOfMoved = numberOfMoved;
    }

    /**
     * @return the numberOfCandidates
     */
    public long getNumberOfCandidates() {
        return numberOfCandidates;
    }

    /**
     * @param numberOfCandidates the numberOfCandidates to set
     */
    public void setNumberOfCandidates(long numberOfCandidates) {
        this.numberOfCandidates = numberOfCandidates;
    }

    /**
     * @return the numberOfViews
     */
    public long getNumberOfViews() {
        return numberOfViews;
    }

    /**
     * @param numberOfViews the numberOfViews to set
     */
    public void setNumberOfViews(long numberOfViews) {
        this.numberOfViews = numberOfViews;
    }
}
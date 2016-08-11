package com.example.android.mtabustime;

/**
 * Created by Daniel on 8/3/2016.
 */
public class Bus {

    private String mLineRef;
    private String mExpectedArrivalTime;
    private String mDestinationName;
    private String mPresentableDistance;
    private String mProgressStatus;

    public Bus(String mLineRef, String mExpectedArrivalTime, String mDestinationName, String mPresentableDistance) {
        this.mLineRef = mLineRef;
        this.mExpectedArrivalTime = mExpectedArrivalTime;
        this.mDestinationName = mDestinationName;
        this.mPresentableDistance = mPresentableDistance;
    }

    public Bus(String mLineRef, String mExpectedArrivalTime, String mDestinationName, String mPresentableDistance, String mProgressStatus) {
        this.mLineRef = mLineRef;
        this.mExpectedArrivalTime = mExpectedArrivalTime;
        this.mDestinationName = mDestinationName;
        this.mPresentableDistance = mPresentableDistance;
        this.mProgressStatus = mProgressStatus;
    }

    public String getmLineRef() {
        return mLineRef;
    }

    public String getmExpectedArrivalTime() {
        return mExpectedArrivalTime;
    }

    public String getmDestinationName() {
        return mDestinationName;
    }

    public String getmPresentableDistance() {
        return mPresentableDistance;
    }

    public String getmProgressStatus() {
        return mProgressStatus;
    }
}

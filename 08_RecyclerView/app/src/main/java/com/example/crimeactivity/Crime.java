package com.example.crimeactivity;

import java.util.Date;
import java.util.UUID;

public class Crime {


    public Crime()
    {
 		mId = UUID.randomUUID();
        mDate = new Date();
    }

    public boolean isRequestPolice() {
        return mRequestPolice;
    }

    public void setRequestPolice(boolean requestPolice) {
        mRequestPolice = requestPolice;
    }

    private boolean mRequestPolice;

    private UUID mId;
    private Date mDate;


    private String mTitle;

    private boolean mSolved;


    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }



}

package com.example.criminalintent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Crime {
    public static final String DATE_FORMAT_1 = "EEEE, MMM d , yyyy";
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private Boolean mSolved;
    private Boolean mRequiresPolice;

    public Boolean getRequiresPolice() {
        return mRequiresPolice;
    }

    public void setRequiresPolice(Boolean requiresPolice) {
        mRequiresPolice = requiresPolice;
    }

    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
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

    public Boolean getSolved() {
        return mSolved;
    }

    public void setSolved(Boolean solved) {
        mSolved = solved;
    }

    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        return mDateFormat.format(mDate);
    }
}

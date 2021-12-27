package com.example.polity_correct;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String userName;
    private String password;
    private String mail;
    private Long yearOfBirth;
    private int gender;
    private UserType userType;
    private String key_pg;

    public User(String userName, String password, String mail, Long yearOfBirth, int gender, UserType userType, String pg){
        this.userName=userName;
        this.password=password;
        this.mail=mail;
        this.yearOfBirth=yearOfBirth;
        this.gender=gender;
        this.userType=userType;
        this.key_pg=pg;
    }

    protected User(Parcel in) {
        userName = in.readString();
        password = in.readString();
        mail = in.readString();
        yearOfBirth = in.readLong();
        gender= in.readInt();
        key_pg = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public Long getYearOfBirth() {
        return yearOfBirth;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setYearOfBirth(Long yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getKey_pg() {
        return key_pg;
    }

    public void setKey_pg(String key_pg) {
        this.key_pg = key_pg;
    }

    public int getGender() {
        return gender;
    }

    public void setGander(int gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(mail);
        dest.writeLong(yearOfBirth);
        dest.writeString(key_pg);
    }
}

enum UserType{
    citizen,
    parliament,
}

enum StatusVote{
    against,
    impossible,
    agreement
}
package com.example.younityin;

public class FindPeople {
    public String profileimage, fullname;
    public  FindPeople()
    {

    }
    public FindPeople(String profileimage, String fullname) {
        this.profileimage = profileimage;
        this.fullname = fullname;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}



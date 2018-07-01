package com.example.tyrone.scse_foc_2018.entity;

/**
 * Created by Tyrone on 14/2/2018.
 */

public class Member {

    //  Member details
    private String email, name, avatar, role;
    private int mobileNo;

    public Member() {}

    public Member ( String name, String email, String role, int mobileNo, String avatar ) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.mobileNo = mobileNo;
        this.avatar = avatar;
    }

    //  Get Variables
    public String getName() { return this.name; }
    public String getEmail() { return this.email; }
    public String getRole() { return this.role; }
    public String getAvatar() { return this.avatar; }
    public int getMobileNo() { return this.mobileNo; }

    //  Set Variable
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.role = role; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public void setMobileNo(int mobileNo) { this.mobileNo = mobileNo; }


}

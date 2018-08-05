package com.example.tyrone.scse_foc_2018.entity;

/**
 * Created by Tyrone on 14/2/2018.
 */

public class Member {

    //  Member details
    private String email, group, name, role;
    private int mobileNo;

    public Member() {
        role = "Freshmen";
    }

    public Member ( String name, String email, String role, int mobileNo, String group ) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.mobileNo = mobileNo;
        this.group = group;
    }

    //  Get Variables
    public String getName() { return this.name; }
    public String getEmail() { return this.email; }
    public String getRole() { return this.role; }
    public String getGroup() { return this.group; }
    public int getMobileNo() { return this.mobileNo; }

    //  Set Variable
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
    public void setAvatar(String avatar) { this.group = group; }
    public void setMobileNo(int mobileNo) { this.mobileNo = mobileNo; }


}

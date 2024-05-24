package com.virtual_bank_g66.demo;

public class UserInfoBean {
    // User's info which will not change during a session
    private static UserInfoBean instance;
    private String userName;
    private String Password;
    private String role;
    private String Mail;
    private String ID;
    private String Associated_child;
    private String Associated_ID;


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getRole() {
        return role;
    }

    public String getMail() {
        return Mail;
    }

    public String getID() {
        return ID;
    }

    public void setID(String id) {
        this.ID = id;
    }

    public String getAssociated_ID() {
        return Associated_ID;
    }

    public void setAssociated_ID(String associated_ID) {
        Associated_ID = associated_ID;
    }

    public String getAssociated_child() {
        return Associated_child;
    }

    public void setAssociated_child(String associated_child) {
        Associated_child = associated_child;
    }

    private UserInfoBean(String userName, String role, String id, String password, String mail, String associated_child, String associated_ID) {
        this.userName = userName;
        this.role = role;
        this.ID = id;
        this.Password = password;
        this.Mail = mail;
        this.Associated_child = associated_child;
        this.Associated_ID = associated_ID;
    }

    public static UserInfoBean getInstance(String userName, String role, String id, String password, String mail, String associated_child, String associated_ID) {
        if (instance == null) {
            instance = new UserInfoBean(userName, role, id, password, mail, associated_child, associated_ID);
        }
        return instance;
    }

    public static UserInfoBean getInstance() {
        return instance;
    }

    public static void cleanUserSession() {
        instance = null;
    }

    public void updateSessionDetails(String userName, String password, String email) {
        if (userName != null && !userName.isEmpty())
            this.userName = userName;
        if (password != null && !password.isEmpty())
            this.Password = password;
        if (email != null && !email.isEmpty())
            this.Mail = email;
    }
}




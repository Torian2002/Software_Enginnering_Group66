package com.virtual_bank_g66.demo;

/**
 * The UserInfoBean class holds the user information that remains constant during a session.
 * This class follows the Singleton pattern to ensure a single instance during the session.
 *
 * @version 5.0 May 25th, 2024
 * @author Jiabo Tong
 * @author Zonghuan Han
 */
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


    /**
     * Returns the user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the password.
     *
     * @return the password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * Sets a new password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.Password = password;
    }

    /**
     * Returns the user role.
     *
     * @return the user role
     */
    public String getRole() {
        return role;
    }

    /**
     * Returns the user email.
     *
     * @return the user email
     */
    public String getMail() {
        return Mail;
    }

    /**
     * Returns the user ID.
     *
     * @return the user ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets a new user ID.
     *
     * @param id the new user ID
     */
    public void setID(String id) {
        this.ID = id;
    }

    /**
     * Returns the associated child's ID.
     *
     * @return the associated child's ID
     */
    public String getAssociated_ID() {
        return Associated_ID;
    }

    /**
     * Sets a new associated child's ID.
     *
     * @param associated_ID the new associated child's ID
     */
    public void setAssociated_ID(String associated_ID) {
        Associated_ID = associated_ID;
    }

    /**
     * Returns the associated child's name.
     *
     * @return the associated child's name
     */
    public String getAssociated_child() {
        return Associated_child;
    }

    /**
     * Sets a new associated child's name.
     *
     * @param associated_child the new associated child's name
     */
    public void setAssociated_child(String associated_child) {
        Associated_child = associated_child;
    }

    /**
     * Private constructor to prevent instantiation from outside the class.
     *
     * @param userName the user's name
     * @param role the user's role
     * @param id the user's ID
     * @param password the user's password
     * @param mail the user's email
     * @param associated_child the associated child's name
     * @param associated_ID the associated child's ID
     */
    private UserInfoBean(String userName, String role, String id, String password, String mail, String associated_child, String associated_ID) {
        this.userName = userName;
        this.role = role;
        this.ID = id;
        this.Password = password;
        this.Mail = mail;
        this.Associated_child = associated_child;
        this.Associated_ID = associated_ID;
    }

    /**
     * Returns the singleton instance of UserInfoBean, creating it if necessary.
     *
     * @param userName the user's name
     * @param role the user's role
     * @param id the user's ID
     * @param password the user's password
     * @param mail the user's email
     * @param associated_child the associated child's name
     * @param associated_ID the associated child's ID
     * @return the singleton instance of UserInfoBean
     */
    public static UserInfoBean getInstance(String userName, String role, String id, String password, String mail, String associated_child, String associated_ID) {
        if (instance == null) {
            instance = new UserInfoBean(userName, role, id, password, mail, associated_child, associated_ID);
        }
        return instance;
    }

    /**
     * Returns the existing singleton instance of UserInfoBean.
     *
     * @return the singleton instance of UserInfoBean
     */
    public static UserInfoBean getInstance() {
        return instance;
    }

    /**
     * Returns the existing singleton instance of UserInfoBean.
     *
     * @return the singleton instance of UserInfoBean
     */
    public static void cleanUserSession() {
        instance = null;
    }

    /**
     * Updates the session details with new information.
     *
     * @param userName the new user name
     * @param password the new password
     * @param email the new email
     */
    public void updateSessionDetails(String userName, String password, String email) {
        if (userName != null && !userName.isEmpty())
            this.userName = userName;
        if (password != null && !password.isEmpty())
            this.Password = password;
        if (email != null && !email.isEmpty())
            this.Mail = email;
    }
}




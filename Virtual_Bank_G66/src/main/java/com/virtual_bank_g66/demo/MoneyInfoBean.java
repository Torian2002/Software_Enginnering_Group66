package com.virtual_bank_g66.demo;

import java.time.LocalDateTime;

/**
 * The MoneyInfoBean class represents the financial information of a user, including
 * their current balance, savings, goal, and related time limits.
 *
 * @version 1.0 April 10th, 2024 - original UserSessionBean including both user and money information
 * @version 2.0 April 24th, 2024 - separate money information from previous UserSessionBean (deleted)
 * @version 3.0 May 15th, 2024 - add getter and setter about new variable timeLimit introduced in iteration 3
 * @author Jiabo Tong
 * @author Zonghuan Han
 *
 * This class is used to manage and track the financial data for a user,
 * with methods to get and set the values of the various attributes.
 *
 * <ul>
 * <li>Current: the current balance of the user.</li>
 * <li>Saving: the savings balance of the user.</li>
 * <li>Goal: the financial goal of the user.</li>
 * <li>timeLimit: the time limit for achieving the goal.</li>
 * <li>initialDate: the date when the goal was set.</li>
 * <li>expireDate: the date when the goal should be achieved.</li>
 * <li>Log_day: the date of the last login.</li>
 * </ul>
 *
 */
public class MoneyInfoBean {
    // The following variables will easily been changed as users do something
    private float Current;
    private float Saving;
    private float Goal;
    private int timeLimit;
    private LocalDateTime initialDate;
    private LocalDateTime expireDate;


    private LocalDateTime Log_day;

    /**
     * Constructs a MoneyInfoBean with the specified initial values.
     *
     * @param current the current balance
     * @param saving the savings balance
     * @param goal the financial goal
     * @param timeLimit the time limit for achieving the goal
     * @param initialDate the date when the goal was set
     * @param expireDate the date when the goal should be achieved
     */
    public MoneyInfoBean(float current, float saving, float goal, int timeLimit, LocalDateTime initialDate, LocalDateTime expireDate) {
        this.Current = current;
        this.Saving = saving;
        this.Goal = goal;
        this.timeLimit = timeLimit;
        this.initialDate = initialDate;
        this.expireDate = expireDate;
    }

    /**
     * Gets the date of the last login.
     *
     * @return the date of the last login
     */
    public LocalDateTime getLog_day() {
        return Log_day;
    }

    /**
     * Sets the date of the last login.
     *
     * @param log_day the date of the last login
     */
    public void setLog_day(LocalDateTime log_day) {
        Log_day = log_day;
    }

    /**
     * Gets the current balance.
     *
     * @return the current balance
     */
    public float getCurrent() {
        return Current;
    }

    /**
     * Sets the current balance.
     *
     * @param current the current balance
     */
    public void setCurrent(float current) {
        this.Current = current;
    }

    /**
     * Gets the savings balance.
     *
     * @return the savings balance
     */
    public float getSaving() {
        return Saving;
    }

    /**
     * Sets the savings balance.
     *
     * @param saving the savings balance
     */
    public void setSaving(float saving) {
        this.Saving = saving;
    }

    /**
     * Gets the financial goal.
     *
     * @return the financial goal
     */
    public float getGoal() {
        return Goal;
    }

    /**
     * Sets the financial goal.
     *
     * @param goal the financial goal
     */
    public void setGoal(float goal) {
        this.Goal = goal;
    }

    /**
     * Gets the time limit for achieving the goal.
     *
     * @return the time limit for achieving the goal
     */
    public int getTimeLimit() {
        return timeLimit;
    }

    /**
     * Sets the time limit for achieving the goal.
     *
     * @param timeLimit the time limit for achieving the goal
     */
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    /**
     * Gets the date when the goal was set.
     *
     * @return the date when the goal was set
     */
    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    /**
     * Sets the date when the goal was set.
     *
     * @param initialDate the date when the goal was set
     */
    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    /**
     * Gets the date when the goal should be achieved.
     *
     * @return the date when the goal should be achieved
     */
    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    /**
     * Sets the date when the goal should be achieved.
     *
     * @param expireDate the date when the goal should be achieved
     */
    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }
}

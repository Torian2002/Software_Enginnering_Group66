package com.virtual_bank_g66.demo;

import java.time.LocalDateTime;

public class MoneyInfoBean {
    // The following variables will easily been changed as users do something
    private float Current;
    private float Saving;
    private float Goal;
    private int timeLimit;
    private LocalDateTime initialDate;
    private LocalDateTime expireDate;



    private LocalDateTime Log_day;

    public MoneyInfoBean(float current, float saving, float goal, int timeLimit, LocalDateTime initialDate, LocalDateTime expireDate) {
        this.Current = current;
        this.Saving = saving;
        this.Goal = goal;
        this.timeLimit = timeLimit;
        this.initialDate = initialDate;
        this.expireDate = expireDate;
    }

    public LocalDateTime getLog_day() {
        return Log_day;
    }

    public void setLog_day(LocalDateTime log_day) {
        Log_day = log_day;
    }

    public float getCurrent() {
        return Current;
    }

    public void setCurrent(float current) {
        this.Current = current;
    }

    public float getSaving() {
        return Saving;
    }

    public void setSaving(float saving) {
        this.Saving = saving;
    }

    public float getGoal() {
        return Goal;
    }

    public void setGoal(float goal) {
        this.Goal = goal;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }
}

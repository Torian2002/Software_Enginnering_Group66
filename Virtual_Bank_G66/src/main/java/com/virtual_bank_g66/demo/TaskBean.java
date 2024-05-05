package com.virtual_bank_g66.demo;

public class TaskBean {
    private String ID;
    private String taskNumber;
    private String taskContent;
    private String reward;
    private String state;


    public TaskBean(String ID, String taskNumber,String taskContent, String reward, String state) {
        this.ID = ID;
        this.taskNumber = taskNumber;
        this.taskContent = taskContent;
        this.reward = reward;
        this.state = state;
    }

    // Getters and Setters




    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

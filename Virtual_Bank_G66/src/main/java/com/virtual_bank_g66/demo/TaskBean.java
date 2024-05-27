package com.virtual_bank_g66.demo;

/**
 * The TaskBean class represents a task with its associated properties
 * such as ID, task number, task content, reward, and state.
 * This class provides getters and setters to access and modify these properties.
 *
 * @version 5.0 May 25th, 2024
 * @author Jiabo Tong
 * @author Wantong Zhao
 */
public class TaskBean {
    private String ID;
    private String taskNumber;
    private String taskContent;
    private String reward;
    private String state;

    /**
     * Constructs a TaskBean with the specified details.
     *
     * @param ID the ID of the task
     * @param taskNumber the task number
     * @param taskContent the content or description of the task
     * @param reward the reward for completing the task
     * @param state the current state of the task
     */
    public TaskBean(String ID, String taskNumber,String taskContent, String reward, String state) {
        this.ID = ID;
        this.taskNumber = taskNumber;
        this.taskContent = taskContent;
        this.reward = reward;
        this.state = state;
    }

    // Getters and Setters

    /**
     * Returns the ID of the task.
     *
     * @return the task ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID of the task.
     *
     * @param ID the new task ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Returns the content or description of the task.
     *
     * @return the task content
     */
    public String getTaskContent() {
        return taskContent;
    }

    /**
     * Sets the content or description of the task.
     *
     * @param taskContent the new task content
     */
    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    /**
     * Returns the task number.
     *
     * @return the task number
     */
    public String getTaskNumber() {
        return taskNumber;
    }

    /**
     * Sets the task number.
     *
     * @param taskNumber the new task number
     */
    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Returns the reward for completing the task.
     *
     * @return the task reward
     */
    public String getReward() {
        return reward;
    }

    /**
     * Sets the reward for completing the task.
     *
     * @param reward the new task reward
     */
    public void setReward(String reward) {
        this.reward = reward;
    }

    /**
     * Returns the current state of the task.
     *
     * @return the task state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the current state of the task.
     *
     * @param state the new task state
     */
    public void setState(String state) {
        this.state = state;
    }
}

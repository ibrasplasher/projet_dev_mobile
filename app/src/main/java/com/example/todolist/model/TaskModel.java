package com.example.todolist.model;

public class TaskModel {
    String taskId,taskstatus,taskName,taskContent;

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public TaskModel(String taskId, String taskstatus, String taskName,String taskContent) {
        this.taskId = taskId;
        this.taskstatus = taskstatus;
        this.taskName = taskName;
        this.taskContent = taskContent;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskstatus() {
        return taskstatus;
    }

    public void setTaskstatus(String taskstatus) {
        this.taskstatus = taskstatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

}

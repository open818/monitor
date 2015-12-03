package com.sumi.monitor.object;

import java.util.Date;

/**
 * Created by kery on 2015/12/3.
 */
public class Job {
    private int id;
    private String name;
    private String desc;
    private String filePath;
    private String cron;
    private int created_uid;
    private String created_time;
    private int update_uid;
    private String update_time;
    private String lastLog_time;
    private int lastLog_state;
    private String active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public int getCreated_uid() {
        return created_uid;
    }

    public void setCreated_uid(int created_uid) {
        this.created_uid = created_uid;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public int getUpdate_uid() {
        return update_uid;
    }

    public void setUpdate_uid(int update_uid) {
        this.update_uid = update_uid;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getLastLog_time() {
        return lastLog_time;
    }

    public void setLastLog_time(String lastLog_time) {
        this.lastLog_time = lastLog_time;
    }

    public int getLastLog_state() {
        return lastLog_state;
    }

    public void setLastLog_state(int lastLog_state) {
        this.lastLog_state = lastLog_state;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}

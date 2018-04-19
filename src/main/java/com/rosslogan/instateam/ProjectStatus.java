package com.rosslogan.instateam;

public enum ProjectStatus {
    ACTIVE("Active"),
    ARCHIVED("Archived"),
    NOT_STARTED("Not Started");

    private final String name;

    ProjectStatus(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}

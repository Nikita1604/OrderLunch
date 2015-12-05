package com.nikita.pischik.orderlunch.model;

public enum UserRoleType {

    USER("USER"),
    ADMIN("ADMIN");

    String userRoleType;

    private UserRoleType(String userRoleType) {
        this.userRoleType = userRoleType;
    }

    public String getUserRoleType() {
        return userRoleType;
    }
}

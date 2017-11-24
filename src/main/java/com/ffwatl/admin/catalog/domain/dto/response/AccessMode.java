package com.ffwatl.admin.catalog.domain.dto.response;

/**
 * @author mmed 11/24/17
 */
public enum AccessMode {

    ALL(0), ADMIN_ONLY(100), CUSTOMER(1000);

    private final int role;

    AccessMode(int role) {

        this.role = role;
    }

    public int getRole() {
        return role;
    }

}
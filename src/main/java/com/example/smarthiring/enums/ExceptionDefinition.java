package com.example.smarthiring.common.enums;

import lombok.Getter;

@Getter
public enum ExceptionDefinition {
    AWARD_NOT_FOUND(2001, "Award could not be found."),
    CREATE_AWARD_FAIL(2002, "Failed to create award."),
    UPDATE_AWARD_FAIL(2003, "Failed to update award."),
    DELETE_AWARD_FAIL(2004, "Failed to delete award."),
    AWARD_ALREADY_EXIST(2005, "Award already exist."),
    POSITION_NOT_FOUND(2006, "Position could not be found."),
    CREATE_POSITION_FAIL(2007, "Failed to create position."),
    UPDATE_POSITION_FAIL(2008, "Failed to update position."),
    DELETE_POSITION_FAIL(2009, "Failed to delete position."),
    POSITION_ALREADY_EXIST(2010, "Position already exist."),
    CAPABILITY_NOT_FOUND(2011, "Capability could not be found."),
    CREATE_CAPABILITY_FAIL(2012, "Failed to create capability."),
    UPDATE_CAPABILITY_FAIL(2013, "Failed to update capability."),
    DELETE_CAPABILITY_FAIL(2014, "Failed to delete capability."),
    CAPABILITY_ALREADY_EXIST(2015, "Capability already exist."),
    LOCATION_NOT_FOUND(2016, "Location could not be found."),
    CREATE_LOCATION_FAIL(2017, "Failed to create location."),
    USER_NOT_FOUND(2021, "User could not be found."),
    DIVISION_NOT_FOUND(2026, "Division could not be found"),
    PROFILE_NOT_FOUND(2031, "Profile could not be found."),
    CREATE_PROFILE_FAIL(2032, "Failed to create profile."),
    UPDATE_PROFILE_FAIL(2033, "Failed to update profile."),
    DELETE_PROFILE_FAIL(2034, "Failed to delete profile."),
    PROFILE_ALREADY_EXIST(2035, "Profile already exist."),
    CREATE_PROFILE_AWARD_FAIL(2036, "Failed to create award for profile."),
    CREATE_PROFILE_POSITION_FAIL(2037, "Failed to create position for profile."),
    CREATE_PROFILE_CAPABILITY_FAIL(2038, "Failed to create capability for profile."),
    CREATE_PROFILE_LOCATION_FAIL(2038, "Failed to create position for profile."),
    CREATE_PROFILE_DIVISION_FAIL(2039, "Failed to create division for profile."),
    CREATE_PROFILE_CERTIFICATE_FAIL(2040, "Failed to create certificate for profile."),
    CREATE_PROFILE_PRODUCT_OWNER_FAIL(2041, "Failed to create product owner for profile."),
    PROJECT_MATCHING_NOT_FOUND(2042, "Failed to find project_matching for delete project_matching");

    private int errorCode;
    private String message;

    ExceptionDefinition(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}

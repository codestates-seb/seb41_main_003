package com.mainproject.server.constant;

import lombok.Getter;

public enum ErrorCode {
    USER_NOT_FOUND(404, "USER NOT FOUND"),
    USER_TYPE_NOT_NONE(400, "PLEASE SET USER TYPE (NOW : NONE)"),
    USER_EMAIL_EXISTS(409, "USER EMAIL EXISTS"),
    USER_INACTIVE(403, "INACTIVE USER"),
    MESSAGE_ROOM_EXISTS(409, "MESSAGE ROOM EXISTS"),
    PHONE_NUMBER_EXISTS(409, "USER PHONE NUMBER EXISTS"),
    MATCHING_REQUEST_EXISTS(409, "MATCHING REQUSET EXISTS"),
    PROFILE_NOT_FOUND(404, "PROFILE NOT FOUND"),
    SUBJECT_NOT_FOUND(404, "SUBJECT NOT FOUND"),
    SUBJECT_NOT_NULL(404, "SUBJECT NOT NULL"),
    USER_STATUS_NOT_NULL(404, "USER STATUS NOT NULL"),
    USER_ID_NOT_NULL(404, "USER ID NOT NULL"),
    HOMEWORK_STATUS_NOT_NULL(404, "HOMEWORK STATUS NOT NULL"),
    NOT_FOUND(404, "NOT FOUND"),
    REVIEW_NOT_FOUND(404, "REVIEW NOT FOUND"),
    TUTORING_STATUS_NOT_WAIT_FINISH(404, "TUTORING STATUS NOT WAIT FINISH"),
    EXCEEDED_MAXIMUM_PROFILE_COUNT(400, "Up to 4 profiles can be created"),
    FILE_NOT_NULL(404, "FILE NOT NULL"),
    UNAUTHORIZED_ACCESS(401, "UNAUTHORIZED ACCESS"),
    ACCESS_DENIED(403, "ACCESS DENIED"),
    WRONG_SECOND_PASSWORD(403, "WRONG SECOND PASSWORD"),
    WRONG_SORT_PROPERTY(404, "WRONG SORT PROPERTY"),
    WRONG_STATUS_PROPERTY(404, "WRONG SORT PROPERTY"),
    WRONG_TUTOR_AND_TUTEE(400, "BAD REQUEST TUTOR AND TUTEE"),
    NOT_NULL_WRONG_PROFILE_STATUS_PROPERTY(404, "PROFILE STATUS WRONG OR NOT NULL."),
    NOT_CHANGE_USER_STATUS(400, "NOT CHANGE USER STATUS : Already Registered UserStatus"),
    NOT_CHANGE_WANTED_STATUS(400, "NOT CHANGE WANTED STATUS : Basic profile needs updating"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR"),
    DATA_ACCESS_ERROR(500, "Data Access Error"),
    FILE_CONVERT_ERROR(500, "MultipartFile -> File 전환 실패"),
    NOT_IMPLEMENTED(501,"NOT IMPLEMENTED"),
    EXPIRED_ACCESS_TOKEN(403, "EXPIRED ACCESS TOKEN"),
    TOKEN_NOT_NULL(404, "TOKEN_NOT_NULL"),
    EXPIRED_REFRESH_TOKEN(401,"EXPIRED REFRESH TOKEN"),
    OAUTH2_ACCESS_ERROR(400, "OAUTH2 ACCESS ERROR"),
    ARGUMENT_MISMATCH_BAD_REQUEST(400, "Failed to convert value of type Input Resource"),
    BAD_REQUEST(400, "BAD REQUEST"),
    ALARM_CHECK_BAD_REQUEST(400, "ALARM CHECK BAD REQUEST"),
    PROGRESS_TUTORING_BAD_REQUEST(400, "TUTORING IS PROGRESS - BAD REQUEST"),
    TUTORING_STATUS_BAD_REQUEST(400, "TUTORING STATUS BAD REQUEST");

    @Getter
    private int status;

    @Getter
    private String message;

    ErrorCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}

package com.sulee.lms.member.exception;

public class MemberStopUser extends RuntimeException {
    public MemberStopUser(String error) {
        super(error);
    }
}

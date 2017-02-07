package com.DI4MONDTOOL.exceptions;

public class APIThrottleException extends HypixelAPIException {
    public APIThrottleException() {
        super("You have passed the API throttle limit!");
    }
}

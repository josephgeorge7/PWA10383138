package com.babiescry.babycry;

public interface NetworkCallback {
    void processJSONString(String jsonString, String endpoint);
    void dataFetchStarted(String endpoint);
}

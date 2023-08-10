package com.familyGathering.familyGathering.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RequestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long requestId;
    public Long requestMemberId;
    public Long requestFamilyId;
    public boolean requestActive;

    public String status;

    public RequestModel() {
    }

    public RequestModel(Long requestId, Long requestMemberId, Long requestFamilyId, boolean requestActive) {
        this.requestId = requestId;
        this.requestMemberId = requestMemberId;
        this.requestFamilyId = requestFamilyId;
        this.requestActive = requestActive;
        this.status = "Pending";
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRequestId() {
        return this.requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getRequestMemberId() {
        return this.requestMemberId;
    }

    public void setRequestMemberId(Long requestMemberId) {
        this.requestMemberId = requestMemberId;
    }

    public Long getRequestFamilyId() {
        return this.requestFamilyId;
    }

    public void setRequestFamilyId(Long requestFamilyId) {
        this.requestFamilyId = requestFamilyId;
    }

    public boolean isRequestActive() {
        return this.requestActive;
    }

    public void setRequestActive(boolean requestActive) {
        this.requestActive = requestActive;
    }

    @Override
    public String toString() {
        return "RequestModel{" +
                "requestId=" + requestId +
                ", requestMemberId=" + requestMemberId +
                ", requestFamilyId=" + requestFamilyId +
                ", requestActive=" + requestActive +
                '}';
    }
}


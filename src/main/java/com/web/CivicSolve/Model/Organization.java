package com.web.CivicSolve.Model;

public class Organization {
    private Long organizationId;
    private String organizationName;
    private String address;
    private String contactNumber;

    public Organization() {}

    public Organization(Long organizationId, String organizationName, String address, String contactNumber) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}

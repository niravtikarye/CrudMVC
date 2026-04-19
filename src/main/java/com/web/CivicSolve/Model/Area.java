package com.web.CivicSolve.Model;

public class Area {
    private Long areaId;
    private String areaName;
    private String pincode;

    public Area() {}

    public Area(Long areaId, String areaName, String pincode) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.pincode = pincode;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}

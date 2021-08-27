package com.vtheatre.config.drm;

public enum AllowedTrackTypes {

    ALL("ALL"), SD_ONLY("SD_ONLY"), SD_HD("SD_HD"), SD_UHD1("SD_UHD1"), SD_UHD2("SD_UHD2");

    private String value;

    AllowedTrackTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}

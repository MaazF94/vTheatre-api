package com.vtheatre.data.model.drm.policy;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vtheatre.config.drm.AllowedTrackTypes;
import com.vtheatre.exception.drm.PallyConTokenException;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "persistent", "license_duration", "expire_date", "allowed_track_types" })
public class PlaybackPolicy {

    @JsonProperty("persistent")
    private Boolean persistent;

    @JsonProperty("license_duration")
    private Integer licenseDuration;

    @JsonProperty("expire_date")
    private String expireDate;

    @JsonProperty("allowed_track_types")
    private String allowedTrackTypes;

    @JsonProperty("rental_duration")
    private Integer rentalDuration;

    @JsonProperty("playback_duration")
    private Integer playbackDuration;

    public PlaybackPolicy() {
    }

    /**
     * BUILDER PATTERN VER 2.0
     */
    public PlaybackPolicy persistent(boolean persistent) {
        this.persistent = persistent;
        return this;
    }

    public PlaybackPolicy licenseDuration(int licenseDuration) {
        this.licenseDuration = licenseDuration;
        return this;
    }

    public PlaybackPolicy expireDate(String expireDate) {
        this.expireDate = expireDate;
        return this;
    }

    public PlaybackPolicy allowedTrackTypes(AllowedTrackTypes allowedTrackTypes) {
        this.allowedTrackTypes = allowedTrackTypes.getValue();
        return this;
    }

    public PlaybackPolicy rentalDuration(int rentalDuration) {
        this.rentalDuration = rentalDuration;
        return this;
    }

    public PlaybackPolicy playbackDuration(int playbackDuration) {
        this.playbackDuration = playbackDuration;
        return this;
    }

    public void check() throws PallyConTokenException {
        if (null != this.expireDate && !"".equals(this.expireDate) && !checkDates(this.expireDate)) {
            throw new PallyConTokenException("1011");
        }
    }

    private boolean checkDates(String expireDate) {
        Pattern pattern = Pattern.compile("^[0-9]{4}-[0,1][0-9]-[0-5][0-9]T[0-2][0-9]:[0-5][0-9]:[0-5][0-9]Z$");
        return pattern.matcher(expireDate).matches();
    }

}

package com.vtheatre.data.model.drm.token;

import com.vtheatre.exception.drm.PallyConTokenException;

public interface PallyConDrmToken {

    String getDrmType();

    String getSiteId();

    String getUserId();

    String getCId();

    String getPolicy();

    String getSiteKey();

    String getAccessKey();

    String toJsonString() throws PallyConTokenException;

}

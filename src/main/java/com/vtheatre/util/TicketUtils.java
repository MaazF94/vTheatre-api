package com.vtheatre.util;

import com.vtheatre.common.TicketConstants;

public class TicketUtils {

    public static StringBuilder confirmationCodeGenerator(String chargeId) {
        int codeLength = chargeId.length() - TicketConstants.CODE_LENGTH;
        StringBuilder confirmationCode = new StringBuilder(TicketConstants.CODE_LENGTH);

        for (int i = chargeId.length(); i > codeLength; i--)
            confirmationCode.append(chargeId.charAt(i - 1));
        return confirmationCode;
    }
}

package com.vtheatre.util;

import com.vtheatre.common.TicketConstants;

public class TicketUtils {

    public static String confirmationCodeGenerator(String chargeId) {
        int codeLength = chargeId.length() - 7;
        StringBuilder sb = new StringBuilder(TicketConstants.CODE_LENGTH);

        for (int i = chargeId.length(); i > codeLength; i--)
            sb.append(chargeId.charAt(i-1));
        return sb.toString().toUpperCase();
    }
}

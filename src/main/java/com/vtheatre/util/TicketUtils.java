package com.vtheatre.util;

import java.security.SecureRandom;

import com.vtheatre.common.TicketConstants;

public class TicketUtils {

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static SecureRandom rnd = new SecureRandom();

    public static StringBuilder confirmationCodeGenerator(String chargeId) {
        int codeLength = chargeId.length() - TicketConstants.CODE_LENGTH;
        StringBuilder confirmationCode = new StringBuilder(TicketConstants.CODE_LENGTH);

        for (int i = chargeId.length(); i > codeLength; i--)
            confirmationCode.append(chargeId.charAt(i - 1));
        return confirmationCode;
    }

    public static StringBuilder confirmationCodeGenerator() {
        StringBuilder sb = new StringBuilder(TicketConstants.CODE_LENGTH);
        for (int i = 0; i < TicketConstants.CODE_LENGTH; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb;
    }
}

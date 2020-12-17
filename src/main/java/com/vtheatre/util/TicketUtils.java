package com.vtheatre.util;

import java.security.SecureRandom;

public class TicketUtils {

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static SecureRandom rnd = new SecureRandom();

    public static String confirmationCodeGenerator() {
        int codeLength = 7;
        StringBuilder sb = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
    
}

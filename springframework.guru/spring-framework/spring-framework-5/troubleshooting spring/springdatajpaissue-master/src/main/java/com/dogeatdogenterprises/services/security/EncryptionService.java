package com.dogeatdogenterprises.services.security;

/**
 * Created by donaldsmallidge on 1/8/17.
 */
public interface EncryptionService {
    String encryptString(String input);
    boolean checkPassword(String plainPassword, String encryptedPassword);

}

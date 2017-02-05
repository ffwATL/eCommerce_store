package com.ffwatl.common.encryption;



public interface EncryptionModule {

    String encrypt(String plainText);

    String decrypt(String cipherText);
}
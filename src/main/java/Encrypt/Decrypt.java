package Encrypt;

import java.security.CryptoPrimitive;

public class Decrypt implements DecryptInterface {

    @Override
    public byte[] decrypt(String key, byte[] crypt) {
        byte[] decrypt = new byte[crypt.length];
        for (int i = 0; i < crypt.length; i++) {
            decrypt[i] = (byte) (crypt[i] - 1);
        }
        return decrypt;
    }
}

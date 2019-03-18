package com.rongpingkeji.common.util.cipher.password;


import com.rongpingkeji.common.util.RandomUtils;
import com.rongpingkeji.common.util.cipher.DigestUtils;
import com.rongpingkeji.common.util.cipher.Encodes;

/**
 * @version 2017-10-30.
 */
public class SHA1PasswordUtil implements IPassWordUtil {

    @Override
    public String encryptPassword(String plainPassword) {
        byte[] salt = RandomUtils.randomBytes(PasswordUtils.SALT_SIZE);
        byte[] hashPassword = DigestUtils.sha1(plainPassword.getBytes(), salt, PasswordUtils.HASH_INTERATIONS);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    @Override
    public boolean validatePassword(String plainPassword, String password) {
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = DigestUtils.sha1(plainPassword.getBytes(), salt, PasswordUtils.HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }
}

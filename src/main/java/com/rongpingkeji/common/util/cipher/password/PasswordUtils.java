package com.rongpingkeji.common.util.cipher.password;


import com.rongpingkeji.common.util.cipher.DigestUtils;

/**
 * 密码加密验证工具类
 * <p>
 * 如果以后要添加新的验证方式，只需要实现IPassWordUtil，并在这里配置即可。
 *
 * @version 2017-10-30.
 * @auth Licw
 */
public class PasswordUtils {

    public static final String SHA1 = "SHA-1";
    public static final String MD5 = "MD5";
    public static final String HASH_ALGORITHM = MD5;
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 16;

    public static IPassWordUtil getPassWordUtil() {
        if (HASH_ALGORITHM.equals(MD5)) {
            return new MD5PasswordUtil();
        } else {
            return new SHA1PasswordUtil();
        }
    }

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String encryptPassword(String plainPassword) {
        return getPassWordUtil().encryptPassword(plainPassword).toUpperCase();
    }


    /**
     * 单向编译MD5
     *
     * @param plainText
     * @return
     */
    public static String encryptMD5(String plainText) {
        return DigestUtils.md5(plainText).toUpperCase();
    }

    public static boolean checkEncrypt(String password, String plainText) {
        return password.equals(encryptMD5(plainText));
    }

    /**
     * 验证密码
     *
     * @param plainPassword 明文密码
     * @param password      密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        return getPassWordUtil().validatePassword(plainPassword, password);
    }
}

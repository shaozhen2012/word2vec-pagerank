package com.rongpingkeji.common.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 将字符串数组以逗号分隔的形式拼接起来
     *
     * @param stringList
     * @return
     */
    public static String listToString(List<String> stringList) {
        if (CollectionUtils.isEmpty(stringList)) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < stringList.size(); i++) {
            builder.append(stringList.get(i));
            if (i < stringList.size() - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    /**
     * 将字符串从逗号分隔的地方分隔为String数组
     *
     * @param string
     * @return
     */
    public static List<String> stringToList(String string) {
        if (isEmpty(string)) {
            return null;
        }

        return Arrays.asList(string.split(","));
    }

    /**
     * 将字符串从逗号分隔的地方分隔为int数组
     *
     * @param string
     * @return
     */
    public static List<Integer> stringToIntList(String string) {
        if (isEmpty(string)) {
            return null;
        }

        List<Integer> integerList = new ArrayList<>();
        String[] stringArray = string.split(",");
        for (int i = 0; i < stringArray.length; i++) {
            String str = stringArray[i];
            if (isNotEmpty(str)) {
                integerList.add(Integer.valueOf(str));
            }
        }
        return integerList;
    }

    /**
     * 对指定数字补0
     *
     * @param input 待补0数据
     * @param num   位数
     * @return
     */
    public static String fillZero(int input, int num) {
        return String.format("%0" + num + "d", input);
    }


    /**
     * 对指定数字补0
     *
     * @param input 待补0数据
     * @param num   位数
     * @return
     */
    public static String fillZero(Long input, int num) {
        return String.format("%0" + num + "d", input);
    }


    /**
     * 对Email进行模糊化处理
     *
     * @param email
     * @return
     */
    public static String fuzzyEmail(String email) {
        int index = email.indexOf("@");
        if (index < 0) {
            return email;
        }
        String prefix = email.substring(0, index);
        String suffix = email.substring(index, email.length());
        if (prefix.length() < 3) {
            return "***" + suffix;
        } else {
            return prefix.substring(0, 2) + "***" + suffix;
        }
    }

    public static boolean isEmpty(Object str) {
        return org.springframework.util.StringUtils.isEmpty(str);
    }

    /**
     * 生成订单号
     *
     * @param hex
     * @return
     */
    public static String getOrdersn(String hex) {
        StringBuilder result = new StringBuilder(DateUtils.dateToString(new Date(), DateUtils.yyyyMMddmm));
        result.append(RandomUtils.nextInt(9999));
        result.append(hex);
        return result.toString();
    }



    /**
     * 过滤微信的emoj表情
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (source == null) {
            return source;
        }
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            source = emojiMatcher.replaceAll("*");
            return source;
        }
        return source;
    }

}

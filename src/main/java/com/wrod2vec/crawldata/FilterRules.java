package com.wrod2vec.crawldata;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilterRules {


    public static boolean check(String data) {
        return checkLength(data) && checkConsistent(data);
    }


    public static boolean checkLength(String data) {
        if (data.length() <= 6) {
            return false;
        }
        return true;
    }


    /**
     * 检测相同字连续出现次数
     *
     * @param data
     * @return
     */
    public static boolean checkConsistent(String data) {
        char[] items = data.toCharArray();
        int size = data.length() - 1, startIndex = 0, maxCurrent = 1, max = 1;
        for (int i = startIndex; i < size; i++) {
            if (items[i] == items[i + 1]) {
                maxCurrent++;
                max = Math.max(max, maxCurrent);
            } else {
                startIndex++;
                i = startIndex;
                max = Math.max(max, maxCurrent);
                maxCurrent = 1;
            }

        }
        return max < 5;
    }


    public static void main(String[] args) {
        // log.info("检查结果={}",checkConsistent("你好蠢啊啊哈哈哈"));
    }


}

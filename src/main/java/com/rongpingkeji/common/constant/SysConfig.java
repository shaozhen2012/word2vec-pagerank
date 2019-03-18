package com.rongpingkeji.common.constant;

/**
 * Created by Daniel on 2018/11/1.
 */
public class SysConfig {

    public final static String COMMENT_NUM = "COMMENT_NUM";
    public final static String PUBLISH_NUM = "PUBLISH_NUM";
    public final static String ADMIRE_NUM = "ADMIRE_NUM";
    public final static String BE_ADMIRE_NUM = "BE_ADMIRE_NUM";
    public final static String SIGN_NUM = "SIGN_NUM";
    public final static String COMMENT_NUM_LIMIT = "COMMENT_NUM_LIMIT";
    public final static String PUBLISH_NUM_LIMIT = "PUBLISH_NUM_LIMIT";
    public final static String ADMIRE_NUM_LIMIT = "ADMIRE_NUM_LIMIT";
    public final static String BE_ADMIRE_NUM_LIMIT = "BE_ADMIRE_NUM_LIMIT";
    public final static String RMB_SCORE = "RMB_SCORE";
    public final static String GRASH_SCORE = "GRASH_SCORE";
    public final static String GRASH_FOR_PRIZE = "GRASH_FOR_PRIZE";


    public final static String P_TYPE_MALL = "p_mall";            //商城
    public final static String P_TYPE_GROUP = "p_group";          //团购
    public final static String P_TYPE_SECOND_KILL = "p_second";   //秒杀


    public final static int COUPON_VALID = 1;   //优惠券有效
    public final static int COUPON_USELESS = 2; //优惠券已使用
    public final static int COUPON_OUTTIME = 3; //优惠券已过期

    public final static String ORDER_ALL = "all";
    public final static int[] ORDER_ALL_STATE = {1, 2, 4, 5, 6};
    public final static int[] ORDER_ALL_SCORE_STATE = {1, 2, 3};

    public final static String ORDER_WAITPAY = "waitpay";
    public final static int[] ORDER_WAITPAY_STATE = {1};

    public final static String ORDER_EXPRESS = "express";
    public final static int[] ORDER_EXPRESS_STATE = {2, 4};

    public final static int[] ORDER_EXPRESS_SCORE_STATE = {2};
    public final static String ORDER_COMPLETE = "complete";

    public final static int[] ORDER_COMPLETE_STATE = {5, 6};
    public final static int[] ORDER_COMPLETE_SCORE_STATE = {3};

    public final static int ORDER_WAITPAY_STATE_ITEM = 1;
    public final static int ORDER_PAIED_STATE = 2;
    public final static int ORDER_CANCLE_STATE_ITEM = 3;
    public final static int ORDER_ONEXPRESS_STATE = 4;
    public final static int ORDER_RETURN_STATE_ITEM = 5;
    public final static int ORDER_COMPLETE_STATE_ITEM = 6;

    public final static int REFUND_DEAL = 1;  //退款处理中
    public final static int REFUND_DEAL_SUCCESS = 2; //退款成功
    public final static int REFUND_DEAL_FAIL = 3; //退款失败


}

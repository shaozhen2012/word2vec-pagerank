package com.wrod2vec.crawldata;


import com.rongpingkeji.common.util.ExceptionUtil;
import com.wrod2vec.crawldata.dto.CrawlResultDTO;
import com.wrod2vec.crawldata.dto.ReviewsDTO;
import com.wrod2vec.utils.FileUtil;
import com.wrod2vec.utils.MongodbUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 振 on 2018/3/15.
 */
@Slf4j
public class CrawlDianping {


    private final static String reviewsDir = "Corpus/reviews/";

    /**
     * 线上爬取评论数据(单页全部)
     *
     * @param link
     * @return
     */
    public List<ReviewsDTO> getOnePageFromOnline(String link) {

        Document document = null;
        List<ReviewsDTO> result = new ArrayList<ReviewsDTO>();
        try {
            document = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (document.getElementsByClass("review-list-header").size() == 0) {
            ExceptionUtil.BaseError("登录之后才可访问");
        }
        //获取酒店的属性的ID信息
        Element hotelInfo = document.getElementsByClass("review-list-header").get(0).getElementsByTag("a").get(0);
        String hotelId = hotelInfo.attr("href").substring(hotelInfo.attr("href").lastIndexOf("/") + 1);
        String hotelName = hotelInfo.attr("title");
        Elements elements = document.getElementsByClass("main-review");  //评论的单个板块
        if (elements.size() == 0) {
            return result;
        }
        for (Element item : elements) {
            Elements commentAbs = item.getElementsByClass("review-words Hide");
            ReviewsDTO dataItem = new ReviewsDTO();
            dataItem.setHotelID(hotelId);
            dataItem.setHotelName(hotelName);
            String content = "";
            if (commentAbs.size() > 0) {
                content = commentAbs.get(0).text();
            } else {
                content = item.getElementsByClass("review-words").get(0).text();
            }
            dataItem.setReviewContent(content);
            Element timeElement = item.getElementsByClass("misc-info").get(0).getElementsByClass("time").get(0);
            dataItem.setReviewTime(timeElement.text());
            result.add(dataItem);
            log.info("评论数据={}", dataItem);
        }
        return result;
    }


    /**
     * 解析酒店的评论列表数据(读取本地的html文件)
     *
     * @param
     */

    public List<ReviewsDTO> getCommentForHotelSinglePageLocal(String link) {

        Document document = null;
        List<ReviewsDTO> result = new ArrayList<ReviewsDTO>();
        try {
            document = Jsoup.parse(FileUtil.readFile(link));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取酒店的属性的ID信息
        Element hotelInfo = document.getElementsByClass("review-list-header").get(0).getElementsByTag("a").get(0);
        String hotelId = hotelInfo.attr("href").substring(hotelInfo.attr("href").lastIndexOf("/") + 1);
        String hotelName = hotelInfo.attr("title");
        Elements elements = document.getElementsByClass("main-review");  //评论的单个板块
        if (elements.size() == 0) {
            return result;
        }
        for (Element item : elements) {
            Elements commentAbs = item.getElementsByClass("review-words Hide");
            ReviewsDTO dataItem = new ReviewsDTO();
            dataItem.setHotelID(hotelId);
            dataItem.setHotelName(hotelName);
            String content = "";
            if (commentAbs.size() > 0) {
                content = commentAbs.get(0).text().replaceAll("收起评论"," ");
            } else {
                content = item.getElementsByClass("review-words").get(0).text().replaceAll("收起评论"," ");
            }
            dataItem.setReviewContent(content);
            Element timeElement = item.getElementsByClass("misc-info").get(0).getElementsByClass("time").get(0);
            dataItem.setReviewTime(timeElement.text());
            result.add(dataItem);
        }
        return result;
    }


    /**
     * 读取指定目录下的评论网页数据
     *
     * @param dirName
     * @return
     */
    public List<ReviewsDTO> crawlFloderReviews(String dirName) {
        List<ReviewsDTO> result = new ArrayList<>();
        File file = new File(reviewsDir + dirName);
        if (!file.isDirectory()) {
            ExceptionUtil.BaseError("评论目录不存在");
        }
        File[] files = file.listFiles();
        for (File item : files) {
            List<ReviewsDTO> reviewItem = getCommentForHotelSinglePageLocal(item.getPath());
            //log.info("评论数据={}",reviewItem);
            result.addAll(reviewItem);
        }
        return result;
    }


    /**
     * 评论数据写入数据库
     *
     * @return
     */
    public int  insertIntoDb(List<ReviewsDTO> reviews) {
        List<ReviewsDTO> data = new ArrayList<>();
        for (ReviewsDTO item : reviews) {
            if (FilterRules.check(item.getReviewContent())) {
                data.add(item);
            }
        }
        log.info("有效的评论数量={}", data.size());
        MongodbUtil.insertDocument(reviews.get(0).getHotelID(), reviews.get(0).getHotelID() + "_", reviews);
        return data.size();
    }


    /**
     * 读取集合下的评论数据
     *
     * @param collection
     * @return
     */
    public CrawlResultDTO findByCollection(String collection) {

        CrawlResultDTO result = new CrawlResultDTO();
        List<ReviewsDTO> data = MongodbUtil.findAllReviews(collection);
        result.setReviews(data);
        result.setNumber(data.size());
        return result;


    }


    /**
     * 测试获取单个酒店的评论数据
     */

    public static void main(String[] args) {

        //crawlHotelCommentForOne("/spark/File/DIanpingHtml/19113275", 10000);
        CrawlDianping crawlDianping = new CrawlDianping();
        // List<ReviewsDTO> data = crawlDianping.crawlFloderReviews("120017835");
        log.info("评论数据={}", crawlDianping.findByCollection("120017835"));

    }


}

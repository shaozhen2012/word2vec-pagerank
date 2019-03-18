package com.wrod2vec.utils;

import com.mongodb.client.*;
import com.rongpingkeji.common.util.JsonUtil;
import com.wrod2vec.crawldata.dto.ReviewsDTO;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

@Slf4j
public class MongodbUtil {

    private static String ip = "mongodb://localhost";
    private static String port = "27017";
    private static String database = "word2vec";
    public static MongoDatabase mongoDatabase = null;
    public static String hotelSectenceCollection = "hotel_sectence";

    public static MongoDatabase getConnection() {
        if (mongoDatabase == null) {
            MongoClient mongoClient = MongoClients.create(ip + ":" + port);
            MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
            return mongoDatabase;
        }
        return mongoDatabase;
    }

    /**
     * @param collectName 集合名称
     * @return
     */
    public static MongoCollection<Document> getCollection(String collectName) {
        return getConnection().getCollection(collectName);
    }


    /**
     * 批量插入数据到mongodb
     *
     * @param collectName 集合名称
     * @param prefix      key的前缀，用于区分集合
     */
    public static void insertDocument(String collectName, String prefix, List<ReviewsDTO> words) {

        //dropCollection(collectName);
        MongoCollection<Document> collection = getCollection(collectName);
        List<Document> documents = new ArrayList<Document>();
        long index = 0;
        for (ReviewsDTO word : words) {
            index++;
            word.setReviewId(prefix + index);
            Document document = new Document("key", prefix + index).append("value", JsonUtil.stringify(word));
            documents.add(document);
        }
        collection.insertMany(documents);
    }


    /**
     * drop 集合
     *
     * @param collection_name
     */

    public static void dropCollection(String collection_name) {
        MongoCollection<Document> collection = getCollection(collection_name);
        collection.drop();
    }


    /**
     * 查询筛选的值
     *
     * @param key
     * @return
     */
    public static ReviewsDTO findValue(String collection_name, String key) {
        MongoCollection<Document> collection = getCollection(collection_name);
        FindIterable<Document> result = collection.find(eq("key", key));
        MongoCursor<Document> mongoCursor = result.iterator();
        while (mongoCursor.hasNext()) {
            Document first = mongoCursor.next();
            ReviewsDTO data = JsonUtil.parse(first.getString("value"), ReviewsDTO.class);
            return data;
        }
        return null;
    }


    /**
     * 读取集合全部评论
     * @param collection_name
     * @return
     */
    public static List<ReviewsDTO> findAllReviews(String collection_name) {

        List<ReviewsDTO> data=new ArrayList<>();
        MongoCollection<Document> collection = getCollection(collection_name);
        FindIterable<Document> result = collection.find();
        MongoCursor<Document> mongoCursor = result.iterator();
        while (mongoCursor.hasNext()) {
            Document first = mongoCursor.next();
            ReviewsDTO item = JsonUtil.parse(first.getString("value"), ReviewsDTO.class);
            data.add(item);
        }
        return data;
    }


    /**
     * 读取指定集合的全部文档
     *
     * @param collection_name
     * @return
     */

    public static MongoCursor<Document> findAll(String collection_name) {
        MongoCollection<Document> collection = getCollection(collection_name);
        String value = "";
        FindIterable<Document> result = collection.find();
        MongoCursor<Document> mongoCursor = result.iterator();
        return mongoCursor;
    }


    public static void main(String[] args) {

        List<String> data = new ArrayList<String>();
        data.add("hello");
        data.add("China");
        data.add("American");
        data.add("Canada");
        data.add("Japan");
        //insertDocument(hotelWordsCollection, "word2000", data);
        //dropCollection(hotelWordsCollection);
        //testCollection(hotelWordsCollection);
        System.out.println("result=" + findValue(hotelSectenceCollection, "sectence19113275_938"));
    }


}

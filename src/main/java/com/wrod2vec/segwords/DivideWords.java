package com.wrod2vec.segwords;

import lombok.extern.slf4j.Slf4j;
import org.ansj.domain.Result;
import org.ansj.recognition.impl.StopRecognition;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class DivideWords extends Thread {


    private static String stopwordPath = "Corpus/stopwords/stopWords.txt";


    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    private String inputFile;
    private String outputFile;


    /**
     * 读取停用词信息
     */
    public StopRecognition filterStopWords() {


        List<String> strHashMap = new ArrayList<String>();
        String stopWordTable = stopwordPath;
        File f = new File(stopWordTable);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(f);
            BufferedReader StopWordFileBr = new BufferedReader(new InputStreamReader(fileInputStream));
            String stopWord = null;
            for (; (stopWord = StopWordFileBr.readLine()) != null; ) {
                strHashMap.add(stopWord);
            }
            StopWordFileBr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //读入停用词文件
        StopRecognition filter = new StopRecognition();
        filter.insertStopWords(strHashMap);
        return filter;
    }


    /**
     * 分词之后写入文件
     */
    public String[] divideWords(String content) {
        Result parse = NlpAnalysis.parse(content).recognition(filterStopWords());
        String result = parse.toStringWithOutNature(" ");
        return result.split(" ");

    }


    public static void main(String[] args) {
        DivideWords divideWords = new DivideWords();
        String[] result = divideWords.divideWords("我爱我的祖国");
        log.info("分词结果={}", Arrays.asList(result));
    }


}

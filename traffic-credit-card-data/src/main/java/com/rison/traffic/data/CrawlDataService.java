package com.rison.traffic.data;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : Rison 2021/6/18 下午3:15
 * 获取深圳通刷卡数据服务类
 */

@Slf4j
public class CrawlDataService {
    /**
     * 用户appKey 可到平台个人实名申请
     */
    private final static String APP_KEY = "";
    /**
     * url
     */
    private final static String URL = "https://opendata.sz.gov.cn/api/29200_00403601/1/service.xhtml";

    /**
     * 数据保存本地地址
     */
    private final static String LOCAL_PATH = "/home/rison/traffic/data.json";

    public static void main(String[] args) throws InterruptedException {
        SaveData();

    }

    /**
     * 保存数据到local
     * 数据总量为1337000条
     */
    public static void SaveData() throws InterruptedException {
        log.info("start crawl data ....");
        for (int i = 1; i <= 1337; i++) {
            log.info("正在爬取第【{}】页数据....", i);
            String jsonData = HttpUtil.get(URL + "?page=" + i + "&rows=1000&appKey=" + APP_KEY);
            JSONObject jsonObject = JSON.parseObject(jsonData);
            String data = jsonObject.getString("data");
            JSONArray dataArr = JSON.parseArray(data);
            dataArr.get(0);
            for (int j = 0; j < dataArr.size(); j++) {
                log.info("log => {}", dataArr.getString(j));
                FileUtil.appendUtf8String(dataArr.getString(j) + "\n", LOCAL_PATH);
            }
            log.info("进度： [" + i + " / " + "1337]...");
            log.info("第【{}】页数据保存到本地成功！", i);
            Thread.sleep(2_000);
        }
        log.info("crawl data end !");
    }

}

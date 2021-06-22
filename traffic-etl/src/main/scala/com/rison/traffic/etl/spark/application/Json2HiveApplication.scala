package com.rison.traffic.etl.spark.application

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author : Rison 2021/6/22 上午9:06
 *         源文件原封不动保存到Hive
 */
object Json2HiveApplication {
  private val LOCAL_PATH = "/home/rison/traffic/data.json"
  def main(args: Array[String]): Unit = {
    //设置spark运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName(this.getClass.getSimpleName.stripPrefix("$"))
    val spark: SparkSession = SparkSession
        .builder()
        .config(sparkConf)
        .enableHiveSupport()
        .getOrCreate()

    val dataFrame: DataFrame = spark.read.format("json").load(LOCAL_PATH)
//    dataFrame.show(10,false)







  }
}

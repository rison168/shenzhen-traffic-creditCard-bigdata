package com.rison.traffic.etl.spark.application

import java.util.Properties

import com.rison.traffic.scala.util.MyPropertiesUtil
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author : Rison 2021/6/22 上午9:06
 *         源文件原封不动保存到Hive
 */
object Json2HiveApplication {
  def main(args: Array[String]): Unit = {
    val properties: Properties = MyPropertiesUtil.load("application.properties")
    val path: String = properties.getProperty("origin.data.file.path")
    //设置spark运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName(this.getClass.getSimpleName.stripPrefix("$"))
    val spark: SparkSession = SparkSession
        .builder()
        .config(sparkConf)
        .enableHiveSupport()
        .getOrCreate()
    val dataFrame: DataFrame = spark.read.format("json").load(path)
    dataFrame.show(10,false)

    dataFrame.createOrReplaceTempView("credit_card")

    //创建Hive ods数据源表
    spark.sql(
      """
        |SHOW DATABASES
        |""".stripMargin).show(10, false)
    spark.sql(
      """
        |CREATE DATABASE IF NOT EXISTS szt
        |""".stripMargin)
    spark.sql(
      """
        |SHOW DATABASES
        |""".stripMargin).show(10, false)
    spark.sql(
      """
        |use szt
        |""".stripMargin)
    spark.sql(
      """
        |show tables
        |""".stripMargin).show()
    //创建原始表，不改动，直接加载
    val createTableSql =
      """
        |DROP TABLE IF EXISTS ods_szt_data;
        |CREATE EXTERNAL TABLE ods_szt_data(
        |deal_date String,
        |close_date String,
        |card_no String,
        |deal_value String,
        |deal_type String,
        |company_name String,
        |car_no String,
        |station String,
        |conn_mark String,
        |deal_money String,
        |equ_no String)
        |PARTITIONED BY(DAY STRING) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LOCATION '/warehouse/szt.db/ods/ods_szt_data'
        |""".stripMargin
    val createSqlArray: Array[String] = createTableSql.split(";")
    createSqlArray.foreach(
      sql => {
        spark.sql(sql)
      }
    )
    //插入数据到hive
    val insertSql =
      """
        |insert overwrite table ods_szt_data PARTITION(DAY = '2018-09-01')
        |select
        |deal_date,
        |close_date,
        |card_no,
        |deal_value,
        |deal_type,
        |company_name,
        |car_no,
        |station,
        |conn_mark,
        |deal_money,
        |equ_no
        |from credit_card
        |""".stripMargin

    spark.sql(insertSql)

    //查询保存到hive的数据
    spark.sql("select * from ods_szt_data").show(10, false)
    spark.close()

  }
}

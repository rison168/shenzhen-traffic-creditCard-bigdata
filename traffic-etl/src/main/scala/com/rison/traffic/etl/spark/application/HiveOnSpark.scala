package com.rison.traffic.etl.spark.application

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * @author : Rison 2021/6/24 上午8:04
 * Hive基本查询
 */
object HiveOnSpark {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setAppName(this.getClass.getSimpleName.stripPrefix("$")).setMaster("local[*]")
    val spark: SparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()
//    spark.sql("select * from szt.ods_szt_data").show(100, false)
//    spark.sql("desc table szt.ods_szt_data ").show()
//    spark.sql("select count(*) from szt.ods_szt_data").show()
    spark.sql(
      """
        |use szt
        |""".stripMargin)
    spark.sql("show tables").show()
    spark.sql(
      """
        |insert overwrite table ads_card_deal_day_top partition (day='2018-09-01')
        |SELECT
        |    t1.card_no,
        |    t1.deal_date_arr,
        |    t2.deal_sum,
        |    t1.company_name_arr,
        |    t1.station_arr,
        |    t1.conn_mark_arr,
        |    t3.deal_m_sum,
        |    t1.equ_no_arr,
        |    t1.`count`
        |from
        |    dws_card_record_day_wide as t1,
        |    (SELECT card_no, sum(deal_v) OVER(PARTITION BY card_no) AS deal_sum FROM dws_card_record_day_wide LATERAL VIEW explode(deal_value_arr) tmp as deal_v )t2,
        |    (SELECT card_no, sum(deal_m) OVER(PARTITION BY card_no) AS deal_m_sum FROM dws_card_record_day_wide LATERAL VIEW explode(deal_money_arr) tmp as deal_m )t3
        |
        |    WHERE t1.day='2018-09-01'  AND
        |    t1.card_no = t2.card_no AND
        |    t2.card_no = t3.card_no
        |    ORDER BY t2.deal_sum DESC
        |""".stripMargin)
    spark.sql(
      """
        |SELECT * FROM ads_card_deal_day_top LIMIT 20
        |""".stripMargin).show()

    spark.close()
  }
}

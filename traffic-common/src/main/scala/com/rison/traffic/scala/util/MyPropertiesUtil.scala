package com.rison.traffic.scala.util

import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.Properties

/**
 * @author : Rison 2021/6/22 上午9:53
 *         读取prop文件工具类
 */
object MyPropertiesUtil {
  /**
   * 加载prop
   * @param propertiesName
   * @return
   */
  def load(propertiesName: String) = {
    val prop: Properties = new Properties()
    //加载指定的配置文件
    prop.load(
      new InputStreamReader(
        Thread.currentThread().getContextClassLoader.getResourceAsStream(propertiesName),
        StandardCharsets.UTF_8
      )
    )
    prop
  }

  def main(args: Array[String]): Unit = {
    val prop: Properties = MyPropertiesUtil.load("application.properties")
    val st: String = prop.getProperty("origin.data.file.path")
    println(st)
  }
}

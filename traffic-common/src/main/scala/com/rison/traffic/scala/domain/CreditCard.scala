package com.rison.traffic.scala.domain

/**
 * @author : Rison 2021/6/20 下午2:36
 *         刷卡数据
 */
case class CreditCard(
                       deal_date: String, 			//交易时间，格式化的日期时间 			2018-09-01 11:19:04
                       close_date: String,			//结算时间，次日起始时间0点				2018-09-01 00:00:00
                       card_no: String, 			//卡号，9位字母						FFEBEBAJH
                       deal_value: String, 		//交易金额，整数分值，原价				200
                       deal_type: String, 			//交易类型，汉字描述 					地铁入站|地铁出站|巴士
                       company_name: String, 		//公司名称，线名						巴士集团|地铁七号线
                       car_no: String, 			//车号								01563D|宽AGM26-27
                       station: String, 			//站名								74路|华强北
                       conn_mark: String, 			//联程标记							0 直达 |1 联程
                       deal_money: String, 		//实收金额，整数分值，优惠后			190
                       equ_no: String 				//闸机号								265030122
                     )

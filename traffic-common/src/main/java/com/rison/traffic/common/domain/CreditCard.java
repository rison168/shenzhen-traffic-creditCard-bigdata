package com.rison.traffic.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 刷卡数据
 * @author rison
 */
@AllArgsConstructor
@Data
public class CreditCard {
    private String deal_date;
    private String close_date;
    private String card_no;
    private String deal_value;
    private String deal_type;
    private String company_name;
    private String car_no;
    private String station;
    private String conn_mark;
    private String deal_money;
    private String equ_no;
}

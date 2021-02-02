package gugu.trend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//交易类用于记录交易的购买日期，出售日期，购买盘点，出售盘点，收益
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trade {

    private String buyDate;         //购买日期
    private String sellDate;        //出售日期
    private float buyClosePoint;    //购买盘点
    private float sellClosePoint;   //出售盘点
    private float rate;             //收益
}

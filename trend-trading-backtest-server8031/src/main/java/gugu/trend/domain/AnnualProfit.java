package gugu.trend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnualProfit {     //每年收益实体类
    private int year;           //年份
    private float indexIncome;  //指数收益
    private float trendIncome;  //趋势收益
}

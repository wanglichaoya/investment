package gugu.trend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//利润类，有日期和数值两个属性，方便与IndexData做对比。
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profit {

    private String date;
    private float value;
}

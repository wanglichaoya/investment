package gugu.investment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//指数数据
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexData implements Serializable {
    private String date;        // 日期
    private float closePoint;   //收盘点

}

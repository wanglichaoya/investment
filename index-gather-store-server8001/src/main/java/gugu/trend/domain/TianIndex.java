package gugu.trend.domain;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class TianIndex {
    private String code;            //指数代码
    private Integer market;
    private String name;            //指数名字
    private Integer decimal;
    private Integer dktotal;        //历史数据总条数
    private List<String> klines;    //历史数据
}

package gugu.trend.domain;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class TianTian {
    private Integer rc;
    private Integer rt;
    private String svr;
    private Integer lt;
    private Integer full;
    private TianIndex data;  //数据
}

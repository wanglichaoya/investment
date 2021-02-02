package gugu.trend.domain;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Serialization
public class Result {
    Date date;
    float beginPoint;
    float closePoint;
    float max;
    float min;
    BigDecimal jiaoyi;
    BigDecimal money;
    float s;
}

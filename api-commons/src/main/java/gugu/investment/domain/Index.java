package gugu.investment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//指数类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Index implements Serializable{
    private String code; //指数里的名称
    private String name; //指数里的代码
}

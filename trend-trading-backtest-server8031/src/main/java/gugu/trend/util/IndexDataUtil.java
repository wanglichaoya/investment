package gugu.trend.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import gugu.investment.domain.IndexData;
import gugu.trend.domain.Profit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndexDataUtil {

    //根据提供的时间范围选择相应数据
    public static List<IndexData> filterByDateRange(List<IndexData> allIndexDatas, String strStartDate, String strEndDate) {
        if (StrUtil.isBlankOrUndefined(strStartDate) || StrUtil.isBlankOrUndefined(strEndDate))
            return allIndexDatas;
        List<IndexData> result = new ArrayList<>();
        Date startDate = DateUtil.parse(strStartDate);
        Date endDate = DateUtil.parse(strEndDate);

        for (IndexData indexData : allIndexDatas) {
            Date date = DateUtil.parse(indexData.getDate());
            if (date.getTime() >= startDate.getTime() && date.getTime() <= endDate.getTime())
                result.add(indexData);
        }

        return result;
    }

    //计算指数的数据跨越的时长（年）
    public static float getYear(List<IndexData> allIndexDatas) {
        float years;
        String sDateStart = allIndexDatas.get(0).getDate();
        String sDateEnd = allIndexDatas.get(allIndexDatas.size()-1).getDate();

        Date dateStart = DateUtil.parse(sDateStart);
        Date dateEnd = DateUtil.parse(sDateEnd);

        long days = DateUtil.between(dateStart, dateEnd, DateUnit.DAY);
        years = days/365f;
        return years;
    }

    //计算某一年的的指数收益
    public static float getIndexIncome(int year, List<IndexData> indexDatas) {
        IndexData first=null;
        IndexData last=null;
        for (IndexData indexData : indexDatas) {
            String strDate = indexData.getDate();
//			Date date = DateUtil.parse(strDate);
            String strYear = StrUtil.subBefore(strDate, "-", false);
            int currentYear = Convert.toInt(strYear);
            if(currentYear == year) {
                if(null==first)
                    first = indexData;
                last = indexData;
            }
        }
        return (last.getClosePoint() - first.getClosePoint()) / first.getClosePoint();
    }

    //计算某一年的趋势投资收益
    public static float getTrendIncome(int year, List<Profit> profits) {
        Profit first=null;
        Profit last=null;
        for (Profit profit : profits) {
            String strDate = profit.getDate();
            String strYear = StrUtil.subBefore(strDate, "-", false);
            int currentYear = Convert.toInt(strYear);
            if(currentYear == year) {
                if (null == first)
                    first = profit;
                last = profit;
            }
            if(currentYear > year)
                break;
        }
        return (last.getValue() - first.getValue()) / first.getValue();
    }
}

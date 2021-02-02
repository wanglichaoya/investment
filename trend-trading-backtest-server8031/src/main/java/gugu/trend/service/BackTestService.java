package gugu.trend.service;

import cn.hutool.core.date.DateUtil;
import gugu.investment.domain.IndexData;
import gugu.trend.domain.AnnualProfit;
import gugu.trend.domain.Profit;
import gugu.trend.domain.Trade;
import gugu.trend.util.IndexDataUtil;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BackTestService {

    /**
     * 买入、卖出算法。简单说就是追涨杀跌
     *
     * @param ma            moving average 移动均线
     * @param sellRate      出售阈值
     * @param buyRate       购买阈值
     * @param serviceCharge 交易费
     * @param indexDatas    根据日期过滤后的原数据
     * @return
     */
    public Map<String, Object> simulate(int ma, float sellRate, float buyRate, float serviceCharge, List<IndexData> indexDatas) {

        List<Profit> profits = new ArrayList<>();
        List<Trade> trades = new ArrayList<>();

        float initCash = 1000;                  //初始现金
        float cash = initCash;                  //现金
        float share = 0;                        //持有的份数
        float value = 0;                        //持有份数的总价值

        int winCount = 0;                       //盈利交易次数
        float totalWinRate = 0;                 //盈利比率
        float avgWinRate = 0;                   //平均盈利比率
        float totalLossRate = 0;                //亏损比率
        int lossCount = 0;                      //亏损交易次数
        float avgLossRate = 0;                  //平均亏损比率

        float init = 0;
        if (!indexDatas.isEmpty())
            init = indexDatas.get(0).getClosePoint();

        for (int i = 0; i < indexDatas.size(); i++) {
            IndexData indexData = indexDatas.get(i);
            float closePoint = indexData.getClosePoint();
            float avg = getMA(i, ma, indexDatas);
            float max = getMax(i, ma, indexDatas);

            float increase_rate = closePoint / avg;
            float decrease_rate = closePoint / max;

            if (avg != 0) {
                //buy 超过了均线
                if (increase_rate > buyRate) {
                    //如果没买
                    if (0 == share) {
                        share = cash / closePoint;
                        cash = 0;

                        Trade trade = new Trade();
                        trade.setBuyDate(indexData.getDate());
                        trade.setBuyClosePoint(indexData.getClosePoint());
                        trade.setSellDate("n/a");
                        trade.setSellClosePoint(0);
                        trades.add(trade);
                    }
                }
                //sell 低于了卖点
                else if (decrease_rate < sellRate) {
                    //如果没卖
                    if (0 != share) {
                        cash = closePoint * share * (1 - serviceCharge);
                        share = 0;

                        Trade trade = trades.get(trades.size() - 1);
                        trade.setSellDate(indexData.getDate());
                        trade.setSellClosePoint(indexData.getClosePoint());

                        float rate = cash / initCash;
                        trade.setRate(rate);

                        if (trade.getSellClosePoint() - trade.getBuyClosePoint() > 0) {
                            totalWinRate += (trade.getSellClosePoint() - trade.getBuyClosePoint()) / trade.getBuyClosePoint();
                            winCount++;
                        } else {
                            totalLossRate += (trade.getSellClosePoint() - trade.getBuyClosePoint()) / trade.getBuyClosePoint();
                            lossCount++;
                        }
                    }
                }

            }

            if (share != 0) {
                value = closePoint * share;
            } else {
                value = cash;
            }
            float rate = value / initCash;

            Profit profit = new Profit();
            profit.setDate(indexData.getDate());
            profit.setValue(rate * init);

            profits.add(profit);

        }
        avgWinRate = totalWinRate / winCount;
        avgLossRate = totalLossRate / lossCount;

        List<AnnualProfit> annualProfits = calculateAnnualProfits(indexDatas, profits);

        Map<String, Object> map = new HashMap<>();
        map.put("profits", profits);
        map.put("trades", trades);

        map.put("winCount", winCount);
        map.put("lossCount", lossCount);
        map.put("avgWinRate", avgWinRate);
        map.put("avgLossRate", avgLossRate);

        map.put("annualProfits", annualProfits);

        return map;
    }

    //获取日均线里的最大值
    private static float getMax(int i, int day, List<IndexData> list) {
        int start = i - 1 - day;
        if (start < 0)
            start = 0;
        int now = i - 1;

        if (start < 0)
            return 0;

        float max = 0;
        for (int j = start; j < now; j++) {
            IndexData bean = list.get(j);
            if (bean.getClosePoint() > max) {
                max = bean.getClosePoint();
            }
        }
        return max;
    }

    //计算日均线
    private static float getMA(int i, int ma, List<IndexData> list) {
        int start = i - 1 - ma;
        int now = i - 1;

        if (start < 0)
            return 0;

        float sum = 0;
        float avg = 0;
        for (int j = start; j < now; j++) {
            IndexData bean = list.get(j);
            sum += bean.getClosePoint();
        }
        avg = sum / (now - start);
        return avg;
    }

    //计算完整时间范围内，每一年的指数投资收益和趋势投资收益
    private List<AnnualProfit> calculateAnnualProfits(List<IndexData> indexDatas, List<Profit> profits) {
        List<AnnualProfit> result = new ArrayList<>();
        String strStartDate = indexDatas.get(0).getDate();
        String strEndDate = indexDatas.get(indexDatas.size() - 1).getDate();
        Date startDate = DateUtil.parse(strStartDate);
        Date endDate = DateUtil.parse(strEndDate);
        int startYear = DateUtil.year(startDate);
        int endYear = DateUtil.year(endDate);
        for (int year = startYear; year <= endYear; year++) {
            AnnualProfit annualProfit = new AnnualProfit();
            annualProfit.setYear(year);
            float indexIncome = IndexDataUtil.getIndexIncome(year, indexDatas);  //计算某一年的的指数收益
            float trendIncome = IndexDataUtil.getTrendIncome(year, profits);     //计算某一年的趋势投资收益
            annualProfit.setIndexIncome(indexIncome);
            annualProfit.setTrendIncome(trendIncome);
            result.add(annualProfit);
        }
        return result;
    }

}

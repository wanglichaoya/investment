package gugu.trend.controller;

import gugu.investment.domain.IndexData;
import gugu.trend.domain.AnnualProfit;
import gugu.trend.domain.Profit;
import gugu.trend.domain.Trade;
import gugu.trend.service.BackTestService;
import gugu.trend.service.IndexDataClientService;
import gugu.trend.util.IndexDataUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BackController {

    @Resource
    IndexDataClientService indexDataClientService;
    @Resource
    BackTestService backTestService;


    @GetMapping("/simulate/{code}/{ma}/{buyThreshold}/{sellThreshold}/{serviceCharge}/{startDate}/{endDate}")
    @CrossOrigin
    public Map<String, Object> backTest(@PathVariable("code") String code,
                                        @PathVariable("ma") int ma,
                                        @PathVariable("buyThreshold") float buyThreshold,
                                        @PathVariable("sellThreshold") float sellThreshold,
                                        @PathVariable("serviceCharge") float serviceCharge,
                                        @PathVariable("startDate") String strStartDate,
                                        @PathVariable("endDate") String strEndDate
                                       ) {
        List<IndexData> datas = indexDataClientService.getDatas(code);
        String indexStartDate = datas.get(0).getDate();
        String indexEndDate = datas.get(datas.size() - 1).getDate();

        datas = IndexDataUtil.filterByDateRange(datas, strStartDate, strEndDate);

        float sellRate = sellThreshold;
        float buyRate = buyThreshold;
        Map<String, ?> simulateResult = backTestService.simulate(ma, sellRate, buyRate, serviceCharge, datas);
        List<Profit> profits = (List<Profit>) simulateResult.get("profits");    //趋势投资利润信息集合，与指数做对比
        List<Trade> trades = (List<Trade>) simulateResult.get("trades");        //交易信息集合
        List<AnnualProfit> annualProfits = (List<AnnualProfit>) simulateResult.get("annualProfits");    //每一年的指数投资收益和趋势投资收益集合
        float years = IndexDataUtil.getYear(datas);                             //投资时长
        //指数投资总收益率
        float indexIncomeTotal = (datas.get(datas.size() - 1).getClosePoint() - datas.get(0).getClosePoint()) / datas.get(0).getClosePoint();
        float indexIncomeAnnual = (float) Math.pow(1 + indexIncomeTotal, 1 / years) - 1;    //指数投资年化率
        //趋势投资总收益率
        float trendIncomeTotal = (profits.get(profits.size() - 1).getValue() - profits.get(0).getValue()) / profits.get(0).getValue();
        float trendIncomeAnnual = (float) Math.pow(1 + trendIncomeTotal, 1 / years) - 1;    //趋势投资年化率

        int winCount = (Integer) simulateResult.get("winCount");            //盈利交易次数
        int lossCount = (Integer) simulateResult.get("lossCount");          //亏损交易次数
        float avgWinRate = (Float) simulateResult.get("avgWinRate");        //平均盈利比率
        float avgLossRate = (Float) simulateResult.get("avgLossRate");      //平均亏损比率

        Map<String, Object> result = new HashMap<>();
        result.put("indexDatas", datas);                        //指数
        result.put("indexStartDate", indexStartDate);           //默认指数开始日期
        result.put("indexEndDate", indexEndDate);               //默认指数结束日期
        result.put("profits", profits);                         //趋势投资利润信息集合，与指数做对比
        result.put("trades", trades);                           //交易信息集合
        result.put("years", years);                             //投资时长（年）
        result.put("indexIncomeTotal", indexIncomeTotal);       //指数投资总收益率
        result.put("indexIncomeAnnual", indexIncomeAnnual);     //指数投资年化率
        result.put("trendIncomeTotal", trendIncomeTotal);       //趋势投资总收益率
        result.put("trendIncomeAnnual", trendIncomeAnnual);     //趋势投资年化率
        result.put("winCount", winCount);                       //盈利交易次数
        result.put("lossCount", lossCount);                     //亏损交易次数
        result.put("avgWinRate", avgWinRate);                   //平均盈利比率
        result.put("avgLossRate", avgLossRate);                 //平均亏损比率
        result.put("annualProfits", annualProfits);             //每一年的指数投资收益和趋势投资收益

        return result;

    }
}

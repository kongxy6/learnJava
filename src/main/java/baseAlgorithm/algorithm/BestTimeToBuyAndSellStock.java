package baseAlgorithm.algorithm;

public class BestTimeToBuyAndSellStock {
    public int maxProfit(int[] prices) {
        int bestBegin = 0;
        int bestEnd = 0;
        int begin = 0;
        int end = 0;
        for (int i = 0; i < prices.length; ++i) {
            if (i > begin && prices[i] > prices[begin] && prices[i] > prices[end]) {
                end = i;
                if (prices[end] - prices[begin] > prices[bestEnd] - prices[bestBegin]) {
                    bestBegin = begin;
                    bestEnd = end;
                }
            } else if (prices[i] < prices[begin]) {
                begin = i;
                end = i;
            }

        }
        return prices[bestEnd] - prices[bestBegin];
    }
}

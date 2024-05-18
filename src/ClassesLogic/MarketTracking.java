package ClassesLogic;

import java.util.ArrayList;
import java.util.List;

public class MarketTracking {
    private List<Stock> stocks;

    public MarketTracking(List<Stock> stocks) {
        this.stocks = new ArrayList<>(stocks);
    }

    public double calculateAggregateProfitLoss() {
        double totalInvestment = 0.0;
        double currentPortfolioValue = 0.0;

        for (Stock stock : stocks) {
            totalInvestment += stock.getInvestment();
            currentPortfolioValue += stock.getCurrentValue();
        }

        return ((currentPortfolioValue - totalInvestment) / totalInvestment) * 100;
    }

    private static class Stock {
        private final String symbol;
        private final double investment;
        private final double currentValue;

        public Stock(String symbol, double investment, double currentValue) {
            this.symbol = symbol;
            this.investment = investment;
            this.currentValue = currentValue;
        }

        public double getInvestment() {
            return investment;
        }

        public double getCurrentValue() {
            return currentValue;
        }
    }
}


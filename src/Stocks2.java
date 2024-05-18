import javafx.beans.property.*;

public class Stocks2 {
    private final StringProperty stockName;
//    private final IntegerProperty initialPrice;
    private final FloatProperty tradingPrice;
    private final IntegerProperty availableStocks;

    public Stocks2(String stockName, float tradingPrice, int availableStocks)
    {
        this.stockName = new SimpleStringProperty(stockName);
//        this.initialPrice = new SimpleIntegerProperty(initialPrice);
        this.tradingPrice = new SimpleFloatProperty(tradingPrice);
        this.availableStocks = new SimpleIntegerProperty(availableStocks);
    }
    public String getStockName() {
        return stockName.get();
    }

    public void setStockName(String stockName) {
        this.stockName.set(stockName);
    }

    public StringProperty stockNameProperty() {
        return stockName;
    }

//    public int getInitialPrice() {
//        return initialPrice.get();
//    }
//
//    public void setInitialPrice(int initialPrice) {
//        this.initialPrice.set(initialPrice);
//    }

//    public IntegerProperty initialPriceProperty() {
//        return initialPrice;
//    }

    public float getTradingPrice() {
        return tradingPrice.get();
    }

    public void setTradingPrice(float tradingPrice) {
        this.tradingPrice.set(tradingPrice);
    }

    public FloatProperty tradingPriceProperty() {
        return tradingPrice;
    }

    public int getAvaliblestocks() {
        return availableStocks.get();
    }

    public void setAvailableStocks(int availableStocks) {
        this.availableStocks.set(availableStocks);
    }

    public IntegerProperty availableStocksProperty() {
        return availableStocks;
    }
}

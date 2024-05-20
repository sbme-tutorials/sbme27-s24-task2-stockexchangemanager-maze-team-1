package ClassesLogic;

import java.util.List;

public class PremiumUser extends User {
    private List<String> subscribedStocks;
    private boolean isSubscribed;

    public PremiumUser(String username, String password, String email, List<String> subscribedStocks, boolean isSubscribed) {
        super(username, password,email);
        this.subscribedStocks = subscribedStocks;
        this.isSubscribed = isSubscribed;
    }

    public void subscribeStock(String stockName) {
        if (this.isSubscribed) {
            if (!this.subscribedStocks.contains(stockName)) {
                this.subscribedStocks.add(stockName);
                System.out.println("Subscribed to " + stockName);
            } else {
                System.out.println("Already subscribed to " + stockName);
            }
        } else {
            System.out.println("You must be a subscribed user to subscribe to a stock.");
        }
    }

    public void unsubscribeStock(String stockName) {
        if (this.isSubscribed) {
            if (this.subscribedStocks.contains(stockName)) {
                this.subscribedStocks.remove(stockName);
                System.out.println("Unsubscribed from " + stockName);
            } else {
                System.out.println("Not subscribed to " + stockName);
            }
        } else {
            System.out.println("You must be a subscribed user to unsubscribe from a stock.");
        }
    }

    public void notifyPriceChange(String stockName, double oldPrice, double newPrice) {
        if (this.isSubscribed && this.subscribedStocks.contains(stockName)) {
            System.out.println("Price of " + stockName + " has changed from " + oldPrice + " to " + newPrice);
        }
    }

    public void showLineChart(String stockName) {
        if (this.isSubscribed) {
            // You will need to implement this method once you have integrated line charts into your project.
            System.out.println("Line chart for " + stockName + " is not available yet.");
        } else {
            System.out.println("You must be a subscribed user to view line charts.");
        }
    }
}

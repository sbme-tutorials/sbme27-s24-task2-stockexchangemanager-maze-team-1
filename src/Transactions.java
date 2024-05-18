import java.util.Date;

public class Transactions {
    private String state;
    private String stockName;
    private float price;
    private int stockNumber;
    private float totalPrice;
    private Date timestamp;

    // Constructor
    public Transactions(String state, String stockName, float price, int stockNumber, float totalPrice, Date timestamp) {
        this.state = state;
        this.stockName = stockName;
        this.price = price;
        this.stockNumber = stockNumber;
        this.totalPrice = totalPrice;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    // Method to calculate total price if not provided
    public void calculateTotalPrice() {
        this.totalPrice = this.price * this.stockNumber;
    }

    // Method to display transaction details
    @Override
    public String toString() {
        return "Transactions{" +
                "state='" + state + '\'' +
                ", stockName='" + stockName + '\'' +
                ", price=" + price +
                ", stockNumber=" + stockNumber +
                ", totalPrice=" + totalPrice +
                ", timestamp=" + timestamp +
                '}';
    }
}

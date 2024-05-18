package ClassesLogic;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Stock {
    private Button Buy;
    private String number;
    private String name;
    private double price;
    // private int quantity;
    private TextField  quantity;
    private double openingPrice;

    public Stock(String Buy, String number, String name, double price, String quantity) {
        this.Buy =new Button("Buy") ;
        this.number = number;
        this.name = name;
        this.price = price;
        this.quantity = new TextField(quantity);
    }

    // public void updatePrice(double newPrice) {
    //     this.price = newPrice;
    // }

    // public void updateQuantity(int newQuantity) {
    //     this.quantity = newQuantity;
    // }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public Button getBuy() {
        return Buy;
    }

    public void setButton(Button buy) {
        this.Buy = buy;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TextField getQuantity() {
        return quantity;
    }

    public void setQuantity(TextField quantity) {
        this.quantity = quantity;
    }

    public String getSymbol() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSymbol'");
    }

    public double getOpeningPrice() {        return openingPrice;
    }

    public void setOpeningPrice(double openingPrice) {
        this.openingPrice = openingPrice;
    }
}

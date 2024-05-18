import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Stocks {
    String Stockname;
     Float Price;
    Integer  Stocknumber;
    private Button buyButton;
     private TextField Quantityfield;

    public Stocks(String stockname, Float price, Integer stocknumber) {
        Stockname = stockname;
        Price = price;
        Stocknumber = stocknumber;
//        this.buyButton=buyColumn;
//        this.Quantityfield= quantityCol;
    }

    public String getStockname() {
        return Stockname;
    }

    public void setStockname(String stockname) {
        Stockname = stockname;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public Integer getStocknumber() {
        return Stocknumber;
    }

    public void setStocknumber(Integer stocknumber) {
        Stocknumber = stocknumber;
    }

    public Button getBuyButton() {
        return buyButton;
    }

    public void setBuyButton(Button buyButton) {
        this.buyButton = buyButton;
    }

    public TextField getQuantityfield() {
        return Quantityfield;
    }
    public String getstockQuantity() {
        return Quantityfield.getText();
    }

    public void setQuantityfield(TextField quantityfield) {
        Quantityfield = quantityfield;
    }
}

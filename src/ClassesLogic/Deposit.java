package ClassesLogic;

import javafx.scene.control.Button;

public class Deposit {
    private String username ;
    private double amountDeposit;
    private double amountWithdraw;
    private Button approveButton;
    private Button rejectButton;
     
    public Deposit(String username, double amountDeposit, double amountWithdraw,
            Button approveButton, Button rejectButton) {
                this.username = username;
                this.amountDeposit = amountDeposit;
                this.amountWithdraw = amountWithdraw;
                this.approveButton = approveButton;
                this.rejectButton = rejectButton;
    }
    
    public double getAmountDeposit() {
        return amountDeposit;
    }
    public void setAmountDeposit(double amountDeposit) {
        this.amountDeposit = amountDeposit;
    }
    public Button getApproveButton() {
        return approveButton;
    }
    public void setApproveButton(Button approveButton) {
        this.approveButton = approveButton;
    }
    public Button getRejectButton() {
        return rejectButton;
    }
    public void setRejectButton(Button rejectButton) {
        this.rejectButton = rejectButton;
    }

    public double getAmountWithdraw() {
        return amountWithdraw;
    }

    public void setAmountWithdraw(double amountWithdraw) {
        this.amountWithdraw = amountWithdraw;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

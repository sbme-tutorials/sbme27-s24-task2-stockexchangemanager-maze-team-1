package ClassesLogic;

import java.awt.*;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

public class Noti {
    public static void main(String[] args) throws AWTException {
        Noti notificationManager = new Noti();
        notificationManager.displayStockPriceChange("Zobr",19,20);
    }

    public void displayStockPriceChange(String stockName, float oldPrice, float newPrice) throws AWTException {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage("1_deliverable\\icon.png");

            TrayIcon trayIcon = new TrayIcon(image, "Stock App");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Stock price notifications");
            tray.add(trayIcon);

            String changeText;
            if (newPrice > oldPrice) {
                changeText = "went up";
            } else if (newPrice < oldPrice) {
                changeText = "went down";
            } else {
                changeText = "remained unchanged";
            }

            String notificationText = String.format("%s price %s: %.2f -> %.2f", stockName, changeText, oldPrice, newPrice);
            trayIcon.displayMessage("Stock Price Change", notificationText, MessageType.INFO);
        } else {
            System.err.println("System tray not supported!");
        }
    }

}

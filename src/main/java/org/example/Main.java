package org.example;
import org.example.controller.OrderController;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        OrderController orderController = new OrderController();

        // Planifier la tâche chaque heure
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                orderController.processOrders();
            }
        }, 0, 3600 * 1000); // Toutes les heures
    }
}

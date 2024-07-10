package POJO_Ecommerce;

import java.util.ArrayList;
import java.util.List;

public class Level1_CreateOrder {

    List<Level2_CreateOrderDetails> orders = new ArrayList<>();

    public List<Level2_CreateOrderDetails> getOrders() {
        return orders;
    }

    public void setOrders(List<Level2_CreateOrderDetails> orders) {
        this.orders = orders;
    }
}

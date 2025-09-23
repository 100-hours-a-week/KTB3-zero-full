package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Bag {
    int totalPrice;

    Scanner scanner =  new Scanner(System.in);
    ArrayList<String> productList = new ArrayList<String>();
    ArrayList<Integer> priceList = new ArrayList<> ();

    void insertProduct(Device device) {
        productList.add(device.getClass().getSimpleName());
        priceList.add(device.price);
        totalPrice = totalPrice+device.price;
    }

    void showProduct() {
        if (productList.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다");
            return;
        }
        for (int i = 0; i < productList.size(); i++) {
            System.out.printf("%d | %s | %,d$%n", i, productList.get(i), priceList.get(i));
        }
        System.out.printf("총 금액: %,d$%n", totalPrice);
    }

    boolean deleteProduct(int index) {
        if (index < 0 || index >= productList.size()) {
            System.out.println("유효하지 않은 번호입니다.");
            return false;
        }
        totalPrice -= priceList.get(index);
        productList.remove(index);
        priceList.remove(index);
        System.out.println("삭제되었습니다.");
        return true;
    }

    int getTotalPrice() { return totalPrice; }
    boolean isEmpty() { return productList.isEmpty(); }


}

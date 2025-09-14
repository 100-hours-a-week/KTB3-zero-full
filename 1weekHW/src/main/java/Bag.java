import java.util.ArrayList;

public class Bag {
    int totalprice;
    ArrayList<String> productList = new ArrayList<String>();

    void insertProduct(int series, String species, String color, int storage, int price) {
        productList.add("Iphone"+series+"/"+species+"/"+color+"/"+storage+":"+price+"$");
    }

    void showProduct() {
        for(int i=0; i<productList.size();i++) {
            System.out.println(productList.get(i));
        }
    }
    void calculateTotalPrice(int price) {
        totalprice=totalprice+price;

    }
}

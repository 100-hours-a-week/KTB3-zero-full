import java.util.Scanner;

public class Phone {
    String brand;
}

class Iphone extends Phone {
    Scanner sc = new Scanner(System.in);

    String brand = "Apple";
    int series;
    String species;
    String color;
    int storage;
    int speciesPrice;
    int storagePrice;
    int price;

    void selectSpecies() {};
    void selectColor() {};
    void selectStorage() {};
    void setSeriesPrice() {};
    void setStoragePrice() {};
    void checkMySelect() {
        System.out.println("당신이 선택한 핸드폰은 Iphone"+series+"/"+species+"/"+color+"/"+storage+"입니다!");
        price = speciesPrice+storagePrice;
        System.out.println("가격은 "+price+"입니다!");
    }



}

class Iphone17 extends Iphone {
    Scanner sc = new Scanner(System.in);
    int series = 17;


    void selectSpecies() {
        System.out.println("어떤 기종을 선택하시겠습니까?(basic, air, pro, proMax): ");
        String species = sc.nextLine();
        this.species = species;
    }

    void selectColor() {
        if (species.equals("basic")) {
            System.out.println("어떤 색상을 선택하시겠습니까?(red, green, yellow): ");
        } else if(species.equals("air")){
            System.out.println("어떤 색상을 선택하시겠습니까?(babyBlue, white): ");
        }
        else {
            System.out.println("어떤 색상을 선택하시겠습니까?(white, blue, black): ");
        }
        String color = sc.nextLine();
        this.color = color;
    }
    void selectStorage() {
        if(species.equals("air")) {
            System.out.println("용량을 선택해주세요. 단위는 GB입니다.(128, 256): ");

        } else if(species.equals("proMax")){
            System.out.println("용량을 선택해주세요. 단위는 GB입니다.(128, 256, 512, 1024): ");
        } else {
            System.out.println("용량을 선택해주세요. 단위는 GB입니다.(128, 256, 512): ");
        }

        int storage = sc.nextInt();
        this.storage = storage;
    }

    void setSeriesPrice() {
        if (species.equals("basic")) {
            this.speciesPrice = 950;
        } else if (species.equals("air")) {
            this.speciesPrice = 800;
        } else if (species.equals("pro")) {
            this.speciesPrice = 1079;
        } else if (species.equals("proMax")) {
            this.speciesPrice = 1259;
        }
    }
    void setStoragePrice() {
            if (storage == 128) {
                this.storagePrice = 0;
            } else if (storage == 256) {
                this.storagePrice = 100;
            } else if (storage == 512) {
                this.storagePrice = 200;
            } else if (storage == 1024) {
                this.storagePrice = 350;
            }
        }

    }

class Iphone16 extends Iphone {
    Scanner sc = new Scanner(System.in);
    int series = 16;


    void selectSpecies() {
        System.out.println("어떤 기종을 선택하시겠습니까?(basic, pro, proMax): ");
        String species = sc.nextLine();
        this.species = species;
    }

    void selectColor() {
        if (species.equals("basic")) {
            System.out.println("어떤 색상을 선택하시겠습니까?(red, green, yellow): ");
        } else {
            System.out.println("어떤 색상을 선택하시겠습니까?(white, blue, black): ");
        }
        String color = sc.nextLine();
        this.color = color;
    }
    void selectStorage() {
        System.out.println("용량을 선택해주세요. 단위는 GB입니다.(128, 256, 512): ");
        int storage = sc.nextInt();
        this.storage = storage;
    }

    void setSeriesPrice() {
        if (species.equals("basic")) {
            this.speciesPrice = 900;
        } else if (species.equals("pro")) {
            this.speciesPrice = 1050;
        } else if (species.equals("proMax")) {
            this.speciesPrice = 1199;
        }
    }
    void setStoragePrice() {
        if (storage == 128) {
            this.storagePrice = 0;
        } else if (storage == 256) {
            this.storagePrice = 100;
        } else if (storage == 512) {
            this.storagePrice = 200;
        }
    }

}

package org.example;

import java.util.Scanner;

public class Device {
    int price;
    String color;
    Scanner scanner = new Scanner(System.in);

    void calculatePrice() {};
    void showProduct() {};
}

class Mac extends Device {
    int size;
    int storage;
    int integratedMemory;

    void selectSize() {
        System.out.println("크기를 선택하세요. 단위는 inch입니다. 숫자만 입력하세요[13/15]: ");
        this.size = scanner.nextInt();
        scanner.nextLine();
    }

    void selectIntegratedMemory() {
        System.out.println("통합 메모리를 선택하세요. 단위는 GB입니다. 숫자만 입력하세요[16/24]: ");
        this.integratedMemory = scanner.nextInt();
        scanner.nextLine();
    }

    void selectStorage() {
        if(integratedMemory==24) {
            this.storage = 512;
        } else {
            System.out.println("용량을 선택하세요. 단위는 GB입니다. 숫자만 입력하세요[256/512]: ");
            this.storage = scanner.nextInt();
            scanner.nextLine();
        }
    }
}

class MacBook_Air extends Mac {
    MacBook_Air() {
        super.price = 999;
    }
    void selectColor() {
        System.out.println("색상을 선택하세요. 단어 그대로 입력하세요[silver/starlight/skyblue]");
        super.color = scanner.nextLine();
    }
    @Override
    void calculatePrice() {
       this.showProduct();
        if(size==15) price+=200;
        if(integratedMemory==24) price+=200;
        if(storage==512) price+=200;
        System.out.println("해당 상품의 가격은 "+price+"$");
    }
    @Override
    void showProduct() {
        System.out.println("MacBook_Air/"+size+"/"+color+"/"+integratedMemory+"GB 통합메모리/"+storage+"GB SSD 저장 장치");
    }

}

class MacBook_Pro extends Mac {
    MacBook_Pro() {
        super.price = 1599;
    }
    void selectColor() {
        System.out.println("색상을 선택하세요. 단어 그대로 입력하세요[space_black/silver]");
        super.color = scanner.nextLine();
    }
    @Override
    void calculatePrice() {
        this.showProduct();
        if(size==15) price+=200;
        if(integratedMemory==24) price+=200;
        if(storage==512) price+=200;
        System.out.println("해당 상품의 가격은 "+price+"$");
    }

    @Override
    void showProduct() {
        System.out.println("MacBook_Pro/"+size+"/"+color+"/"+integratedMemory+"GB 통합메모리/"+storage+"GB SSD 저장 장치");
    }

}

class Iphone extends Device {
    int storage;

    void selectStorage() {
        System.out.println("용량을 선택하세요. 단위는 GB입니다. 숫자만 입력하세요[256/512]: ");
        this.storage = scanner.nextInt();
        scanner.nextLine();
        }
    }


class Iphone17_Pro extends Iphone {
    Iphone17_Pro() {
        super.price = 1099;
    }

    void selectColor() {
        System.out.println("색상을 선택하세요. 단어 그대로 입력하세요[silver/orange/deep_blue]");
        super.color = scanner.nextLine();
    }

    @Override
    void selectStorage() {
        System.out.println("용량을 선택하세요. 단위는 GB입니다. 숫자만 입력하세요[256/512/1024]: ");
        this.storage = scanner.nextInt();
        scanner.nextLine();
    }

    @Override
    void calculatePrice() {
        this.showProduct();
        if(storage==512) price+=200;
        if(storage==1024) price+=200;
        System.out.println("해당 상품의 가격은 "+price+"$");
    }

    @Override
    void showProduct() {
        System.out.println("Iphone17_Pro/"+color+"/"+storage+"GB 용량");
    }
}

class Iphone17 extends Iphone {
    Iphone17() {
        super.price = 799;
    }

    void selectColor() {
        System.out.println("색상을 선택하세요. 단어 그대로 입력하세요[lavender/sage/white/black]");
        super.color = scanner.nextLine();
    }

    @Override
    void calculatePrice() {
        this.showProduct();
        if(storage==512) price+=200;
        System.out.println("해당 상품의 가격은 "+price+"$");
    }

    @Override
    void showProduct() {
        System.out.println("Iphone17/"+color+"/"+storage+"GB 용량");
    }
}

    class Iphone_Air extends Iphone {
        Iphone_Air() {
            super.price = 999;
        }

        void selectColor() {
            System.out.println("색상을 선택하세요. 단어 그대로 입력하세요[skyblue/light_gold/white/black]");
            super.color = scanner.nextLine();
        }

        @Override
        void calculatePrice() {
            this.showProduct();
            if(storage==512) price+=200;
            System.out.println("해당 상품의 가격은 "+price+"$");
        }

        void showProduct() {
            System.out.println("Iphone17_Air/"+color+"/"+storage+"GB 용량");
        }
    }

class Airpods extends Device {
     Airpods() {
         super.color = "white";
     }
        }

     class Airpods_Pro extends Airpods {
            void calculatePrice() {
                super.price = 249;
                showProduct();
                System.out.println("해당 상품의 가격은 "+price+"$");
            }
            void showProduct() {
                System.out.println("Airpods_Pro");
            }
     }

     class Airpods4 extends Airpods {
            void calculatePrice() {
                super.price = 129;
               this.showProduct();
                System.out.println("해당 상품의 가격은 "+price+"$");
            }
            void showProduct() {
                System.out.println("Airpods4");
            }
        }

     class Airpods_Max extends Airpods {
         void selectColor() {
             System.out.println("색상을 선택하세요. 단어 그대로 입력하세요[blue/purple/midnight/starlight/orange]");
             super.color = scanner.nextLine();
         }
         void calculatePrice() {
             super.price = 549;
             this.showProduct();
             System.out.println("해당 상품의 가격은 "+price+"$");
         }
         void showProduct() {
             System.out.println("Airpods_Max/"+color);
         }
        }





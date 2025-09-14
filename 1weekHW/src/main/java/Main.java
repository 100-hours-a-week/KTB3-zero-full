import java.util.Scanner;

public class Main
{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("안녕하세요 AppleStore입니다!");
        Bag myBag = new Bag();

        int status = 0;

        while (status != 3) {//메뉴 선택
            System.out.println("*****************************************************");
            System.out.println("무엇을 도와드릴까요?");
            System.out.println("1. 제품 구매");
            System.out.println("2. 장바구니 확인");
            System.out.println("3.계산하기");

            System.out.println("원하는 메뉴의 번호를 입력해주세요: ");
            int menu = sc.nextInt();
            sc.nextLine();
            System.out.println("*****************************************************");

            switch (menu) {
                case 1:
                    System.out.println("어떤 제품 보러 오셨나요? (Iphone16/Iphone17): ");
                    String choice = sc.nextLine();

                    switch (choice) {
                        case "Iphone16":
                            Iphone16 iphone16 = new Iphone16();
                            iphone16.selectSpecies();
                            iphone16.selectColor();
                            iphone16.selectStorage();
                            iphone16.setSeriesPrice();
                            iphone16.setStoragePrice();
                            System.out.println("-------------------------------------------");
                            iphone16.checkMySelect();
                            System.out.println("장바구니에 넣으시겠습니까? (Yes/No): ");
                            String Buying = sc.nextLine();
                            if (Buying.equals("Yes")) {
                                myBag.insertProduct(iphone16.series, iphone16.species, iphone16.color, iphone16.storage, iphone16.price);
                                myBag.calculateTotalPrice(iphone16.price);
                            }
                            break;
                        case "Iphone17":
                            Iphone17 iphone17 = new Iphone17();
                            iphone17.selectSpecies();
                            iphone17.selectColor();
                            iphone17.selectStorage();
                            iphone17.setSeriesPrice();
                            iphone17.setStoragePrice();
                            System.out.println("-------------------------------------------");
                            iphone17.checkMySelect();
                            System.out.println("장바구니에 넣으시겠습니까? (Yes/No): ");
                            String Buy = sc.nextLine();
                            if (Buy.equals("Yes")) {
                                myBag.insertProduct(iphone17.series, iphone17.species, iphone17.color, iphone17.storage, iphone17.price);
                                myBag.calculateTotalPrice(iphone17.price);
                            }
                            break;

                        default:
                            System.out.println("해당 상품은 조회되지 않습니다. ");
                            break;
                    }
                    break;

                case 2:
                    System.out.println("나의 장바구니");
                    System.out.println("-------------------------------------------");
                    myBag.showProduct();
                    System.out.println("-------------------------------------------");
                    System.out.println("총금액은 " + myBag.totalprice + "$입니다~!");
                    break;

                case 3:
                    System.out.println("총"+myBag.totalprice+"$ 계산 도와드리겠습니다!");
                    System.out.println("감사합니다 또 뵙겠습니다!");
            }

        }
    }
        }









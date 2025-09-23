package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("안녕하세요 AppleStore입니다!");
        Bag myBag = new Bag();
        Runnable advertisement = new Advertisement();
        Runnable premium = new Premium();

        Thread advertisementThread = new Thread(advertisement);
        Thread premiumThread = new Thread(premium);

        advertisementThread.start();
        premiumThread.start();

        int status = 0;

        while (status != 3) {//메뉴 선택
            System.out.println("*****************************************************");
            System.out.println("무엇을 도와드릴까요?");
            System.out.println("1. 제품 코너");
            System.out.println("2. 장바구니");
            System.out.println("3. 계산대-출구");

            System.out.println("원하는 메뉴의 번호를 입력해주세요: ");
            status = scanner.nextInt();
            scanner.nextLine();
            System.out.println("*****************************************************");

            switch (status) {
                case 1:
                    System.out.println("어떤 제품 보러 오셨나요? (Mac/Iphone/Airpods): "); //Device 선택
                    String Device = scanner.nextLine();
                    switch (Device) {
                        case "Mac":
                            System.out.println("어떤 Mac을 찾으시나요? (MacBook_Air/MacBook_Pro): ");
                            String Mac = scanner.nextLine();
                            if (Mac.equals("MacBook_Air")) {
                                MacBook_Air product = new MacBook_Air();
                                product.selectSize();
                                product.selectIntegratedMemory();
                                product.selectStorage();
                                product.selectColor();
                                product.calculatePrice();
                                System.out.println("해당 상품을 장바구니에 넣으시겠습니까? (yes/no): ");
                                String insertProduct = scanner.nextLine();
                                if (insertProduct.equals("yes")) {
                                    myBag.insertProduct(product);
                                    System.out.println("상품이 장바구니에 담겼습니다!");
                                } else if(insertProduct.equals("no")) {
                                    System.out.println("상품을 담지 않았습니다.");
                                }
                            } else if (Mac.equals("MacBook_Pro")) {
                                MacBook_Pro product = new MacBook_Pro();
                                product.selectSize();
                                product.selectIntegratedMemory();
                                product.selectStorage();
                                product.selectColor();
                                product.calculatePrice();
                                System.out.println("해당 상품을 장바구니에 넣으시겠습니까? (yes/no): ");
                                String insertProduct = scanner.nextLine();
                                if (insertProduct.equals("yes")) {
                                    myBag.insertProduct(product);
                                    System.out.println("상품이 장바구니에 담겼습니다!");
                                } else if(insertProduct.equals("no")) {
                                    System.out.println("상품을 담지 않았습니다.");
                                }
                            }

                            break;

                        case "Iphone":
                            System.out.println("어떤 Iphone을 찾으시나요? (Iphone17_Pro/Iphone17/Iphone_Air): ");
                            String Iphone = scanner.nextLine();
                            if (Iphone.equals("Iphone17_Pro")) {
                                Iphone17_Pro product = new Iphone17_Pro();
                                product.selectStorage();
                                product.selectColor();
                                product.calculatePrice();
                                System.out.println("해당 상품을 장바구니에 넣으시겠습니까? (yes/no): ");
                                String insertProduct = scanner.nextLine();
                                if (insertProduct.equals("yes")) {
                                    myBag.insertProduct(product);
                                    System.out.println("상품이 장바구니에 담겼습니다!");
                                } else if(insertProduct.equals("no")) {
                                    System.out.println("상품을 담지 않았습니다.");
                                }
                            } else if (Iphone.equals("Iphone17")) {
                                Iphone17_Pro product = new Iphone17_Pro();
                                product.selectStorage();
                                product.selectColor();
                                product.calculatePrice();
                                System.out.println("해당 상품을 장바구니에 넣으시겠습니까? (yes/no): ");
                                String insertProduct = scanner.nextLine();
                                if (insertProduct.equals("yes")) {
                                    myBag.insertProduct(product);
                                    System.out.println("상품이 장바구니에 담겼습니다!");
                                } else if(insertProduct.equals("no")) {
                                    System.out.println("상품을 담지 않았습니다.");
                                }
                            } else if (Iphone.equals("Iphone_Air")) {
                                Iphone_Air product = new Iphone_Air();
                                product.selectStorage();
                                product.selectColor();
                                product.calculatePrice();
                                System.out.println("해당 상품을 장바구니에 넣으시겠습니까? (yes/no): ");
                                String insertProduct = scanner.nextLine();
                                if (insertProduct.equals("yes")) {
                                    myBag.insertProduct(product);
                                    System.out.println("상품이 장바구니에 담겼습니다!");
                                } else if(insertProduct.equals("no")) {
                                    System.out.println("상품을 담지 않았습니다.");
                                }
                            }
                            break;

                        case "Airpods":
                            System.out.println("어떤 Airpods을 찾으시나요? (Airpods4/Airpods_Pro/Airpods_Max): ");
                            String Airpods = scanner.nextLine();
                            if (Airpods.equals("Airpods4")) {
                                Airpods4 product = new Airpods4();
                                product.calculatePrice();
                                System.out.println("해당 상품을 장바구니에 넣으시겠습니까? (yes/no): ");
                                String insertProduct = scanner.nextLine();
                                if (insertProduct.equals("yes")) {
                                    myBag.insertProduct(product);
                                    System.out.println("상품이 장바구니에 담겼습니다!");
                                } else if(insertProduct.equals("no")) {
                                    System.out.println("상품을 담지 않았습니다.");
                                }
                            } else if (Airpods.equals("Airpods_Pro")) {
                                Airpods_Pro product = new Airpods_Pro();
                                product.calculatePrice();
                                System.out.println("해당 상품을 장바구니에 넣으시겠습니까? (yes/no): ");
                                String insertProduct = scanner.nextLine();
                                if (insertProduct.equals("yes")) {
                                    myBag.insertProduct(product);
                                    System.out.println("상품이 장바구니에 담겼습니다!");
                                } else if(insertProduct.equals("no")) {
                                    System.out.println("상품을 담지 않았습니다.");
                                }
                            } else if (Airpods.equals("Airpods_Max")) {
                                Airpods_Max product = new Airpods_Max();
                                product.selectColor();
                                product.calculatePrice();
                                System.out.println("해당 상품을 장바구니에 넣으시겠습니까? (yes/no): ");
                                String insertProduct = scanner.nextLine();
                                if (insertProduct.equals("yes")) {
                                    myBag.insertProduct(product);
                                    System.out.println("상품이 장바구니에 담겼습니다!");
                                } else if(insertProduct.equals("no")) {
                                    System.out.println("상품을 담지 않았습니다.");
                                }
                            }
                            break;
                    }
                    break;

                case 2:
                    System.out.println("------------내가 담은 상품------------");
                    myBag.showProduct();
                    System.out.println("-----------------------------------");
                    System.out.println("1. 장바구니 상품 삭제");
                    System.out.println("2. 메인 메뉴로 돌아가기");
                    System.out.println("원하는 메뉴의 번호를 입력해주세요: ");
                    int menu2=0;
                    menu2 = scanner.nextInt();
                    scanner.nextLine();

                    switch (menu2) {
                        case 1:
                            System.out.print("삭제할 제품의 목록 번호: ");
                            int index;
                            try {
                                index = scanner.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("숫자만 입력하세요.");
                                scanner.nextLine();
                                break;
                                }
                            scanner.nextLine();
                            myBag.deleteProduct(index);
                            break;

                            case 2:
                                break;

                                default:
                                System.out.println("존재하지 않는 메뉴입니다.");
                                break;
                        }
                    break;

                case 3:
                    System.out.println("안녕하세요 계산을 도와드릴까요? (yes/no): ");
                    String pay = scanner.nextLine();
                    if(pay.equals("yes")) {
                        System.out.println("총 금액 " + myBag.totalPrice + "결제되었습니다.");
                        System.out.println("");
                        System.out.println("안녕히 가세요!");
                    } else if(pay.equals("no")) {
                        System.out.println("또 오세요!");
                    } else if(pay.equals("ApplePremium")) {
                        status = 0;
                        System.out.println("ApplePremium 구독료는 20$입니다. ");
                        System.out.println("계산을 도와드릴까요? (yes/no): ");
                        String subscription = scanner.nextLine();
                        if(subscription.equals("yes")) {
                            advertisementThread.interrupt();
                            premiumThread.interrupt();
                            System.out.printf("결제 되었습니다.\n (광고 off 상태)\n편안한 쇼핑 되세요!\n");
                        } else if(subscription.equals("no")) {
                            System.out.printf("구독 하지 않으셨습니다. \n (광고 on 상태)\n");

                        }
                    }
                    break;

                default:
                    System.out.println("존재하지 않는 메뉴입니다. ");
                    break;
            }
        }
    }
}
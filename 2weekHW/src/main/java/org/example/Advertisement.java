package org.example;

public class Advertisement implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(10000); // 10초 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        for (int i = 0; i < 5; i++) {
            if (Thread.currentThread().isInterrupted()) return;
            System.out.printf("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n| 열간 단조 알루미늄 Unibody 디자인.      |\n| 비범한 프로 역량을 위한 탄생.            |\n| IPhone 17 Pro &                   |\n|           IPhone 17 Pro Max       |\n-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n");
            try {
                Thread.sleep(30000); // 30초마다 반복
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

class Premium implements Runnable {
    @Override
    public void run() {
        {  try {
            Thread.sleep(50000);
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        for (int i=0; i<2; i++) {
            if (Thread.currentThread().isInterrupted()) return;
                System.out.printf("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n| 잠깐! 혹시 반복되는 광고가 지겨우신가요?                             |\n| Apple Premium 구독자라면 광고에 스트레스 받지 않고 쇼핑할 수 있어요     |\n| Apple Premium을 구독하려면 계산대에서 ApplePremium을 입력하세요      |\n-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n");
                try {
                    Thread.sleep(50000);
                }catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}
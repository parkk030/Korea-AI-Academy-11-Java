package com.korai;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // 1. 구입 금액 입력
        System.out.println("===========================================");
        System.out.println("      🎰 로또 다중 자동 구매 시뮬레이터 🎰      ");
        System.out.println("===========================================");
        System.out.print("구입할 금액을 입력하세요 (장당 1,000원, 예: 5000): ");
        int money = scanner.nextInt();

        int ticketCount = money / 1000;
        if (ticketCount <= 0) {
            System.out.println("⚠️ 최소 1,000원 이상 입력해야 구매할 수 있습니다.");
            scanner.close();
            return;
        }

        System.out.printf("\n총 %d장을 자동으로 발급합니다.\n", ticketCount);

        // 2. 입력받은 장수만큼 자동 로또 티켓 생성
        List<Set<Integer>> myTickets = new ArrayList<>();
        for (int i = 0; i < ticketCount; i++) {
            myTickets.add(generateLottoNumbers(random));
        }

        // 발급된 번호 출력
        System.out.println("-------------------------------------------");
        for (int i = 0; i < ticketCount; i++) {
            System.out.printf("[%02d번 티켓] %s\n", (i + 1), myTickets.get(i));
        }
        System.out.println("-------------------------------------------");

        // 3. 당첨 번호 및 보너스 번호 추첨
        Set<Integer> winningNumbers = generateLottoNumbers(random);
        int bonusNumber;
        while (true) {
            bonusNumber = random.nextInt(45) + 1;
            if (!winningNumbers.contains(bonusNumber)) {
                break;
            }
        }

        System.out.println("\n🎉 이번 주 로또 6/45 당첨 번호 추첨 결과 🎉");
        System.out.println("당첨 번호  : " + winningNumbers + " + [보너스: " + bonusNumber + "]");
        System.out.println("===========================================");

        // 4. 각 티켓별 등수 판정 및 전체 통계 집계
        int count1st = 0, count2nd = 0, count3rd = 0, count4th = 0, count5th = 0, countFail = 0;
        long totalPrize = 0L;

        System.out.println("📋 [티켓별 당첨 결과]");
        for (int i = 0; i < ticketCount; i++) {
            Set<Integer> ticket = myTickets.get(i);

            // 일치하는 번호 세기
            int matchCount = 0;
            List<Integer> matchedList = new ArrayList<>();
            for (int num : ticket) {
                if (winningNumbers.contains(num)) {
                    matchCount++;
                    matchedList.add(num);
                }
            }
            boolean hasBonus = ticket.contains(bonusNumber);

            // 등수 판정 및 통계 누적
            String rankResult;
            if (matchCount == 6) {
                rankResult = "🏆 1등 당첨! (약 20억원)";
                totalPrize += 2_000_000_000L;
                count1st++;
            } else if (matchCount == 5 && hasBonus) {
                rankResult = "🥈 2등 당첨! (약 5,000만원)";
                totalPrize += 50_000_000L;
                count2nd++;
            } else if (matchCount == 5) {
                rankResult = "🥉 3등 당첨! (약 150만원)";
                totalPrize += 1_500_000L;
                count3rd++;
            } else if (matchCount == 4) {
                rankResult = "🎖️ 4등 당첨! (50,000원)";
                totalPrize += 50_000L;
                count4th++;
            } else if (matchCount == 3) {
                rankResult = "🎟️ 5등 당첨! (5,000원)";
                totalPrize += 5_000L;
                count5th++;
            } else {
                rankResult = "💧 낙첨";
                countFail++;
            }

            System.out.printf("[%02d번 티켓] 일치: %d개 %-15s -> %s\n",
                    (i + 1), matchCount, matchedList, rankResult);
        }

        // 5. 최종 통계 및 수익률 출력
        System.out.println("===========================================");
        System.out.println("📊 [최종 집계 통계]");
        System.out.printf("- 1등 (6개 일치)        : %d개\n", count1st);
        System.out.printf("- 2등 (5개 + 보너스)    : %d개\n", count2nd);
        System.out.printf("- 3등 (5개 일치)        : %d개\n", count3rd);
        System.out.printf("- 4등 (4개 일치)        : %d개\n", count4th);
        System.out.printf("- 5등 (3개 일치)        : %d개\n", count5th);
        System.out.printf("- 꽝   (2개 이하)       : %d개\n", countFail);
        System.out.println("-------------------------------------------");
        System.out.printf("총 투자 금액 : %,d원\n", ticketCount * 1000);
        System.out.printf("총 당첨 금액 : %,d원\n", totalPrize);

        double yield = ((double) totalPrize / (ticketCount * 1000)) * 100;
        System.out.printf("총 수익률     : %.2f%%\n", yield);
        System.out.println("===========================================");

        scanner.close();
    }

    /**
     * 1부터 45 사이의 중복 없는 번호 6개를 생성하여 반환 (자동 정렬)
     */
    private static Set<Integer> generateLottoNumbers(Random random) {
        Set<Integer> numbers = new TreeSet<>();
        while (numbers.size() < 6) {
            numbers.add(random.nextInt(45) + 1);
        }
        return numbers;
    }
}
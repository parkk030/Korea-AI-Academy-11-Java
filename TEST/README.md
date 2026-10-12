# Java 21 Smart GUI Calculator

**JDK 21 (Java 21)** 최신 문법(`sealed interface`, `record`, Pattern Matching `switch`)과 `BigDecimal` 고정밀 연산을 활용하여 제작한 **모던 스윙(Swing) GUI 계산기 프로젝트**입니다.

---

## 주요 기능

1. **사칙연산 및 연속 계산 (`BigDecimal` 16자리 정밀 연산)**
   - 부동소수점 오차(`0.1 + 0.2 = 0.30000000000000004`) 없이 정확한 십진 연산 수행
   - 천 단위 구분 기호(`,`) 자동 포맷팅 및 글자 수에 따른 디스플레이 폰트 크기 자동 조절
   - 연속 사칙연산(`10 + 20 × 3`) 및 반복 등호(`=`) 연산 지원
2. **단항 공학/보조 연산**
   - 백분율(`%`), 역수(`1/x`), 제곱(`x²`), 제곱근(`√x`), 부호 변환(`±`)
   - 0으로 나누기 및 음수 제곱근 시 안전한 예외 메시지 표시 및 다음 숫자 입력 시 즉시 복구
3. **계산 기록(History) 사이드바 & 메모리(`MC`, `MR`, `M+`, `M-`, `MS`)**
   - 우측 상단 `🕘 기록` 버튼으로 계산 히스토리 사이드바 열기/닫기
   - 이전 계산 기록 카드를 클릭하면 해당 결과값을 즉시 현재 계산기로 불러오기
   - 메모리 저장/가감/호출/초기화 및 상단 디스플레이 `[M]` 상태 배지 표시
4. **다크 / 라이트 테마 전환 & 키보드 단축키 지원**
   - 상단 `☀ 라이트` / `🌙 다크` 버튼으로 즉시 테마 전환
   - 키보드 넘패드 및 단축키 완벽 연동 (입력 시 버튼 눌림 애니메이션 피드백 제공)

---

## 키보드 단축키 안내

| 키보드 입력 | 동작 |
| :---: | :--- |
| `0` ~ `9`, `.` | 숫자 및 소수점 입력 |
| `+`, `-`, `*`, `/` | 사칙연산 (`+`, `−`, `×`, `÷`) |
| `Enter` 또는 `=` | 계산 결과 도출 (`=`) |
| `Backspace` | 마지막 입력 숫자 지우기 (`⌫`) |
| `Delete` | 현재 입력값 초기화 (`CE`) |
| `ESC` | 전체 초기화 (`C`) |
| `%` | 백분율 계산 (`%`) |
| `F9` | 양수/음수 부호 전환 (`±`) |

---

## 프로젝트 구조

```text
TEST/
├── src/com/calc/
│   ├── Main.java                   # 애플리케이션 진입점 (Swing EDT & 안티앨리어싱 설정)
│   ├── CalculatorEngineTest.java   # 계산 엔진 자동 검증 테스트 러너
│   ├── model/
│   │   ├── Operator.java           # 이항/단항 연산자 Enum 및 Java 21 Switch 표현식
│   │   ├── CalculationResult.java  # Java 21 sealed interface & record (Success / Error)
│   │   ├── HistoryItem.java        # Java 21 record 기반 계산 기록 불변 모델
│   │   └── CalculatorEngine.java   # BigDecimal 정밀 계산, 메모리, 히스토리 핵심 엔진
│   └── ui/
│       ├── ThemePalette.java       # Java 21 record 기반 다크/라이트 테마 컬러 팔레트
│       ├── RoundedButton.java      # Graphics2D 안티앨리어싱 커스텀 둥근 버튼
│       ├── DisplayPanel.java       # 수식 미리보기, 메모리 배지, 동적 폰트 디스플레이
│       ├── HistoryPanel.java       # 계산 기록 사이드 패널 (클릭 복원 및 삭제)
│       └── CalculatorFrame.java    # 메인 윈도우 레이아웃, 이벤트 및 키보드 단축키 바인딩
├── run.bat                         # 원클릭 JDK 21 컴파일 및 실행 스크립트
└── README.md                       # 프로젝트 문서
```

---

## 실행 방법

### 1. 배치 스크립트로 바로 실행 (권장)
프로젝트 루트 폴더에서 `run.bat`을 실행합니다.
```bat
.\run.bat
```

### 2. 수동 컴파일 및 실행 (JDK 21)
```powershell
javac --release 21 -encoding UTF-8 -d bin src/com/calc/*.java src/com/calc/model/*.java src/com/calc/ui/*.java
java -cp bin com.calc.Main
```

### 3. 단위 테스트 실행
```powershell
java -cp bin com.calc.CalculatorEngineTest
```

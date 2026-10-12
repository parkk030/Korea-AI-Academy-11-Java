# 🎮 공피하기 게임 (Ball Dodging Game)

Java (JDK 21) 및 Java Swing GUI를 기반으로 제작된 레트로 아케이드 스타일의 공피하기 게임입니다.  
사방에서 튕겨 다니는 다양한 장애물 공들을 피하며 최대한 오래 생존하여 최고 기록을 달성해 보세요!

---

## 🌟 주요 특징

1. **부드러운 조작감 & 8방향 대각선 이동**:
   - 상/하/좌/우 방향키(↑, ↓, ←, →) 및 W, A, S, D 지원
   - 다중 키 동시 입력을 처리하여 키 씹힘 현상이 없으며, 대각선 이동 시 속도 정규화를 적용하여 균일한 이동 속도 보장
   - 플레이어 이동 시 푸른 네온 잔상(트레일) 효과 적용

2. **다이내믹 물리 & 장애물 공(Obstacle Ball)**:
   - 벽면 탄성 반사(Bouncing) 물리 로직
   - 다채로운 네온 컬러와 입체감 있는 하이라이트/글로우 렌더링
   - 플레이어와 안전 거리를 확보하여 불합리한 시작 사망 방지

3. **정밀 충돌 판정 (Collision Detection)**:
   - 플레이어와 장애물 공의 원형 히트박스(Euclidean Distance) 기반의 정밀 충돌 판정

4. **점진적 난이도 조절 (Dynamic Difficulty)**:
   - 생존 시간에 따라 레벨이 올라가며, 추가 공이 스폰되고 이동 속도가 점차 빨라집니다.

5. **완성도 높은 아케이드 GUI**:
   - 실시간 생존 시간, 획득 점수, 현재 레벨/공 개수, 최고 기록(High Score) HUD
   - 일시정지(`P` / `ESC`) 기능
   - 충돌 시 게임 오버 화면 및 `R` 키 즉시 재시작 지원

---

## 🕹️ 조작법

| 키 입력 | 기능 |
|---|---|
| `↑` `↓` `←` `→` 또는 `W` `A` `S` `D` | 플레이어 8방향 이동 |
| `SPACE` / `ENTER` | 게임 시작 / 재도전 |
| `P` / `ESC` | 일시정지(Pause) / 재개(Resume) |
| `R` | 게임 오버 시 즉시 재시작 |

---

## 📁 프로젝트 구조

```
Ball-Dodging-Game/
├── .gitignore                      # Git 추적 제외 설정
├── README.md                       # 프로젝트 문서
├── run.bat                         # 원클릭 컴파일 및 실행 스크립트
└── src/
    └── com/
        └── dodge/
            ├── Main.java           # 애플리케이션 진입점
            ├── model/
            │   ├── Player.java     # 플레이어 모델 (위치, 이동, 모션 잔상)
            │   ├── Ball.java       # 장애물 공 모델 (물리 반사, 속도, 크기)
            │   └── GameState.java  # 게임 상태 (READY, PLAYING, PAUSED, GAME_OVER)
            └── ui/
                ├── GameWindow.java # JFrame 메인 윈도우
                └── GamePanel.java  # 게임 루프, HUD, 충돌 감지 및 렌더링
```

---

## 🚀 실행 방법

### 방법 1. 원클릭 실행 (Windows 배치 파일)
프로젝트 루트 경로에서 `run.bat`을 더블 클릭하거나 명령 프롬프트/PowerShell에서 실행합니다:
```cmd
run.bat
```

### 방법 2. 콘솔에서 직접 컴파일 및 실행 (JDK 21)
```powershell
# 1. 컴파일 (bin 폴더에 바이트코드 생성)
& "C:\Users\Admin\.jdks\temurin-21.0.12.1\bin\javac.exe" -encoding UTF-8 -d bin (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object FullName)

# 2. 실행
& "C:\Users\Admin\.jdks\temurin-21.0.12.1\bin\java.exe" -cp bin com.dodge.Main
```

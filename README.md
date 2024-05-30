# 🕹️ Tetris Game

---

- software-engineering 12팀

## ☑️ **소개**

이 프로젝트는 텍스트 기반의 테트리스 게임입니다. Java와 JavaSwing 라이브러리를 이용하여 구현되었습니다.

## ☑️ 기능

### 기본 모드

- 기본 모드는 테트리스의 전통적인 방식입니다. 플레이어는 떨어지는 블록을 조작하여 수평선 한 줄을 채우는 것을 목표로 합니다. 완전히 채워진 줄은 사라지고, 플레이어는 점수를 얻게 됩니다. 게임은 블록이 더 이상 보드에 적절히 배치될 공간이 없을 때 종료됩니다.

### 아이템 모드

| 아이템 | 효과 | 적용 메서드 |
| --- | --- | --- |
| Weight 아이템 | 해당 모양의 블록이 떨어질 때, 수직 방향으로 모든 블록을 제거합니다. | deleteWeightItem() |
| LineDel 아이템 | 해당 블록을 중심으로 좌우로 모든 블록을 제거합니다. | applyLineDelItem() |
| Three 아이템 | 해당 블록을 중심으로 3x3 범위의 주변 블록을 삭제합니다. | applyThreeItem() |
| Five 아이템 | 해당 블록을 중심으로 5x5 범위의 주변 블록을 삭제합니다. | applyFiveItem() |
| Seven 아이템 | 해당 블록을 중심으로 7x7 범위의 주변 블록을 삭제합니다. | applySevenItem() |
| Plus 아이템 | 해당 블록을 중심으로 가로 방향과 세로 방향으로 한 줄씩 제거합니다. | applyPlusItem() |
| AllDel 아이템 | 게임판 상의 모든 블록을 제거합니다. | applyAllDelItem() |

### 타이머 모드

- 타이머 모드는 제한된 시간 내에 가능한 많은 점수를 얻는 것을 목표로 합니다. 게임 모드와 난이도에 따라 타이머가 설정되며, 시간 내에 최대한 많은 줄을 제거하고 높은 점수를 얻는 것이 목표입니다. 시간이 다 되면 게임이 자동으로 종료됩니다.

### 대전 모드

- 대전 모드는 두 명의 플레이어가 동시에 게임을 즐길 수 있는 모드입니다. 각 플레이어는 자신의 게임 보드를 가지고 있으며, 상대방의 보드에 공격을 가할 수 있습니다. 2줄 이상을 제거하는 조건을 충족하면 상대방의 보드에 해당 추가적인 블록을 생성하여 상대방을 방해할 수 있습니다. 더 이상 블럭을 쌓을 수 없는 플레이어가 패배합니다.

### 다양한 난이도 설정 (Easy, Normal, Hard)

### 점수 시스템

- 점수 시스템은 플레이어가 줄을 제거할 때마다 점수를 부여합니다. 제거된 줄의 수에 따라 점수가 다르게 부여됩니다:
    - 1줄 제거: 10점
    - 2줄 제거: 20점
    - 3줄 제거: 30점
    - 4줄 제거: 40점
    - 아이템 효과나 빠르게 떨어뜨리는 등의 추가 점수도 포함됩니다.
    게임이 끝나면 점수가 저장되고, 최고 점수를 기록할 수 있습니다.

### **게임 일시정지 및 재개**

- **`P`** 키
    - 게임 도중 언제든지 **`P`** 키를 눌러 게임을 일시정지할 수 있습니다. 일시정지 상태에서는 게임이 멈추고, 다시 **`P`** 키를 누르면 게임이 재개됩니다. 일시정지는 점수 계산이나 타이머 진행에 영향을 주지 않습니다.
- **`ESC`** 키
    - 게임 도중 언제든지 **`ESC`** 키를 누르면 *취소, 타이틀로 돌아가기, 종료* 버튼 중에서 선택할 수 있습니다.

### **키보드 조작**

- 게임은 키보드로 조작할 수 있습니다. 조작키는 wasd키와 방향키 중 선택할 수 있습니다. 기본 키보드 설정은 다음과 같습니다:

**WASD키/Q, P키**

- **`W`**: 블록을 회전합니다.
- **`A`**: 블록을 왼쪽으로 이동합니다.
- **`S`**: 블록을 한 줄 아래로 이동합니다.
- **`D`**: 블록을 오른쪽으로 이동합니다.
- **`Q`**: 블록을 맨 아래로 즉시 떨어뜨립니다.
- **`P`**: 게임을 일시정지/재개합니다.

이외에도 이동을 위한 키는 방향키로 변경할 수 있습니다.

**대전모드 조작키**

- 대전모드 조작키는 player1, player2에 따라 다릅니다.
    - player1 : 이동/회전 - wasd키, 한번에 내리기 - q키
    - player2 : 이동/회전 - 방향키, 한번에 내리기 shift(우)키

## ☑️ UI

<img src="https://github.com/Software-engineering-12-team/Tetris-game-12/assets/90715224/b552771d-6268-43ef-9098-fb435fc6c4af" width="330" height="450">
<img src="https://github.com/Software-engineering-12-team/Tetris-game-12/assets/90715224/84c6fb1b-2a55-4f7a-a608-8d97ebbaf9a6" width="330" height="450">
<img src="https://github.com/Software-engineering-12-team/Tetris-game-12/assets/90715224/c2239e10-62aa-44fe-97aa-e176e0273a4c" width="330" height="450">

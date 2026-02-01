# Score4_Game (Java Console)

A console-based implementation of **Score4 (Connect-4 style)** written in **Java**.  
The game supports **2 to 4 players** (turns are played cyclically), uses different board sizes depending on the number of players, and detects wins **horizontally, vertically, and diagonally**.

---

## Features

- ✅ **2–4 players** (cyclic turns)
- ✅ Dynamic board size:
  - **2 players:** 6 × 7  
  - **3 players:** 7 × 9  
  - **4 players:** 7 × 12
- ✅ Player tokens (symbols): `X`, `O`, `#`, `$`
- ✅ Empty cells shown as `.`
- ✅ Column numbering starts from **1** (user-friendly input)
- ✅ Input validation:
  - Non-numeric input rejected
  - Out-of-range columns rejected
  - Full columns rejected
- ✅ Win detection:
  - Horizontal
  - Vertical
  - Diagonal (`/` and `\`)
- ✅ End-of-game statistics:
  - Total moves played
  - Full rounds completed
- ✅ Restart option (start a new game without closing the program)

---

## How to Run

### Requirements
- Java **JDK 8+** (recommended: JDK 17+)

### Compile
From the project folder:

```bash
javac Score4_Game.java

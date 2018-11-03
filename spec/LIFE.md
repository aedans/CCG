Life
====

Players start the game with 15 life.

If at any point a player's life drops to 0, that player
loses the game.

Formally
========

```
GameStart:
    AllPlayers (SetHealth 15)

Constant:
    AllPlayers (if Zero Health then Lose)
```

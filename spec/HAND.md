Hand
====

Your hand contains all currently playable cards.

Players start with 3 starting cards and 3 cards from their deck.

At the start of each player's turn, they draw a card.

Formally
========

```
GameStart:
    DrawStartingCards
    AllPlayers (Draw 3)

TurnStart:
    ActivePlayer (Draw 1)
```

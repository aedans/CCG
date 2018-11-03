Mana
====

Both players gain 1 mana every third turn.

Formally
========

```
TurnStart:
    if Divides 3 TurnNumber
    then AllPlayers (AddMana 1)
```

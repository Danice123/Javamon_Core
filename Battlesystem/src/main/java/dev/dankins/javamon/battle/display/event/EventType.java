package dev.dankins.javamon.battle.display.event;

public enum EventType {

	Cancel,

	StartBattle, EscapeFailed, EscapeSuccess, TrainerLoss, EndBattle,

	SendMonster, ReturnMonster, FaintMonster, CannotSwitchToFaintedMonster,

	Attack, UpdateHealth, AttackFailed, AttackMissed, AttackDisplay, CriticalHit, TypeEffectiveness, TypeChange,

}

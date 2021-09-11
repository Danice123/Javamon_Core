package dev.dankins.javamon.battle.display.event;

public enum EventType {

	Cancel,

	StartBattle, EscapeFailed, EscapeSuccess, TrainerLoss, EndBattle, ExpGain, LevelUp, LearnNewAttack, WonMoney,

	SendMonster, ReturnMonster, FaintMonster, CannotSwitchToFaintedMonster,

	Attack, UpdateHealth, AttackFailed, AttackMissed, AttackDisplay, CriticalHit, TypeEffectiveness, TypeChange,

}

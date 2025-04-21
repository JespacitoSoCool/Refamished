package net.fabricmc.refamished.quality;

public class QualityEffect {
	private final float digSpeedMultiplier;
	private final float reachAdjustment;
	private final float attackDamageBonus;

	public QualityEffect(float digSpeedMultiplier, float reachAdjustment, float attackDamageBonus) {
		this.digSpeedMultiplier = digSpeedMultiplier;
		this.reachAdjustment = reachAdjustment;
		this.attackDamageBonus = attackDamageBonus;
	}

	public float getDigSpeedMultiplier() {
		return digSpeedMultiplier;
	}

	public float getReachAdjustment() {
		return reachAdjustment;
	}

	public float getAttackDamageBonus() {
		return attackDamageBonus;
	}
}

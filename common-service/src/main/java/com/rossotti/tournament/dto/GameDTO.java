package com.rossotti.tournament.dto;

public class GameDTO {
	private int preliminaryPerRound;
	private int finalPerRound;
	private int dayHalf;
	private int dayFull;

	public int getPreliminaryPerRound() {
		return preliminaryPerRound;
	}
	public void setPreliminaryPerRound(int preliminaryPerRound) {
		this.preliminaryPerRound = preliminaryPerRound;
	}

	public int getFinalPerRound() {
		return finalPerRound;
	}
	public void setFinalPerRound(int finalPerRound) {
		this.finalPerRound = finalPerRound;
	}

	public int getDayHalf() {
		return dayHalf;
	}
	public void setDayHalf(int dayHalf) {
		this.dayHalf = dayHalf;
	}

	public int getDayFull() {
		return dayFull;
	}
	public void setDayFull(int dayFull) {
		this.dayFull = dayFull;
	}
}
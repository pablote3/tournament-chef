package com.rossotti.tournament.dto;

public class GameDTO {
	private int preliminaryPerRound;
	private int finalPerRound;
	private int total;

	public GameDTO() {
	}

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

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
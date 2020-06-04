package com.rossotti.tournament.dto;

public class RoundDTO {
	private int preliminary;
	private boolean playoff1;
	private boolean playoff2;
	private boolean quarterFinal;
	private boolean semiFinal;
	private boolean championship;
	private int dayHalf;
	private int dayFull;

	public RoundDTO() {
	}

	public RoundDTO(int preliminary, boolean quarterFinal, boolean semiFinal, boolean championship, int dayHalf, int dayFull) {
		this.preliminary = preliminary;
		this.quarterFinal = quarterFinal;
		this.semiFinal = semiFinal;
		this.championship = championship;
		this.dayHalf = dayHalf;
		this.dayFull = dayFull;
	}

	public int getPreliminary() {
		return preliminary;
	}
	public void setPreliminary(int preliminary) {
		this.preliminary = preliminary;
	}

	public boolean getPlayoff1() {
		return playoff1;
	}
	public void setPlayoff1(boolean playoffs) {
		this.playoff1 = playoff1;
	}

	public boolean getPlayoff2() {
		return playoff2;
	}
	public void setPlayoff2(boolean playoff2) {
		this.playoff2 = playoff2;
	}

	public boolean getQuarterFinal() {
		return quarterFinal;
	}
	public void setQuarterFinal(boolean quarterFinal) {
		this.quarterFinal = quarterFinal;
	}

	public boolean getSemiFinal() {
		return semiFinal;
	}
	public void setSemiFinal(boolean semiFinal) {
		this.semiFinal = semiFinal;
	}

	public boolean getChampionship() {
		return championship;
	}
	public void setChampionship(boolean championship) {
		this.championship = championship;
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
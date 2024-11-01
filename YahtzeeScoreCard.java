import java.util.Arrays;

/**
 *	YahtzeeScoreCard.java
 *	Fills the scorecard method.
 *
 *	@author	Ani Kumar
 *	@since	October 23, 2024
 */

public class YahtzeeScoreCard {
	private int[] scores;

	/* Constructor that initializes array of scores */
	public YahtzeeScoreCard() {
		scores = new int[13];
		Arrays.fill(scores, -1); // init. to -1 to indicate each score isn't occupied yet
	}

	/* Prints scorecard header with category names */
	public void printCardHeader() {
		System.out.println("\n");
		System.out.printf("\t\t\t\t\t       3of  4of  Fll Smll Lrg\n");
		System.out.printf("  NAME\t\t  1    2    3    4    5    6   Knd  Knd  Hse " +
				"Strt Strt Chnc Ytz!\n");
		System.out.printf("+----------------------------------------------------" +
				"---------------------------+\n");
	}

	/* Prints the player's score */
	public void printPlayerScore(YahtzeePlayer player) {
		System.out.printf("| %-12s |", player.getName());
		for (int i = 1; i < 14; i++) {
			if (getScore(i) > -1)
				System.out.printf(" %2d |", getScore(i));
			else System.out.printf("    |");
		}
		System.out.println();
		System.out.printf("+----------------------------------------------------" +
				"---------------------------+\n");
	}

	/* Getter that returns score of a certain category */
	public int getScore(int category) {
		return scores[category - 1];
	}

	public int getTotalScore() {
		int total = 0;
        for (int score : scores)
			total += score;
		return total;
	}

	/**
	 * Change the scorecard based on the category choice 1-13
	 *
	 * @param choice The choice of the player 1 to 13
	 * @param dg     The DiceGroup to score
	 * @return true if change succeeded. Returns false if choice already taken.
	 */
	public boolean changeScore(int choice, DiceGroup dg) {
		if (getScore(choice) != -1) // choice already taken
			return false;
		switch (choice) {
			case 1: // ones
				numberScore(1, dg);
				break;
			case 2: // twos
				numberScore(2, dg);
				break;
			case 3: // threes
				numberScore(3, dg);
				break;
			case 4: // fours
				numberScore(4, dg);
				break;
			case 5: // fives
				numberScore(5, dg);
				break;
			case 6: // sixes
				numberScore(6, dg);
				break;
			case 7: // three of a kind
				threeOfAKind(dg);
				break;
			case 8: // four of a kind
				fourOfAKind(dg);
				break;
			case 9: // full house
				fullHouse(dg);
				break;
			case 10: // small straight
				smallStraight(dg);
				break;
			case 11: // large straight
				largeStraight(dg);
				break;
			case 12: // chance
				chance(dg);
				break;
			case 13: // Yahtzee
				yahtzeeScore(dg);
				break;
		}
		return true; // change succeeded
	}

	/**
	 * Change the scorecard for a number score 1 to 6
	 *
	 * @param choice The choice of the player 1 to 6
	 * @param dg     The DiceGroup to score
	 */
	public void numberScore(int choice, DiceGroup dg) {
		int score = 0;
		for (int i = 0; i < dg.getDieLength(); i++) {
			if (dg.getDieValue(i) == choice)
				score += choice;
		}
		scores[choice - 1] = score;
	}

	/**
	 * Updates the scorecard for Three Of A Kind choice
	 *
	 * @param dg The DiceGroup to score
	 */
	public void threeOfAKind(DiceGroup dg) {
		int[] valCounts = new int[6];
		for (int i = 0; i < dg.getDieLength(); i++)
			valCounts[dg.getDieValue(i) - 1]++; // count occurences of each value
        for (int valCount : valCounts) {
            if (valCount >= 3) {
                scores[6] = dg.getTotal();
                return;
            }
        }
		scores[6] = 0;
	}

	/**
	 * Updates the scorecard for Four Of A Kind choice
	 *
	 * @param dg The DiceGroup to score
	 */
	public void fourOfAKind(DiceGroup dg) {
		int[] valCounts = new int[6];
		for (int i = 0; i < dg.getDieLength(); i++)
			valCounts[dg.getDieValue(i) - 1]++; // count occurences of each value
        for (int valCount : valCounts) {
            if (valCount >= 4) {
                scores[7] = dg.getTotal();
                return;
            }
        }
		scores[7] = 0;
	}

	/**
	 * Updates the scorecard for Full House choice
	 *
	 * @param dg The DiceGroup to score
	 */
	public void fullHouse(DiceGroup dg) {
		int[] valCounts = new int[6];
		boolean two, three;
		two = three = false;
		for (int i = 0; i < dg.getDieLength(); i++)
			valCounts[dg.getDieValue(i) - 1]++; // count occurences of each value
        for (int valCount : valCounts) {
            if (valCount == 2)
                two = true;
            if (valCount == 3)
                three = true;
        }
		if (two && three)
			scores[8] = 25;
		else
			scores[8] = 0;
	}

	/**
	 * Updates the scorecard for Small Straight choice
	 *
	 * @param dg The DiceGroup to score
	 */
	public void smallStraight(DiceGroup dg) {
		boolean[] exists = new boolean[6];
		for (int i = 0; i < dg.getDieLength(); i++)
			exists[dg.getDieValue(i) - 1] = true;
		if ((exists[0] && exists[1] && exists[2] && exists[3]) ||
				(exists[1] && exists[2] && exists[3] && exists[4]) ||
				(exists[2] && exists[3] && exists[4] && exists[5])) {
			scores[9] = 30;
		} else
			scores[9] = 0;
	}
	/**
	 * Updates the scorecard for Large Straight choice
	 *
	 * @param dg The DiceGroup to score
	 */
	public void largeStraight(DiceGroup dg) {
		boolean[] exists = new boolean[6];
		for (int i = 0; i < dg.getDieLength(); i++)
			exists[dg.getDieValue(i) - 1] = true;
		if ((exists[0] && exists[1] && exists[2] && exists[3] && exists[4]) ||
				(exists[1] && exists[2] && exists[3] && exists[4] && exists[5])) {
			scores[10] = 40;
		} else
			scores[10] = 0;
	}

	/**
	 * Updates the scorecard for Chance choice
	 *
	 * @param dg The DiceGroup to score
	 */
	public void chance(DiceGroup dg) {
		scores[11] = dg.getTotal();
	}

	/**
	 * Updates the scorecard for Yahtzee choice
	 *
	 * @param dg The DiceGroup to score
	 */
	public void yahtzeeScore(DiceGroup dg) {
		int valFirst = dg.getDieValue(0);
		for (int i = 1; i < dg.getDieLength(); i++) {
			if (dg.getDieValue(i) != valFirst) {
				scores[12] = 0;
				return;
			}
		}
		scores[12] = 50;
	}
}
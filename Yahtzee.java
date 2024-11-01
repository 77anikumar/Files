/**
 *	Yahtzee.java
 *	Two players roll dice and decide scoring categories while trying to
 * 	fill up these categories and score the highest point total.
 *
 *	@author	Ani Kumar
 *	@since	October 23, 2024
 */

public class Yahtzee {
	Dice dice;
	DiceGroup dicegroup;
	YahtzeeScoreCard yscPlayer1, yscPlayer2;
	YahtzeePlayer player1, player2;
	private final int NUM_TURNS = 13;
	private final int NUM_ROLLS = 2;

	/* Constructor that initializes fields */
	public Yahtzee() {
		dice = new Dice();
		dicegroup = new DiceGroup();
		yscPlayer1 = new YahtzeeScoreCard();
		yscPlayer2 = new YahtzeeScoreCard();
		player1 = new YahtzeePlayer();
		player2 = new YahtzeePlayer();
	}

	/* Main method that executes the program */
	public static void main(String[] args) {
		Yahtzee yt = new Yahtzee();
		yt.runner();
	}

	/* Runner method that calls other methods to print and play game */
	public void runner() {
		printHeader();
		chooseNamesAndFirst();
		printCard();
		playGame();
		endGame();
	}

	/* Prints game header at start */
	public void printHeader() {
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("| WELCOME TO MONTA VISTA YAHTZEE!                                                    |");
		System.out.println("|                                                                                    |");
		System.out.println("| There are 13 rounds in a game of Yahtzee. In each turn, a player can roll his/her  |");
		System.out.println("| dice up to 3 times in order to get the desired combination. On the first roll, the |");
		System.out.println("| player rolls all five of the dice at once. On the second and third rolls, the      |");
		System.out.println("| player can roll any number of dice he/she wants to, including none or all of them, |");
		System.out.println("| trying to get a good combination.                                                  |");
		System.out.println("| The player can choose whether he/she wants to roll once, twice or three times in   |");
		System.out.println("| each turn. After the three rolls in a turn, the player must put his/her score down |");
		System.out.println("| on the scorecard, under any one of the thirteen categories. The score that the     |");
		System.out.println("| player finally gets for that turn depends on the category/box that he/she chooses  |");
		System.out.println("| and the combination that he/she got by rolling the dice. But once a box is chosen  |");
		System.out.println("| on the score card, it can't be chosen again.                                       |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME YAHTZEE!                                                           |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n\n");
	}

	/* Prompts players for their names and decides who plays first */
	public void chooseNamesAndFirst() {
		boolean askOneAgain, askTwoAgain, redo;
		String p1Name, p2Name, name;
		p1Name = p2Name = name = "";
		askOneAgain = askTwoAgain = redo = true;
		while (askOneAgain) {
			p1Name = Prompt.getString("Player 1, please enter your first name");
			if (!p1Name.isEmpty()) {
				player1.setName(p1Name);
				askOneAgain = false;
			}
		}
		System.out.println();
		while (askTwoAgain) {
			p2Name = Prompt.getString("Player 2, please enter your first name");
			if (!p2Name.isEmpty()) {
				player2.setName(p2Name);
				askTwoAgain = false;
			}
		}
		while (redo) {
			Prompt.getString("\nLet's see who will go first. " + p1Name +
					", please hit enter to roll the dice");
			dicegroup.rollDice();
			dicegroup.printDice();
			int p1Sum = dicegroup.getTotal();
			Prompt.getString(p2Name + ", it's your turn. Please hit enter to roll the dice");
			dicegroup.rollDice();
			dicegroup.printDice();
			int p2Sum = dicegroup.getTotal();
			System.out.println(p1Name+", you rolled a sum of "+p1Sum+", and "
					+p2Name+", you rolled a sum of "+p2Sum+".");
			if (p1Sum != p2Sum) {
				if (p1Sum > p2Sum)
					name = p1Name;
				else
					name = p2Name;
				redo = false;
			} else
				System.out.println("\nYou both rolled the same sum! Try again.");
		}
		System.out.print(name+", since your sum was higher, you'll roll first.");
	}

	/* Prints scorecard with up-to-date scores */
	public void printCard() {
		yscPlayer1.printCardHeader();
		yscPlayer1.printPlayerScore(player1);
		yscPlayer2.printPlayerScore(player2);
	}

	/* Iterates through both players' turns 13 times */
	public void playGame() {
		for (int i = 1; i <= NUM_TURNS; i++) {
			System.out.print("\nRound " + i + " of 13 rounds.\n\n");
			playTurn(player1);
			playTurn(player2);
		}
	}

	public void endGame() {
		int p1TotalScore = yscPlayer1.getTotalScore();
		int p2TotalScore = yscPlayer2.getTotalScore();
		String name;
		System.out.println(player1.getName() + "\t\tscore total = " + p1TotalScore);
		System.out.println(player2.getName() + "\t\tscore total = " + p2TotalScore);
		if (p1TotalScore != p2TotalScore) {
			if (p1TotalScore > p2TotalScore)
				name = player1.getName();
			else
				name = player2.getName();
			System.out.println("Congratulations " + name + ". YOU WON!!!");
		} else System.out.println("Congratulations both of you. YOU TIED!!!");
		System.exit(0);
	}

	/* Plays one turn by calling other methods for dice and scorecard */
	public void playTurn(YahtzeePlayer player) {
		Prompt.getString(player.getName() + ", it's your turn to play. Please hit enter to roll the dice");
		dicegroup.rollDice();
		dicegroup.printDice();
		holdDice();
		printCard();
		if (player == player1)
			chooseCategory(yscPlayer1, player);
		else
			chooseCategory(yscPlayer2, player);
		printCard();
	}

	/* Prompts current player to hold certain dice */
	public void holdDice() {
		for (int i = 1; i <= NUM_ROLLS; i++) { // allows up to 2 re-rolls
			int hold = Prompt.getInt("Which di(c)e would you like to keep? Enter the values you'd like to 'hold' without spaces. " +
					"For example, if you'd like to 'hold' die 1, 2, and 5, enter 125\n" +
					"(enter -1 if you'd like to end the turn)");
			if (hold == -1)
				break;
			dicegroup.rollDice(Integer.toString(hold));
			dicegroup.printDice();
		}
	}

	/* Prompts current player to choose scoring category */
	public void chooseCategory(YahtzeeScoreCard ysc, YahtzeePlayer player) {
		boolean redo = true;
		while (redo) {
			int choice = Prompt.getInt("\n" + player.getName() + ", now you need to make a choice. Pick a valid integer " +
						"from the list above", 1, 13);
			redo = !ysc.changeScore(choice, dicegroup);
		}
	}
}
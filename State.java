/**
 *	The object to store US state information.
 *
 *	@author	Ani Kumar
 *	@since	May 21, 2025
 */
public class State implements Comparable<State> {
	private String name;			// state name
	private String abbreviation;	// state abbreviation
	private int population;			// state population
	private int area;				// state area in sq miles
	private int reps;				// number of House Reps
	private String capital;			// state capital city
	private int month;				// month joined Union
	private int day;				// day joined Union
	private int year;				// year joined Union

	public State(String n, String a, int p, int ar, int r, String c, int m, int d, int y) {
		name = n;
		abbreviation = a;
		population = p;
		area = ar;
		reps = r;
		capital = c;
		month = m;
		day = d;
		year = y;
	}

	public int compareTo(State other) {
		return name.compareTo(other.getName());
	}
	
	public String getName () {
		return name;
	}

	public String toString() {
		return String.format("%-20s %-5s %9d %7d %3d   %-20s %2d %2d %4d%n",
				name, abbreviation, population, area, reps, capital, month, day, year);
	}
}
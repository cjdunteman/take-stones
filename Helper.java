import java.util.List;

public class Helper {

	static final int SMALLEST_PRIME = 2;

	/**
	 * Class constructor.
	 */
	private Helper() {
	}

	/**
	 * This method is used to check if a number is prime or not
	 * 
	 * @param x A positive integer number
	 * @return boolean True if x is prime; Otherwise, false
	 */
	public static boolean isPrime(int x) {
		if (x < SMALLEST_PRIME) {
			return false;
		}
		for (int i = SMALLEST_PRIME; i <= Math.sqrt(x); i++) {
			if (x % i == 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * This method is used to get the largest prime factor
	 * 
	 * @param x A positive integer number
	 * @return int The largest prime factor of x
	 */
	public static int getLargestPrimeFactor(int x) throws IllegalArgumentException {
		if (x < SMALLEST_PRIME) {
			throw new IllegalArgumentException();
		}
		int i;
		for (i = SMALLEST_PRIME; i <= x; i++) {
			if (x % i == 0) {
				x = x / i;
				i--;
			}
		}

		return i;
	}

	/**
	 * Counts the number of multiples of x in a list of Integers
	 * 
	 * @param x    the int to find multiples of
	 * @param list the list of possible multiples
	 * @return the number of multiples found
	 */
	public static int getMultiplesCount(int x, List<Integer> list) {
		int multiple = 0;

		if (list.size() < 1) {
			return multiple;
		} else if (x == 0) {
			return 0;
		}

		for (int test : list) {
			if (test % x == 0) {
				multiple += 1;
			}
		}
		return multiple;
	}
}
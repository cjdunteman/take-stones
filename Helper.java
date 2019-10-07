import java.util.List;

public class Helper {

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
		if (x < Constants.SMALLEST_PRIME) {
			return false;
		}

		double sqrt = Math.sqrt(x);
		for (int i = Constants.SMALLEST_PRIME; i <= sqrt; i++) {
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
		if (x < Constants.SMALLEST_PRIME) {
			throw new IllegalArgumentException();
		}
		int i;
		for (i = Constants.SMALLEST_PRIME; i <= x; i++) {
			if (x % i == 0) {
				x /= i;
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
		int multiples = 0;

		if (list.size() < 1) {
			return multiples;
		} else if (x == 0) {
			// Can't happen, but yeah
			return 0;
		}

		for (int test : list) {
			if (test % x == 0) {
				multiples++;
			}
		}
		return multiples;
	}
}
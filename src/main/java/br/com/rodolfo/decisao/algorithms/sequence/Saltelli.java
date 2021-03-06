package br.com.rodolfo.decisao.algorithms.sequence;

/**
 * Extension of Sobol's sequence for generating the samples for Saltelli's
 * version of Sobol' global variance decomposition. This approach allows
 * the first-, second-, and total-order sensitivities to be computed with
 * lower error rates and fewer samples than other approaches, and is the
 * sequences required for using {@link SobolAnalysis}.
 * <p>
 * The number of samples, {@code N}, generated by the {@link #generate} method
 * must be a multiple of {@code 2*D+2}, where {@code D} is the dimension of the
 * generated samples.
 * <p>
 * References:
 * <ol>
 * <li>Saltelli, A., et al. "Global Sensitivity Analysis: The Primer." John
 * Wiley & Sons Ltd, 2008.
 * </ol>
 */
public class Saltelli implements Sequence {

	/**
	 * The internal Sobol' sequence.
	 */
	private final Sobol sobol;

	/**
	 * Constructs a Saltelli sequence generator for use in Sobol' global
	 * variance decomposition.
	 */
	public  Saltelli() {
		super();

		sobol = new Sobol();
	}

	@Override
	public  double[][] generate(int N, int D) {
		if (N % (2 * D + 2) != 0) {
			throw new IllegalArgumentException("N must be a multiple of 2*D+2");
		}

		N = N / (2 * D + 2);

		double[][] sobolSequence = sobol.generate(N + 1000, 2 * D);
		double[][] saltelliSequence = new double[(2 * D + 2) * N][D];
		int index = 0;

		for (int i = 1000; i < N + 1000; i++) {
			for (int j = 0; j < D; j++) {
				saltelliSequence[index][j] = sobolSequence[i][j];
			}

			index++;

			for (int k = 0; k < D; k++) {
				for (int j = 0; j < D; j++) {
					if (j == k) {
						saltelliSequence[index][j] = sobolSequence[i][j + D];
					} else {
						saltelliSequence[index][j] = sobolSequence[i][j];
					}
				}

				index++;
			}

			for (int k = 0; k < D; k++) {
				for (int j = 0; j < D; j++) {
					if (j == k) {
						saltelliSequence[index][j] = sobolSequence[i][j];
					} else {
						saltelliSequence[index][j] = sobolSequence[i][j + D];
					}
				}

				index++;
			}

			for (int j = 0; j < D; j++) {
				saltelliSequence[index][j] = sobolSequence[i][j + D];
			}

			index++;
		}

		return saltelliSequence;
	}

}
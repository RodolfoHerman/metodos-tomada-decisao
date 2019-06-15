package br.com.rodolfo.decisao.algorithms.sequence;

public interface Sequence {

	/**
	 * Returns a {@code N x D} matrix of real numbers in the range {@code [0,
	 * 1]}.
	 * 
	 * @param N the number of sample points
	 * @param D the dimension of each sample point
	 * @return a {@code N x D} matrix of real numbers in the range {@code [0,
	 *         1]}
	 */
	public  double[][] generate(int N, int D);

	/**
	 * Mostrar a matriz com valores
	 */
	default public void mostrarMatriz(double[][] matriz) {

		for(int x = 0; x < matriz.length; x++) {
            for(int y = 0; y < matriz[0].length; y++) {

                System.out.print(matriz[x][y] + "\t\t");
            }
            System.out.println();
        }
	}

}
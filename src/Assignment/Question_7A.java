package Assignment;
/**Implement multi-threaded algorithm to multiply n*n matrix.**/
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Question_7A {

    static class MatrixMultiplication {

        /**
         * These are class instance variables that stand in for the input, output, size, and number of threads to be
         * used for the multiplication of the matrices.
         **/
        private final int[][] a;
        private final int[][] b;
        private final int[][] result;
        private final int n;
        private final int numThreads;

        /**
         * The class's constructor is shown here. The size of the matrices is determined, the result matrix is created,
         * the number of threads to be used is configured, and the instance variables are initialized with the input matrices.
         **/
        public MatrixMultiplication(int[][] a, int[][] b, int numThreads) {
            this.a = a;
            this.b = b;
            this.n = a.length;
            this.result = new int[n][n];
            this.numThreads = numThreads;
        }

        /**
         * Using the supplied number of threads, this technique multiplies matrices. It establishes an executor service with
         * the predetermined number of threads, divides the matrix into equal-sized chunks according to the number of threads,
         * generates a new WorkerThread for each chunk, and submits the WorkerThread to the executor service for execution.
         * It turns off the executor service after all threads have been sent and waits for all of the threads to finish,
         * which could take up to an hour.
         **/
        public void multiply() throws InterruptedException {
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            int chunkSize = (int) Math.ceil((double) n / numThreads);
            for (int i = 0; i < numThreads; i++) {
                int startRow = i * chunkSize;
                int endRow = Math.min(startRow + chunkSize, n);
                executor.execute(new WorkerThread(startRow, endRow));
            }
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.HOURS);
        }

        //This method returns the resulting matrix after the multiplication has been performed.
        public int[][] getResult() {
            return result;
        }

        /**
         * This is an inner class that represents a worker thread. It implements the Runnable interface and overrides
         * the run() method to perform the matrix multiplication on its assigned chunk of the matrices.
         **/
        private class WorkerThread implements Runnable {
            private final int startRow;
            private final int endRow;

            public WorkerThread(int startRow, int endRow) {
                this.startRow = startRow;
                this.endRow = endRow;
            }

            @Override
            public void run() {
                for (int i = startRow; i < endRow; i++) {
                    for (int j = 0; j < n; j++) {
                        int sum = 0;
                        for (int k = 0; k < n; k++) {
                            sum += a[i][k] * b[k][j];
                        }
                        result[i][j] = sum;
                    }
                }
            }
        }

        public static void main(String[] args) throws InterruptedException {
            int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
            int[][] b = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
            MatrixMultiplication multiplication = new MatrixMultiplication(a, b, 4);
            multiplication.multiply();
            int[][] result = multiplication.getResult();
            for (int[] row : result) {
                System.out.println(Arrays.toString(row));
            }
        }
    }
}
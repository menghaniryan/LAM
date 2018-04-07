
public class Matrix{

    private float[][] mat; //the matrix
    private int rows, cols;
    private boolean showSteps; //show the steps in row reduction, MIGHT BE ABLE TO DELETE
    private int lengthOfLongestEntry; //used to create toString


    //Some thoughts I am having
    /*
        - What functions can I actually implement?
        - How will I show my steps in the row reduction algorithm?
        - How fast will this be, and is using floating point the best way to go about doing this?
        - Will I be able to do the thing where I don't have to turn the numbers into decimals in order to do row reduction?
        - Output format for floats, since they don't round evenly, so they will have multiple decimal point
        - USE GENERICS!!!!!!!!!!!!!!! <<<<<<<<<<<
    */

    /**
     * Creates a new matrix with a specified number of rows and specified number of columns
     * @param numRows the number of rows
     * @param numCols the number of columns
     */
    public Matrix(int numRows, int numCols){
        rows = numRows;
        cols = numCols;
        mat = new float[rows][cols];
        lengthOfLongestEntry = 1;
    }

    /**
     * Creates a new square matrix with the specified number.
     * @param num number of rows and columns of a new matrix
     */
    public Matrix(int num){
        rows = num;
        cols = num;
        mat = new float[rows][cols];
        lengthOfLongestEntry = 1;
    }

    /**
     * Creates a new Matrix given an array of integer values
     * @param arr the array of integer values
     */
    public Matrix(int[][] arr){
        rows = arr.length;
        cols = arr[0].length;
        mat = new float[rows][cols];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                mat[i][j] = arr[i][j];
                //this computes the longest entry of the input matrix
                lengthOfLongestEntry = ((""+arr[i][j]).length() > lengthOfLongestEntry) ?  (""+arr[i][j]).length() : lengthOfLongestEntry;
            }
        }
    }

    /**
     * Creates a new Matrix given an array of floating point values
     * @param arr the array of floating point values
     */
    public Matrix(float[][] arr){
        rows = arr.length;
        cols = arr[0].length;
        mat = new float[rows][cols];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                mat[i][j] = arr[i][j];
                lengthOfLongestEntry = ((""+arr[i][j]).length() > lengthOfLongestEntry) ?  (""+arr[i][j]).length() : lengthOfLongestEntry;
            }
        }
    }


    /**
     * Multiply the entire matrix by a scalar constant
     * @param constant the scalar constant to multiply the matrix by
     */
    public void multiplyByConstant(float constant){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                mat[i][j]*=constant;
            }
        }
    }

    /**
     * Add the matrix to another matrix
     * @param other the other matrix that is being added to this matrix
     */
    public void add(Matrix other){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                this.mat[i][j] += other.mat[i][j];
            }
        }
    }

    /**
     * Puts the new value at the coordinates specified.
     * @param row the row
     * @param col the column
     * @param newVal the value you are putting
     */
    public void editEntry(int row, int col, float newVal){
        mat[row][col] = newVal;
    }

    /**
     * Multiplying two matrices, returning the output in a new matrix. This multiplication puts this matrix on the lhs, the other matrix on the rhs.
     * Note - this algo uses the generic algorithm for matrix multiplication. A faster way to implement would be to use the strassen algorithm. Finally,
     * we could transpose this matrix, then do the multiplcation, and then transpose back because accessing in col-row order is slower
     * @param other the matrix to mutiply.
     * @return the result of the multiplication of the two matrices
     */
    public Matrix multiply(Matrix other){
        if(this.cols != other.rows)
            throw new IllegalArgumentException("Error, mismatching dimensions");
        Matrix newmat = new Matrix(rows, other.cols);
        for(int lhsrows = 0; lhsrows < rows; lhsrows++){
            for(int rhscols = 0; rhscols < other.cols; rhscols++){
                float sumEntries = 0;
                for(int sharedDim = 0; sharedDim < cols; sharedDim++)
                    sumEntries += (mat[lhsrows][sharedDim] * other.mat[sharedDim][rhscols]);
                newmat.mat[lhsrows][rhscols] = sumEntries;
            }
        }
        return newmat;
    }

    /**
     * Method that swaps two rows. Uses zero-based indexing for the rows.
     * @param row1 the number of one of the rows to be swapped
     * @param row2 the number of the other row that is being swapped
     */
    public void rowSwap(int row1, int row2){
        float[] temp = mat[row1];
        mat[row1] = mat[row2];
        mat[row2] = temp;
    }

    /**
     * Multiplies a row by a given multiplier
     * @param row the index of the row that is being multiplied
     * @param multiplier the multiplier
     */
    public void rowMultiply(int row, double multiplier){
        for(int i = 0; i < cols; i++)
            mat[row][i] *= multiplier;
    }

    /**
     * Performs row addition and mulitplication on the specified rows.
     * The order for the parameters goes as so:
     * (The row that you are multiplying and adding to another row, the multiplier, the row that is being added to)
     * @param addingRow the index of the row that you are multiplying and adding to another row
     * @param multiplier the multiplier
     * @param addedToRow the row that is being added to
     */
    public void rowAddition(int addingRow, float multiplier, int addedToRow){
        for(int i = 0; i < cols; i++)
            mat[addedToRow][i] += (mat[addingRow][i]*multiplier);
    }

    /**
     * A method that reduces the current matrix into echelon form.
     * @param showSteps whether the computer should show it's steps or not.
     */
    public void echelonForm(boolean showSteps){
        boolean foundCol = false;
        for(int currPivCol = 0; currPivCol < mat.length; currPivCol++) {
            if (!isColZeroes(currPivCol))
                foundCol = true;
            else
                continue;

            //if no pivot column found, there is nothing we can do!
            if (!foundCol)
                throw new IllegalArgumentException("No pivot columns ya dingus! CHANGE THIS TO THE CORRECT EXCEPTION.");

            int pivotRow = getPivotRow(currPivCol);

            //move the row into the pivot position
            if (pivotRow != currPivCol)
                rowSwap(pivotRow, currPivCol);
            pivotRow = currPivCol;

            //multiply all the values in the pivot row by the reciprocal of the value at the pivot position
            applyPivotRowOperations(pivotRow, currPivCol);

            //Zero out all the rows below it
            zeroOutRowsBelow(pivotRow, currPivCol);
        }

    }

    /**
     * Zeroes out all the entries below a specified pivot position.
     * @param pivRow the pivot row
     * @param pivCol the pivot col
     */
    public void zeroOutRowsBelow(int pivRow, int pivCol){
        for(int i = pivRow+1; i < mat.length; i++){
            //the value I need to do this subtraction by is just the value underneath the pivot rows value
            rowAddition(pivRow, mat[i][pivCol] * -1, i);
        }
    }

    /**
     * Helper method that basically multiplies all the numbers in a row by the pivot positions reciprocal, which sets up
     * the rest of the rref alogrithm.
     * @param pivRow the pivot row
     * @param pivCol the pivot column
     */
    public void applyPivotRowOperations(int pivRow, int pivCol){
        double reciprocal = (1.0)/(mat[pivRow][pivCol]);
        rowMultiply(pivRow, reciprocal);
    }


    /**
     * Given a pivot column, find the row that contains the pivot value. This is done by selecting the row with the
     * greatest absolute value.
     * @param pivotColumn the current column you are working with
     * @return the row that should be used as the pivot row
     */
    public int getPivotRow(int pivotColumn){
        int pivRow = pivotColumn;
        for(int i = pivotColumn+1; i < mat.length; i++){
            if(Math.abs(mat[i][pivotColumn]) > Math.abs(mat[pivRow][pivotColumn]))
                pivRow = i;
        }
        return pivRow;
    }

    /**
     * Helper method for RREF to determine if a column is full of only zeroes.
     * @param col the column to determine is only zeroes
     * @return true if the column is only zeroes
     */
    public boolean isColZeroes(int col){
        for(int i = 0; i < mat.length; i++){
            if(mat[i][col] != 0)
                return false;
        }
        return true;
    }


    /**
     * Helper method to generate the spacing for the generic toString
     * @return
     */
    private String generateTabs(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < lengthOfLongestEntry; i++)
            stringBuilder.append("\t");
        return stringBuilder.toString();
    }


    /**
     * Generic toString. Will edit later.
     * @return A string representation of the matrix.
     */
    public String toString(){
        StringBuilder builder = new StringBuilder();
        String tab = generateTabs();
        for(int i = 0; i < rows; i++){
            builder.append("|");
            for(int j = 0; j < cols; j++){
                builder.append(mat[i][j]);
                if(j != cols-1) {
                    builder.append(",");
                    builder.append(tab);
                }
            }
            builder.append("|\n");
        }
        return builder.toString();
    }
}

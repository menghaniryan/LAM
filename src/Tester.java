public class Tester {

    public static void main(String[] args){
        Matrix A = new Matrix(4, 2);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 2; j++){
                int val = (j == 0) ? 1 : 2;
                A.editEntry(i, j, val);
            }
        }

        Matrix B = new Matrix(2, 3);
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 3; j++){
                B.editEntry(i, j, j+1);
            }
        }
        Matrix result = A.multiply(B);
        System.out.println(result);

    }
}

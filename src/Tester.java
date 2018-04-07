public class Tester {

    public static void main(String[] args){

        Matrix res = new Matrix(3);
        int count = 1;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                res.editEntry(i, j, count);
                count++;
            }
        }
        res.echelonForm(false);
        System.out.println(res);
    }
}

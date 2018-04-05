public class Tester {

    public static void main(String[] args){

        Matrix res = new Matrix(3, 4);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                res.editEntry(i, j, i+1);
            }
        }
        System.out.println(res);
        res.rowAddition(1, 2, 2);
        System.out.println(res);

    }
}

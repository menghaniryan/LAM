public class Tester {

    public static void main(String[] args) {

        float[][] temp = { {2, 12, 4}, {8, 5, 9}, {1, 2, 3} };
        Matrix res = new Matrix(3);
        int count = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res.editEntry(i, j, count);
                count++;
            }
        }
        Matrix alsotemp = new Matrix(temp);
        System.out.println(alsotemp);
//        res.echelonForm(false);
        double whatsup = alsotemp.threeByThreeDeterminant(alsotemp);
        System.out.println(whatsup);
//        System.out.println(res);

    }
}


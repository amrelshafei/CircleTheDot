package util;

public class Util {
    
    public static String matrixToString(int[][] matrix) {
        StringBuffer str = new StringBuffer();
        
        for (int i = 0; i < matrix.length; i++) {
            str.append("\n[  ");
            
            for (int j = 0; j < matrix[i].length; j++) {
                str.append(matrix[j][i]);
                str.append("  ");
            }
            str.append("]");
        }
        return str.toString();
    }
    
    public static void main(String[] args) {
        int[][] m = new int[][]{{0,1,2}, {0,1,2}, {0,1,2}};
        System.out.print(matrixToString(m));
    }
    
}
package com.company;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Choose operation (+, -, *, *num, transpose(tR), determinant(dT), reverse(rV), rank(rK)): ");
        String operation = in.nextLine();
        System.out.print("Enter size matrix A (Rows x Columns): ");
        int hA = in.nextInt();
        int wA = in.nextInt();
        System.out.print("Enter matrix A: ");
        int[][] aMatrix = new int[hA][wA];
        for (int i = 0; i < hA; i++) {
            for (int j = 0; j < wA; j++) {
                aMatrix[i][j] = in.nextInt();
            }
        }
        int n = aMatrix.length;
        int n0 = aMatrix[0].length;

        int[][] value = switch (operation) {
            case "+", "-", "*" -> {
                System.out.print("Enter size matrix B (Rows x Columns): ");
                int hB = in.nextInt();
                int wB = in.nextInt();
                System.out.print("Enter matrix B: ");
                int[][] bMatrix = new int[hB][wB];
                for (int i = 0; i < hB; i++) {
                    for (int j = 0; j < wB; j++) {
                        bMatrix[i][j] = in.nextInt();
                    }
                }
                int[][] tempValue = new int[hA][wB];
                switch (operation) {
                    case "+" -> tempValue = matrixAdd(aMatrix, bMatrix, n);
                    case "-" -> tempValue = matrixSub(aMatrix, bMatrix, n);
                    case "*" -> tempValue = matrixMulti(aMatrix, bMatrix, n, n0);
                }
                yield tempValue;
            }
            case "*num" -> {
                System.out.print("Enter number: ");
                int x = in.nextInt();
                yield matrixMultiNumber(aMatrix, x, n, n0);
            }
            case "transpose", "tR" -> matrixTranspose(aMatrix, n, n0);
            case "determinant", "dT" -> matrixDeterminant(aMatrix, n);
            case "rank", "rK" -> matrixRank(aMatrix, n, n0);
            default -> aMatrix;
        };

        boolean flag;
        flag = false;
        float[][] err = new float[1][1];
        float[][] floatValue = switch (operation) {
            case "rank", "rV" -> {
                flag = true;
                yield matrixReverse(aMatrix, n, n0);
            }
            default -> err;
        };

        System.out.println("Result: ");
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[0].length; j++) {
                if (flag) {
                    System.out.printf("%.2f ", floatValue[i][j]);

                } else {
                    System.out.print(value[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

        public static int[][] matrixAdd (int[][] aMatrix, int[][] bMatrix, int n) {

            int[][] Result = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    Result[i][j] = aMatrix[i][j] + bMatrix[i][j];
                }
            }
            return Result;
        }

        public static int[][] matrixSub (int[][] aMatrix, int[][] bMatrix, int n) {

            int[][] Result = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    Result[i][j] = aMatrix[i][j] - bMatrix[i][j];
                }
            }
            return Result;
        }

        public static int[][] matrixMulti (int[][] aMatrix, int[][] bMatrix, int n, int n0) {
            int[][] Result = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n0; k++) {
                        Result[i][j] += aMatrix[i][k] * bMatrix[k][j];
                    }
                }
            }
            return Result;
        }

        public static int[][] matrixMultiNumber (int[][] aMatrix, int x, int n, int n0) {

            int[][] Result = new int[n][n0];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n0; j++) {
                    Result[i][j] = x * aMatrix[i][j];
                }
            }
            return Result;
        }

        public static int[][] matrixTranspose (int[][] aMatrix, int n, int n0) {
            int[][] Result = new int[n0][n];

            System.out.println(aMatrix.length + " " + aMatrix[0].length);
            System.out.println(Result.length + " " + Result[0].length);

            for (int j = 0; j < n; j++) {
                for (int i = 0; i < n0; i++) {
                    Result[i][j] = aMatrix[j][i];
                }
            }
            return Result;
        }

        public static int[][] matrixDeterminant (int[][] aMatrix, int n) {
            int[][] Result = new int[1][1];
            for (int i = 0; i < n; i++) {
                int c = 1;
                for (int j = 0; j < n; j++) {
                    c *= aMatrix[j % n][(i + j) % n];
                }
                Result[0][0] += c;
            }
            for (int i = 0; i < n; i++) {
                int c = 1;
                for (int j = 0; j < n; j++) {
                    c *= aMatrix[(n - j - 1) % n][(i + j) % n];
                }
                Result[0][0] -= c;
            }
            return Result;
        }

        public static float[][] matrixReverse (int[][] aMatrix, int n, int n0) {

            float[][] Result = new float[n][n0];
            float t;
            float[][] eMatrix = new float[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n0; j++) {
                    Result[i][j] = aMatrix[i][j];
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    eMatrix[i][j] = 0f;
                    if (i == j) {
                        eMatrix[i][j] = 1f;
                    }
                }
            }
            for (int k = 0; k < n; k++) {
                t = Result[k][k];
                for (int j = 0; j < n; j++) {
                    Result[k][j] /= t;
                    eMatrix[k][j] /= t;
                }
                for (int i = k + 1; i < n; i++) {
                    t = Result[i][k];
                    for (int j = 0; j < n; j++) {
                        Result[i][j] -= Result[k][j] * t;
                        eMatrix[i][j] -= eMatrix[k][j] * t;
                    }
                }
            }
            for (int k = n - 1; k > 0; k--) {
                for (int i = k - 1; i >= 0; i--) {
                    t = Result[i][k];
                    for (int j = 0; j < n; j++) {
                        Result[i][j] -= Result[k][j] * t;
                        eMatrix[i][j] -= eMatrix[k][j] * t;
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    Result[i][j] = eMatrix[i][j];
                }
            }
            return Result;
        }

        static void matrixSwap (int[][] aMatrix, int row1, int row2, int col) {
            for (int i = 0; i < col; i++) {
                int temp = aMatrix[row1][i];
                aMatrix[row1][i] = aMatrix[row2][i];
                aMatrix[row2][i] = temp;
            }
        }

        public static int[][] matrixRank (int[][] aMatrix, int n, int n0) {
            int[][] rank = new int[1][1];
            rank[0][0] = n;
            for (int row = 0; row < rank[0][0]; row++) {
                if (aMatrix[row][row] != 0) {
                    for (int col = 0; col < n0; col++) {
                        if (col != row) {
                            double mult = (double) aMatrix[col][row] / aMatrix[row][row];
                            for (int i = 0; i < rank[0][0]; i++) {
                                aMatrix[col][i] -= mult * aMatrix[row][i];
                            }
                        }
                    }
                } else {
                    boolean reduce = true;
                    for (int i = row + 1; i < n0; i++) {
                        if (aMatrix[i][row] != 0) {
                            matrixSwap(aMatrix, row, i, rank[0][0]);
                            reduce = false;
                            break;
                        }
                    }
                    if (reduce) {
                        rank[0][0]--;
                        for (int i = 0; i < n0; i++) {
                            aMatrix[i][row] = aMatrix[i][rank[0][0]];
                        }
                    }
                    row--;
                }
            }
            return rank;
        }
    }


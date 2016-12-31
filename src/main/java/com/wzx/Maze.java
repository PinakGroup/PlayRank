package com.wzx;

import java.util.*;

/**
 * Created by arthurwang on 16/12/28.
 */
public class Maze {
    /**
     * set the default value
     */
    private int height = 15;
    private int width = 15;
    private final int N = 1;
    private final int S = 2;
    private final int E = 4;
    private final int W = 8;

    private List<Integer> directions;
    private List<List<Integer>> grid;
    private HashMap DX;
    private HashMap DY;
    private HashMap OPPOSITE;

    private String maze = "";

    Maze() {
        directions = Arrays.asList(N, S, E, W);
        grid = new ArrayList<>();
        DX = new HashMap();
        DY = new HashMap();
        OPPOSITE = new HashMap();
        mapping(DX, DY, OPPOSITE);
    }

    private void mapping(HashMap DX, HashMap DY, HashMap OPPOSITE) {
        DX.put(E, 1);
        DX.put(W, -1);
        DX.put(N, 0);
        DX.put(S, 0);

        DY.put(E, 0);
        DY.put(W, 0);
        DY.put(N, -1);
        DY.put(S, 1);

        OPPOSITE.put(E, W);
        OPPOSITE.put(W, E);
        OPPOSITE.put(N, S);
        OPPOSITE.put(S, N);
    }

    /**
     * give that if a number is between (min, max) range
     *
     * @param i
     * @param minValueInclusive
     * @param maxValueInclusive
     * @return
     */
    private boolean between(int i, int minValueInclusive, int maxValueInclusive) {
        return (i >= minValueInclusive && i <= maxValueInclusive);
    }

    /**
     * initialize the number grid with 0
     *
     * @param width
     * @param height
     */
    private void initGrid(int width, int height) {
        this.width = width;
        this.height = height;
        for (int i = 0; i < height; i++) {
            List<Integer> list = new ArrayList<>(Collections.nCopies(width, 0));
            grid.add(list);
        }
    }

    /**
     * generate the path
     *
     * @param cx
     * @param cy
     * @param grid
     */
    private void carve_passage_from(int cx, int cy, List<List<Integer>> grid) {
        Collections.shuffle(directions, new Random(System.nanoTime()));

        for (Integer direction : directions) {
            int nx = cx + (int) DX.get(direction);
            int ny = cy + (int) DY.get(direction);

            /*
            System.out.println("dir= " + direction +
            "("+Integer.toBinaryString(direction)+")"+
            ", cx= "+cx+", cy= "+cy+", nx= " + nx + ", ny= "+ ny);
            */

            if (between(ny, 0, grid.size() - 1) && between(nx, 0, grid.get(ny).size() - 1) && (grid.get(ny).get(nx) == 0)) {
                grid.get(cy).set(cx, (grid.get(cy).get(cx) | direction));
                grid.get(ny).set(nx, (grid.get(ny).get(nx) | (int) OPPOSITE.get(direction)));
                // printArray(grid);
                // System.out.println("______________");
                carve_passage_from(nx, ny, grid);
            }
        }
    }

    /**
     * print the number grid
     *
     * @param grid
     */
    private void printArray(List<List<Integer>> grid) {
        for (List<Integer> ele : grid) {
            for (Integer i : ele) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    /**
     * print the maze
     *
     * @param grid
     */
    private void printMaze(List<List<Integer>> grid) {
        maze += (" " + new String(new char[width * 2 - 1]).replace("\0", "_") + "\n");
        for (int y = 0; y < height; y++) {
            maze += ("|");
            for (int x = 0; x < width; x++) {
                maze += (((grid.get(y).get(x) & S) != 0) ? " " : "_");
                if ((grid.get(y).get(x) & E) != 0) {
                    maze += ((((grid.get(y).get(x) | grid.get(y).get(x + 1)) & S) != 0) ? " " : "_");
                } else {
                    maze += ("|");
                }
            }
            maze += ("\n");
        }
    }

    private void generateMaze(int rows, int cols) {
        initGrid(rows, cols);
        carve_passage_from(0, 0, grid);
//        printArray(grid);
//        printMaze(grid);
    }

    List<List<Integer>> getMaze(int rows, int cols) {
        generateMaze(rows, cols);
        return grid;
    }

    public static void main(String[] args) throws java.lang.Exception {

    }
}

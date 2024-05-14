package org.example;

import org.example.pathfinding.NodeGrid;
import org.example.pathfinding.PNode;

import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    static String helpStr = "'ajuda' - lista de comandos\n'sair' - sair do programa\n'grafico' - ver o mapa limpo\n'simular <começoX> <começoY> <fimX> <fimY>' - simular algoritmo a* entre as posições";

    public static void main(String[] args)
    {
        int[][] map =
        {
            {0,0,0,0,0,0,0,0,1,0},
            {0,1,1,0,1,1,1,1,1,0},
            {0,1,1,0,0,0,0,0,0,0},
            {0,1,1,0,1,1,1,1,1,1},
            {0,0,1,0,1,0,0,0,0,0},
            {0,0,1,0,1,0,0,0,0,0},
            {0,1,1,0,0,0,0,0,0,0},
            {1,0,0,0,1,0,0,0,0,0},
            {0,0,1,0,1,0,0,0,0,0},
            {0,1,0,0,1,0,0,0,0,0}
        };

        NodeGrid grid = new NodeGrid(map, map.length, map[0].length);

        Scanner scanner = new Scanner(System.in);

        String death = "sair";

        String answer = "";

        System.out.println("Bem vindo, digite 'ajuda' para ver os comandos.");
        while(!answer.equals(death))
        {
            answer = scanner.nextLine();

            if(answer.equals(death)) continue;
            String[] carg = answer.split(" ");

            int xfi;
            try
            {
                switch (carg[0])
                {
                    case "ajuda":
                        System.out.println(helpStr);
                        break;
                    case "help":
                        System.out.println(helpStr);
                        break;
                    case "grafico":
                        System.out.println("O - chão, X - paredes");
                        System.out.print('0');
                        for(int x1 = 1; x1 < grid.Grid.length - 1; ++x1)
                        {
                            System.out.print('-');
                        }
                        System.out.print('9');
                        System.out.println();

                        xfi = grid.Grid.length - 1;
                        for(int x1 = 0; x1 < grid.Grid.length; ++x1)
                        {
                            for(int y1 = 0; y1 < grid.Grid[x1].length; ++y1)
                            {
                                System.out.print(grid.Grid[x1][y1].Neighbors.size() == 0 ? 'X' : 'O');

                                if(y1 == grid.Grid[x1].length - 1)
                                {
                                    if(x1 != xfi & x1 > 0) System.out.print(" |");
                                    else if(x1 == 0) System.out.print(" 0");
                                    else System.out.print(" 9");
                                }
                            }
                            System.out.println();
                        }
                        break;
                    case "simular":
                        char[][] graph = new char[grid.Grid.length][grid.Grid[0].length];

                        for(int x1 = 0; x1 < grid.Grid.length; ++x1)
                        {
                            for(int y1 = 0; y1 < grid.Grid[x1].length; ++y1)
                            {
                                if(grid.Grid[x1][y1].Neighbors.size() == 0)
                                    graph[x1][y1] = 'X';
                                else
                                    graph[x1][y1] = 'O';
                            }
                        }
                        int xstart = 9;
                        int ystart = 0;

                        int xend = 0;
                        int yend = 9;

                        if(carg.length > 1)
                        {
                            xstart = Integer.parseInt(carg[1]);
                            ystart = Integer.parseInt(carg[2]);
                            xend = Integer.parseInt(carg[3]);
                            yend = Integer.parseInt(carg[4]);
                        }

                        ArrayList<PNode> path = grid.genPath(grid.Grid[ystart][xstart], grid.Grid[yend][xend]);

                        for(int i = 0; i < path.size(); ++i)
                        {
                            PNode curr = path.get(i);

                            graph[curr.x][curr.y] = 'P';
                        }

                        PNode start = path.get(0);
                        PNode end = path.get(path.size()-1);
                        graph[start.x][start.y] = 'S';
                        graph[end.x][end.y] = 'E';

                        System.out.println("O - chão, X - paredes, P - caminho\nS - onde o personagem está, E - onde o personagem deve chegar");
                        System.out.print('0');
                        for(int x1 = 1; x1 < grid.Grid.length - 1; ++x1)
                        {
                            System.out.print('-');
                        }
                        System.out.print('9');
                        System.out.println();
                        xfi = grid.Grid.length - 1;
                        for(int x1 = 0; x1 < grid.Grid.length; ++x1)
                        {
                            //y final index
                            for(int y1 = 0; y1 < grid.Grid[x1].length; ++y1)
                            {
                                System.out.print(graph[x1][y1]);

                                if(y1 == grid.Grid[x1].length - 1)
                                {
                                    if(x1 != xfi & x1 > 0) System.out.print(" |");
                                    else if(x1 == 0) System.out.print(" 0");
                                    else System.out.print(" 9");
                                }
                            }
                            System.out.println();
                        }

                        break;
                }
            }
            catch (Exception e)
            {
                System.out.println("error");
            }
        }
    }
}
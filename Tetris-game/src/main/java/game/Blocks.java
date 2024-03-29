package main.java.game;

import java.util.Random;

public class Blocks {

    protected enum Tetrominoe { NoBlock, ZBlock, SBlock, IBlock, 
               TBlock, OBlock, LBlock, JBlock };	//블록의 종류

    private Tetrominoe pieceBlock;
    private int coords[][];
    private int[][][] coordsTable;


    public Blocks() {
        
        initBlock();
    }
    
    private void initBlock() {		//블록 초기화

        coords = new int[4][2];
        setBlock(Tetrominoe.NoBlock);
    }

    protected void setBlock(Tetrominoe block) {		//블록 설정 

         coordsTable = new int[][][] {
            { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
            { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
            { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
            { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
            { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
            { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
            { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
            { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
        };

        for (int i = 0; i < 4 ; i++) {
            
            for (int j = 0; j < 2; ++j) {
                
                coords[i][j] = coordsTable[block.ordinal()][i][j];
            }
        }
        
        pieceBlock = block;
    }

    private void setX(int index, int x) { coords[index][0] = x; }
    private void setY(int index, int y) { coords[index][1] = y; }
    public int x(int index) { return coords[index][0]; }
    public int y(int index) { return coords[index][1]; }
    public Tetrominoe getBlock()  { return pieceBlock; }

    public void setRandomBlock() {		//랜덤한 블록으로 설정
        
        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;
        Tetrominoe[] values = Tetrominoe.values(); 
        setBlock(values[x]);
    }

    public int minX() {		//현재 블록의 가장 왼쪽 x좌표 반환
        
      int m = coords[0][0];
      
      for (int i=0; i < 4; i++) {
          
          m = Math.min(m, coords[i][0]);
      }
      
      return m;
    }


    public int minY() {		//현재 블록의 가장 아래 y좌표 반환
        
      int m = coords[0][1];
      
      for (int i=0; i < 4; i++) {
          
          m = Math.min(m, coords[i][1]);
      }
      
      return m;
    }

    public Blocks rotateLeft() {
        
        if (pieceBlock == Tetrominoe.OBlock)
            return this;

        Blocks result = new Blocks();
        result.pieceBlock = pieceBlock;

        for (int i = 0; i < 4; ++i) {
            
            result.setX(i, y(i));
            result.setY(i, -x(i));
        }
        
        return result;
    }

    public Blocks rotateRight() {
        
        if (pieceBlock == Tetrominoe.OBlock)
            return this;

        Blocks result = new Blocks();
        result.pieceBlock = pieceBlock;

        for (int i = 0; i < 4; ++i) {

            result.setX(i, -y(i));
            result.setY(i, x(i));
        }
        
        return result;
    }
}
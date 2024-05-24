package main.java.game;

import java.util.Random;

public class Blocks {

    protected enum Tetrominoe { NoBlock, ZBlock, SBlock, IBlock, 
               TBlock, OBlock, LBlock, JBlock, BorderBlock,
               WeightItem, LineDelItem, ThreeItem, FiveItem, SevenItem, PlusItem, AllDelItem, HighlightBlock, GrayBlock};	//블록의 종류, BorderBlock은 테두리를 나타냄

    private Tetrominoe pieceBlock;
    private int coords[][];
    private int[][][] coordsTable;
    private String difficulty;
    public Blocks(String difficulty) {
    	this.difficulty = difficulty;
        initBlock();
    }
    private void initBlock() {		//블록 초기화
        coords = new int[4][2];
        setBlock(Tetrominoe.NoBlock);
    }
    protected void setBlock(Tetrominoe block) {		//블록 설정 
         coordsTable = new int[][][] {
             { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
             { { -1, 0 },  { 0, 0 },   { 0, 1 },  { 1, 1 } },
             { { -1, 1 },  { 0, 1 },   { 0, 0 },   { 1, 0 } },
             { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 2, 0 } },
             { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
             { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
             { { -1, 1 }, { 0, 1 },  { 1, 1 },   { 1, 0 } },
             { { -1, 0 },  { -1, 1 },  { 0, 1 },   { 1, 1 } },
             { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
             
             { { -1, 0 },   { 0, 0 },   { 1, 0 },   { 2, 0 } },
             { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
             { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
             { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
             { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
             { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
             { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } }
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

    
    public void setRandomBlock(String difficulty, boolean isItem) {
    	if(isItem == false)
    	{
    	int mode = 0;
    	if (difficulty.equals("Easy")) {
            mode = 1; // Easy 모드
        } else if (difficulty.equals("Hard")) {
            mode = 2; // Hard 모드
        }
        
        Random r = new Random();
        double random = r.nextDouble(); // 0 이상 1 미만의 난수
        double sum = 0.0;
        int blockIndex = 0; // 선택된 블록의 인덱스

        // 각 모드에 따른 블록 생성 확률
        double[] easyProbabilities = {0, 0.1389, 0.1389, 0.1667, 0.1389, 0.1389, 0.1389, 0.1389, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] hardProbabilities = {0, 0.1471, 0.1471, 0.1176, 0.1471, 0.1471, 0.1471, 0.1471, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] probabilities;

        if (mode == 1) { // Easy 모드
            probabilities = easyProbabilities;
        } else if (mode == 2) { // Hard 모드
            probabilities = hardProbabilities;
        } else { // Normal 모드
            probabilities = new double[]{0, 1.0 / 7, 1.0 / 7, 1.0 / 7, 1.0 / 7, 1.0 / 7, 1.0 / 7, 1.0 / 7, 0, 0, 0, 0, 0, 0, 0, 0};
        }

        // 확률에 따라 블록 선택
        for (int i = 0; i < probabilities.length; i++) {
        	if (probabilities[i] == 0) continue;
            sum += probabilities[i];
            if (random <= sum) {
                blockIndex = i;
                break;
            }
            
        }
    	
        Tetrominoe[] values = Tetrominoe.values(); 
        setBlock(values[blockIndex]);
    	}
    	else
    	{
    		Random r = new Random();
            double random = r.nextDouble(); // 0 이상 1 미만의 난수
            double sum = 0.0;
            int blockIndex = 0; // 선택된 블록의 인덱스

            // 각 모드에 따른 블록 생성 확률
            double[] probabilities = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1.0/7 , 1.0/7, 1.0/7, 1.0/7, 1.0/7, 1.0/7, 1.0/7 };
            for (int i = 0; i < probabilities.length; i++) {
            	if (probabilities[i] == 0) continue;
                sum += probabilities[i];
                if (random <= sum) {
                    blockIndex = i;
                    break;
                } 
            }
            Tetrominoe[] values = Tetrominoe.values(); 
            setBlock(values[blockIndex]);
    	}
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

        Blocks result = new Blocks(difficulty);
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

        Blocks result = new Blocks(difficulty);
        result.pieceBlock = pieceBlock;

        for (int i = 0; i < 4; ++i) {

            result.setX(i, -y(i));
            result.setY(i, x(i));
        }
        
        return result;
    }

    public int[][] getCoords() {
        return coords;
    }
}
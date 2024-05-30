package main.java.game;

import main.java.game.Blocks.Tetrominoe;

public class ItemEffectHandler {		// 아이템 효과를 나타내는 클래스
	static int BOARD_WIDTH = Board.BOARD_WIDTH;
	static int BOARD_HEIGHT = Board.BOARD_HEIGHT;
	
	public static void deleteWeightItem(Tetrominoe[] board, Blocks curPiece, int curX, int curY) {
	   	 for (int i = 0; i < 4; i++) {
	   	        int x = curX + curPiece.x(i);
	   	        int y = curY - curPiece.y(i);
	   	        if (x >= 0 && x < BOARD_WIDTH && y >= 0 && y < BOARD_HEIGHT && board[(y * BOARD_WIDTH) + x] != Tetrominoe.BorderBlock) {
	   	            board[(y * BOARD_WIDTH) + x] = Tetrominoe.NoBlock;
	   	        }
	   	    }
	   }
	   
	   public static void applyLineDelItem(Tetrominoe[] board, int curY, int LBlockY) {
	       int centerY = curY - LBlockY;
	       
	       // 가로 방향으로 한 줄 제거
	       for (int j = 1; j < BOARD_WIDTH - 1; j++) {
	           if (board[(centerY * BOARD_WIDTH) + j] != Tetrominoe.BorderBlock) {
	               board[(centerY * BOARD_WIDTH) + j] = Tetrominoe.NoBlock;
	           }
	       }
	       
	    // 해당 줄 위의 모든 블록들을 한 칸씩 아래로 이동시키기
	       for (int i = centerY; i < BOARD_HEIGHT - 2; ++i) {
	           for (int j = 0; j < BOARD_WIDTH; ++j) {
	               board[(i* BOARD_WIDTH) + j] = board[((i+1)*BOARD_WIDTH) + j];
	           }
	       }
	   }

	   public static void applyThreeItem(Tetrominoe[] board, int curX, int curY) {
	   	int centerX = curX ; // 현재 블록의 중심 X 좌표
	       int centerY = curY ; // 현재 블록의 중심 Y 좌표
	       
	       // 블록이 3x3 범위 내에 있는지 확인하고 중심을 기준으로 주변 블록을 삭제
	       for (int i = centerY - 1; i <= centerY + 1; i++) {
	           for (int j = centerX - 1; j <= centerX + 1; j++) {
	               if (i >= 0 && i < BOARD_HEIGHT && j >= 0 && j < BOARD_WIDTH && board[(i * BOARD_WIDTH) + j] != Tetrominoe.BorderBlock) {
	                   board[(i * BOARD_WIDTH) + j] = Tetrominoe.NoBlock;
	               }
	           }
	       }
	   }

	   public static void applyFiveItem(Tetrominoe[] board, int curX, int curY) {
	   	int centerX = curX ; 
	       int centerY = curY ; 
	       
	       for (int i = centerY - 2; i <= centerY + 2; i++) {
	           for (int j = centerX - 2; j <= centerX + 2; j++) {
	               if (i >= 0 && i < BOARD_HEIGHT && j >= 0 && j < BOARD_WIDTH && board[(i * BOARD_WIDTH) + j] != Tetrominoe.BorderBlock) {
	                   board[(i * BOARD_WIDTH) + j] = Tetrominoe.NoBlock;
	               }
	           }
	       }
	   }
	   
	   public static void applySevenItem(Tetrominoe[] board, int curX, int curY) {
	   	int centerX = curX ; 
	       int centerY = curY ; 
	       
	       for (int i = centerY - 3; i <= centerY + 3; i++) {
	           for (int j = centerX - 3; j <= centerX + 3; j++) {
	               if (i >= 0 && i < BOARD_HEIGHT && j >= 0 && j < BOARD_WIDTH && board[(i * BOARD_WIDTH) + j] != Tetrominoe.BorderBlock) {
	                   board[(i * BOARD_WIDTH) + j] = Tetrominoe.NoBlock;
	               }
	           }
	       }
	   }
	   
	   public static void applyPlusItem(Tetrominoe[] board, int curX, int curY) {
	   	int centerX = curX; 
	       int centerY = curY; 	
	       
	       // 가로 방향으로 한 줄 제거
	       for (int j = 1; j < BOARD_WIDTH - 1; j++) {
	           if (board[(centerY * BOARD_WIDTH) + j] != Tetrominoe.BorderBlock) {
	               board[(centerY * BOARD_WIDTH) + j] = Tetrominoe.NoBlock;
	           }
	       }
	       
	       // 세로 방향으로 한 줄 제거
	       for (int i = 1; i < BOARD_HEIGHT - 1; i++) {
	           if (board[(i * BOARD_WIDTH) + centerX] != Tetrominoe.BorderBlock) {
	               board[(i * BOARD_WIDTH) + centerX] = Tetrominoe.NoBlock;
	           }
	       }
	       
	    // 해당 줄 위의 모든 블록들을 한 칸씩 아래로 이동시키기
	       for (int i = centerY; i < BOARD_HEIGHT - 2; ++i) {
	           for (int j = 0; j < BOARD_WIDTH; ++j) {
	              board[(i* BOARD_WIDTH) + j] = board[((i + 1)* BOARD_WIDTH ) + j];
	           }
	       }
	   }

	   public static void applyAllDelItem(Tetrominoe[] board) {
	   	for (int i = 1; i < BOARD_HEIGHT - 1; i++) {
	           for (int j = 1; j < BOARD_WIDTH - 1; j++) {
	               board[(i * BOARD_WIDTH) + j] = Tetrominoe.NoBlock;
	           }
	       }
	   }
}

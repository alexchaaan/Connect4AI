public class minimax_Malcolm2 extends AIModule {
	private int[][][] winConditionList = {
		{{0,0},{1,0},{2,0},{3,0}},
		{{1,0},{2,0},{3,0},{4,0}},
		{{2,0},{3,0},{4,0},{5,0}},
		{{3,0},{4,0},{5,0},{6,0}},
		{{0,1},{1,1},{2,1},{3,1}},
		{{1,1},{2,1},{3,1},{4,1}},
		{{2,1},{3,1},{4,1},{5,1}},
		{{3,1},{4,1},{5,1},{6,1}},	
		{{0,2},{1,2},{2,2},{3,2}},
		{{1,2},{2,2},{3,2},{4,2}},
		{{2,2},{3,2},{4,2},{5,2}},
		{{3,2},{4,2},{5,2},{6,2}},
		{{0,3},{1,3},{2,3},{3,3}},
		{{1,3},{2,3},{3,3},{4,3}},
		{{2,3},{3,3},{4,3},{5,3}},
		{{3,3},{4,3},{5,3},{6,3}},
		{{0,4},{1,4},{2,4},{3,4}},
		{{1,4},{2,4},{3,4},{4,4}},
		{{2,4},{3,4},{4,4},{5,4}},
		{{3,4},{4,4},{5,4},{6,4}},
		{{0,5},{1,5},{2,5},{3,5}},
		{{1,5},{2,5},{3,5},{4,5}},
		{{2,5},{3,5},{4,5},{5,5}},
		{{3,5},{4,5},{5,5},{6,5}}, //vertical		
		{{0,0},{0,1},{0,2},{0,3}},
		{{0,1},{0,2},{0,3},{0,4}},
		{{0,2},{0,3},{0,4},{0,5}},
		{{1,0},{1,1},{1,2},{1,3}},
		{{1,1},{1,2},{1,3},{1,4}},
		{{1,2},{1,3},{1,4},{1,5}},
		{{2,0},{2,1},{2,2},{2,3}},
		{{2,1},{2,2},{2,3},{2,4}},
		{{2,2},{2,3},{2,4},{2,5}},
		{{3,0},{3,1},{3,2},{3,3}},
		{{3,1},{3,2},{3,3},{3,4}},
		{{3,2},{3,3},{3,4},{3,5}},
		{{4,0},{4,1},{4,2},{4,3}},
		{{4,1},{4,2},{4,3},{4,4}},
		{{4,2},{4,3},{4,4},{4,5}},
		{{5,0},{5,1},{5,2},{5,3}},
		{{5,1},{5,2},{5,3},{5,4}},
		{{5,2},{5,3},{5,4},{5,5}},
		{{6,0},{6,1},{6,2},{6,3}},
		{{6,1},{6,2},{6,3},{6,4}},
		{{6,2},{6,3},{6,4},{6,5}}, //horizontal
		{{0,2},{1,3},{2,4},{3,5}},
		{{0,1},{1,2},{2,3},{3,4}},
		{{1,2},{2,3},{3,4},{4,5}},
		{{0,0},{1,1},{2,2},{3,3}},
		{{1,1},{2,2},{3,3},{4,4}},
		{{2,2},{3,3},{4,4},{5,5}},
		{{1,0},{2,1},{3,2},{4,3}},
		{{2,1},{3,2},{4,3},{5,4}},
		{{3,2},{4,3},{5,4},{6,5}},
		{{2,0},{3,1},{4,2},{5,3}},
		{{3,1},{4,2},{5,3},{6,4}},
		{{3,0},{4,1},{5,2},{6,3}},
		{{0,3},{1,2},{2,1},{3,0}},
		{{0,4},{1,3},{2,2},{3,1}},
		{{1,3},{2,2},{3,1},{4,0}},
		{{0,5},{1,4},{2,3},{3,2}},
		{{1,4},{2,3},{3,2},{4,1}},
		{{2,3},{3,2},{4,1},{5,0}},
		{{1,5},{2,4},{3,3},{4,2}},
		{{2,4},{3,3},{4,2},{5,1}},
		{{3,3},{4,2},{5,1},{6,0}},
		{{2,5},{3,4},{4,3},{5,2}},
		{{3,4},{4,3},{5,2},{6,1}},
		{{3,5},{4,4},{5,3},{6,2}}};

	private int [][] valueTable = 
		{{3, 4, 5,  9,  5,  4, 3},	
		 {4, 6, 8,  10, 8,  6, 4},
		 {5, 8, 11, 13, 11, 8, 5},
		 {5, 8, 11, 13, 11, 8, 5},
 		 {4, 6, 8,  10, 8,  6, 4},		  				  		
		 {3, 4, 1000,  100,  1000,  4, 3}};

		private int ourAIPlayer;
		private int theirAIPlayer;
	
		@Override
		public void getNextMove(final GameStateModule game) {
			int max = Integer.MIN_VALUE;
			int gameWidth = game.getWidth();
			int i = 0;
			int depth = 7;
			this.ourAIPlayer = game.getActivePlayer();
			if(this.ourAIPlayer == 1) {
				this.theirAIPlayer = 2;
			}
			else {
				this.theirAIPlayer = 1;
			}
			
			for (int j = 0; j < game.getWidth(); j++) {
				if (game.canMakeMove(j)) {
					chosenMove = j;
					break;
				}
			}
			while(i < gameWidth) {
				if (game.canMakeMove(i)) {
					game.makeMove(i);
					int min = minValue(game, i, depth);
					game.unMakeMove();
					if (min > max) {
						chosenMove = i;
						max = min;
					}
				}
				i++;
			}
		}

		private int minValue(GameStateModule game, int x, int depth) {
			if(terminate) {
				return evalFunction(game);
			}
			boolean gameOver = game.isGameOver();

			if (gameOver|| depth <= 0) {
				return evalFunction(game);
			}

			int gameWidth = game.getWidth();
			int i = 0;
			int min = Integer.MAX_VALUE;
			while(i < gameWidth) {
				if (game.canMakeMove(i)) {
					game.makeMove(i);
					min = Math.min(min, maxValue(game, x, depth - 1));
					game.unMakeMove();
				}
				i++;
			}
			return min;
		}

		private int maxValue(GameStateModule game, int x, int depth) {
			if(terminate) {
				return evalFunction(game);
			}
			boolean gameOver = game.isGameOver();

			if (gameOver|| depth <= 0) {
				return evalFunction(game);
			}

			int gameWidth = game.getWidth();
			int i = 0;
			int max = Integer.MIN_VALUE;
			while(i < gameWidth) {
				if (game.canMakeMove(i)) {
					game.makeMove(i);
					max = Math.max(max, minValue(game, x, depth - 1));
					game.unMakeMove();
				}
				i++;
			}
			return max;
		}

		private int evalFunction(GameStateModule game) {
			boolean gameOver = game.isGameOver();
			if (gameOver) {
				if (game.getWinner() == this.ourAIPlayer) {
					return Integer.MAX_VALUE;
				}
				else if (game.getWinner() == this.theirAIPlayer) {
					return Integer.MIN_VALUE;
				}
				else {
					return 0;
				}
			}
			int current = game.getActivePlayer();
			int points = 0;
			int ourPoints;
			int theirPoints;
			int y;
			int x;

			for(int i = 0; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					if(game.getAt(i, j) == this.ourAIPlayer) {
						points += valueTable[i][j];
					}
					if(game.getAt(i,j) == this.theirAIPlayer) {
						points -= valueTable[i][j];
					}
				}
			}

			for (int i = 0; i < this.winConditionList.length; i++) {
				int[][] tempWinCond = this.winConditionList[i];
				theirPoints = 0;
				ourPoints = 0;
				y = 0;
				x = 0;

				for (int j = 0; j < tempWinCond.length; j++) {
					int[] posAr = tempWinCond[j];
					int playerAtPos = game.getAt(posAr[0], posAr[1]);
					if(playerAtPos == 0) {
						x = posAr[0];
						y = posAr[1];
					}
					else if(playerAtPos == this.ourAIPlayer) {
						ourPoints++;
					}
					else {
						theirPoints++;
					}
				}
				if ((theirPoints == 3) && (ourPoints == 0)) {
					if ((y > 0) && (game.getAt(x, y - 1) == 0)) {
						if (this.ourAIPlayer == 1) {
							if (y % 2 == 0) {
								points -= 1000;
							}
						}
						else if (y % 2 == 1) {
								points -= 100;
						}
					}
					else {
						return Integer.MIN_VALUE;
					}					
				}
				else if ((ourPoints == 3) && (theirPoints == 0)) {
					if ((y > 0) && (game.getAt(x, y - 1) == 0)) {
						if (this.ourAIPlayer == 1) {
							if (y % 2 == 1) {
								points += 1000;
							}
						}
						else if (y % 2 == 0) {
							points += 100;
						}
					}
					else {
						return Integer.MAX_VALUE;
					}
				}
				points += (ourPoints - theirPoints);
			}
			return points;
		}
}
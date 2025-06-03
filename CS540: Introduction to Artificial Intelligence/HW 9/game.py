import random
# import time
import copy

class TeekoPlayer:
    """ An object representation for an AI game player for the game Teeko.
    """
    board = [[' ' for j in range(5)] for i in range(5)]
    pieces = ['b', 'r']

    def __init__(self):
        """ Initializes a TeekoPlayer object by randomly selecting red or black as its
        piece color.
        """
        self.my_piece = random.choice(self.pieces)
        self.opp = self.pieces[0] if self.my_piece == self.pieces[1] else self.pieces[1]

    def run_challenge_test(self):
        """ Set to True if you would like to run gradescope against the challenge AI!
        Leave as False if you would like to run the gradescope tests faster for debugging.
        You can still get full credit with this set to False
        """ 
        return False
    
    def succ(self, state):
        '''
        returns a list of legal successor states (i.e., states that are one marker-adjacent to the current states)
        During the drop phase, this simply means adding a new piece of the current players type to the board; during
        continued gameplay, this means moving any one of the current players pieces to an unoccupied
        location on the board, adjacent to that piece.
        
        '''
        #if drop state 
        succ = []
        currPiece = self.my_piece
        drop_phase = True
        
        countR = 0
        countB = 0
        for row in state: 
            for col in row:
                if col == 'b':
                    countB += 1
                elif col == 'r':
                    countR += 1
        drop_phase = False if countB >= 4 or countR >= 4 else True
        if drop_phase:
            #any open square 
            for i in range(len(state)):
                for j in range(len(state[0])):
                    if state[i][j] == ' ':
                        newCopy = copy.deepcopy(state)
                        newCopy[i][j] = currPiece
                        succ.append(newCopy)
        else:
            #upper left, up, upper right, right, lower right, down, lower left, left
            directions = [(-1,1),(0,1),(1,1),(1,0),(1,-1),(0,-1),(-1,-1),(-1,0)]
            for i in range(len(state)):
                for j in range(len(state)):
                    if state[i][j] == self.my_piece:
                        for r, c in directions:
                            #current row ind and col ind after moving the tile to oprn square
                            newRow, newCol = i+r, j+c
                            #check that currRow and currCol within range of matrix
                            if (newRow >= 0 and newRow < len(state)) and (newCol >= 0 and newCol < len(state)) and state[newRow][newCol] == ' ':
                                newCopy = copy.deepcopy(state)
                                newCopy[i][j] = ' '
                                newCopy[newRow][newCol] = self.my_piece
                                succ.append(newCopy)
        return succ
        # pass


    def make_move(self, state):
        """ Selects a (row, col) space for the next move. You may assume that whenever
        this function is called, it is this player's turn to move.

        Args:
            state (list of lists): should be the current state of the game as saved in
                this TeekoPlayer object. Note that this is NOT assumed to be a copy of
                the game state and should NOT be modified within this method (use
                place_piece() instead). Any modifications (e.g. to generate successors)
                should be done on a deep copy of the state.

                In the "drop phase", the state will contain less than 8 elements which
                are not ' ' (a single space character).

        Return:
            move (list): a list of move tuples such that its format is
                    [(row, col), (source_row, source_col)]
                where the (row, col) tuple is the location to place a piece and the
                optional (source_row, source_col) tuple contains the location of the
                piece the AI plans to relocate (for moves after the drop phase). In
                the drop phase, this list should contain ONLY THE FIRST tuple.

        Note that without drop phase behavior, the AI will just keep placing new markers
            and will eventually take over the board. This is not a valid strategy and
            will earn you no points.
        """
        #count non empty squares 
        countR = 0
        countB = 0
        for row in state: 
            for col in row:
                if col == 'b':
                    countB += 1
                elif col == 'r':
                    countR += 1
        
        drop_phase = True if (countB + countR < 8) else False
            
        # select an unoccupied space randomly
        move = []
        # print(currState)
        # (row, col) = (random.randint(0,4), random.randint(0,4))
        # while not state[row][col] == ' ':
        #     (row, col) = (random.randint(0,4), random.randint(0,4))
        value, optState = self.max_value(state, 0)
        src = None
        dst = None
        for row in range(len(state)):
            for col in range(len(state[row])):
                if state[row][col] == ' ' and optState[row][col] == self.my_piece:
                    dst = (row,col)
                elif state[row][col] == self.my_piece and optState[row][col] == ' ':
                    src = (row,col)
                    
        # ensure the destination (row,col) tuple is at the beginning of the move list
        move.insert(0, dst)
        if not drop_phase:
            # TODO: choose a piece to move and remove it from the board
            # (You may move this condition anywhere, just be sure to handle it)
            #
            # Until this part is implemented and the move list is updated
            # accordingly, the AI will not follow the rules after the drop phase!
            move.insert(1, src)
        return move
    
    def max_value(self, state, depth):
        # t0 = time.time()
        nextState = state
        if self.game_value(state) != 0:
            return self.game_value(state), state
        
        if depth >= 3:
            return self.heuristic_game_value(state, self.my_piece), state
        
        else:
            a = float('-inf')
            for s in self.succ(state):
                minVal = self.min_value(s, depth+1)
                if minVal[0] > a:
                    a = minVal[0]
                    nextState = s

        # t1 = time.time()
        #print("time to evaluate", t1-t0)
        return a, nextState
    
    def min_value(self, state, depth):
        nextState = state
        if self.game_value(state) != 0:
            return self.game_value(state), state
        
        if depth >= 3:
            return self.heuristic_game_value(state, self.opp), state
        
        else:
            b = float('inf')
            for s in self.succ(state):
                maxVal = self.max_value(s, depth+1)
                if maxVal[0] < b:
                    b = maxVal[0]
                    nextState = s
        return b, nextState
    def heuristic_game_value(self, state, piece):
        '''
        Determine whether the state is a terminal state before you start evaluating it heuristically.) This function should return some 
        floating-point value between 1 and -1, closer to -1 = worse state for player and closer to 1 is better state for player
        '''
        if self.game_value(state) != 0:
            return self.game_value(state)
        
        maxM = 0
        maxO = 0
        # Check horizontal windows
        for i in range(5):
            for j in range(2):  
                window = [state[i][j+k] for k in range(4)]
                countM, countO = self.evaluate_window(window)
                maxM = max(maxM, countM)
                maxO = max(maxO, countO)

        # Check vertical windows
        for j in range(5):
            for i in range(2):
                window = [state[i+k][j] for k in range(4)]
                countM, countO = self.evaluate_window(window)
                maxM = max(maxM, countM)
                maxO = max(maxO, countO)

        # Check \ diagonals
        for i in range(2):
            for j in range(2):
                window = [state[i+k][j+k] for k in range(4)]
                countM, countO = self.evaluate_window(window)
                maxM = max(maxM, countM)
                maxO = max(maxO, countO)

        # Check / diagonals
        for i in range(len(state) - 3):
            for j in range(3, len(state)):
                window = [state[i+k][j-k] for k in range(4)]
                countM, countO = self.evaluate_window(window)
                maxM = max(maxM, countM)
                maxO = max(maxO, countO)

        # Check 2x2 boxes
        for i in range(len(state) - 1):
            for j in range(len(state)-1):
                window = [state[i][j], state[i][j+1], state[i+1][j], state[i+1][j+1]]
                countM, countO = self.evaluate_window(window)
                maxM = max(maxM, countM)
                maxO = max(maxO, countO)

        #return values for how close ai is to win
        if maxM == maxO:
            return 0
        elif maxM > maxO:
            return maxM/4  # closer to 1 if I am closer to win
        else:
            return -maxO / 4  # closer to -1 if opp is closer to win
        
    def evaluate_window(self, window):
            countM = window.count(self.my_piece)
            countO = window.count(self.opp)
            if countM > 0 and countO == 0:
                return countM, 0
            elif countO > 0 and countM == 0:
                return 0, countO
            return 0, 0
       
    def opponent_move(self, move):
        """ Validates the opponent's next move against the internal board representation.
        You don't need to touch this code.

        Args:
            move (list): a list of move tuples such that its format is
                    [(row, col), (source_row, source_col)]
                where the (row, col) tuple is the location to place a piece and the
                optional (source_row, source_col) tuple contains the location of the
                piece the AI plans to relocate (for moves after the drop phase). In
                the drop phase, this list should contain ONLY THE FIRST tuple.
        """
        # validate input
        if len(move) > 1:
            source_row = move[1][0]
            source_col = move[1][1]
            if source_row != None and self.board[source_row][source_col] != self.opp:
                self.print_board()
                print(move)
                raise Exception("You don't have a piece there!")
            if abs(source_row - move[0][0]) > 1 or abs(source_col - move[0][1]) > 1:
                self.print_board()
                print(move)
                raise Exception('Illegal move: Can only move to an adjacent space')
        if self.board[move[0][0]][move[0][1]] != ' ':
            raise Exception("Illegal move detected")
        # make move
        self.place_piece(move, self.opp)

    def place_piece(self, move, piece):
        """ Modifies the board representation using the specified move and piece

        Args:
            move (list): a list of move tuples such that its format is
                    [(row, col), (source_row, source_col)]
                where the (row, col) tuple is the location to place a piece and the
                optional (source_row, source_col) tuple contains the location of the
                piece the AI plans to relocate (for moves after the drop phase). In
                the drop phase, this list should contain ONLY THE FIRST tuple.

                This argument is assumed to have been validated before this method
                is called.
            piece (str): the piece ('b' or 'r') to place on the board
        """
        if len(move) > 1:
            self.board[move[1][0]][move[1][1]] = ' '
        self.board[move[0][0]][move[0][1]] = piece

    def print_board(self):
        """ Formatted printing for the board """
        for row in range(len(self.board)):
            line = str(row)+": "
            for cell in self.board[row]:
                line += cell + " "
            print(line)
        print("   A B C D E")

    def game_value(self, state):
        """ Checks the current board status for a win condition

        Args:
        state (list of lists): either the current state of the game as saved in
            this TeekoPlayer object, or a generated successor state.

        Returns:
            int: 1 if this TeekoPlayer wins, -1 if the opponent wins, 0 if no winner
        """
        # check horizontal wins
        for row in state:
            for i in range(2):
                if row[i] != ' ' and row[i] == row[i+1] == row[i+2] == row[i+3]:
                    return 1 if row[i]==self.my_piece else -1

        # check vertical wins
        for col in range(5):
            for i in range(2):
                if state[i][col] != ' ' and state[i][col] == state[i+1][col] == state[i+2][col] == state[i+3][col]:
                    return 1 if state[i][col]==self.my_piece else -1

        # check \ diagonal wins
        for i in range(2):
            for j in range(2):
                if state[i][j] != ' ' and state[i][j] == state[i+1][j+1] == state[i+2][j+2] == state[i+3][j+3]:
                    return 1 if state[i][j]==self.my_piece else -1
        # check / diagonal wins
        for i in range(len(state) - 3):
            for j in range(3, len(state)):
                if state[i][j] != ' ' and state[i][j] == state[i+1][j-1] == state[i+2][j-2] == state[i+3][j-3]:
                    return 1 if state[i][j]==self.my_piece else -1
        # check box wins
        for i in range(len(state) - 1):
            for j in range(len(state)-1):
                if state[i][j] != ' ' and state[i][j] == state[i][j+1] == state[i+1][j+1] == state[i+1][j]:
                    return 1 if state[i][j]==self.my_piece else -1
        
        return 0 # no winner yet
    
############################################################################
#
# THE FOLLOWING CODE IS FOR SAMPLE GAMEPLAY ONLY
#
############################################################################
def main():
    print('Hello, this is Samaritan')
    ai = TeekoPlayer()
    piece_count = 0
    turn = 0

    # drop phase
    while piece_count < 8 and ai.game_value(ai.board) == 0:

        # get the player or AI's move
        if ai.my_piece == ai.pieces[turn]:
            ai.print_board()
            move = ai.make_move(ai.board)
            ai.place_piece(move, ai.my_piece)
            print(ai.my_piece+" moved at "+chr(move[0][1]+ord("A"))+str(move[0][0]))
        else:
            move_made = False
            ai.print_board()
            print(ai.opp+"'s turn")
            while not move_made:
                player_move = input("Move (e.g. B3): ")
                while player_move[0] not in "ABCDE" or player_move[1] not in "01234":
                    player_move = input("Move (e.g. B3): ")
                try:
                    ai.opponent_move([(int(player_move[1]), ord(player_move[0])-ord("A"))])
                    move_made = True
                except Exception as e:
                    print(e)

        # update the game variables
        piece_count += 1
        turn += 1
        turn %= 2

    # move phase - can't have a winner until all 8 pieces are on the board
    while ai.game_value(ai.board) == 0:

        # get the player or AI's move
        if ai.my_piece == ai.pieces[turn]:
            ai.print_board()
            move = ai.make_move(ai.board)
            ai.place_piece(move, ai.my_piece)
            print(ai.my_piece+" moved from "+chr(move[1][1]+ord("A"))+str(move[1][0]))
            print("  to "+chr(move[0][1]+ord("A"))+str(move[0][0]))
        else:
            move_made = False
            ai.print_board()
            print(ai.opp+"'s turn")
            while not move_made:
                move_from = input("Move from (e.g. B3): ")
                while move_from[0] not in "ABCDE" or move_from[1] not in "01234":
                    move_from = input("Move from (e.g. B3): ")
                move_to = input("Move to (e.g. B3): ")
                while move_to[0] not in "ABCDE" or move_to[1] not in "01234":
                    move_to = input("Move to (e.g. B3): ")
                try:
                    ai.opponent_move([(int(move_to[1]), ord(move_to[0])-ord("A")),
                                    (int(move_from[1]), ord(move_from[0])-ord("A"))])
                    move_made = True
                except Exception as e:
                    print(e)

        # update the game variables
        turn += 1
        turn %= 2

    ai.print_board()
    if ai.game_value(ai.board) == 1:
        print("AI wins! Game over.")
    else:
        print("You win! Game over.")


if __name__ == "__main__":
    main()

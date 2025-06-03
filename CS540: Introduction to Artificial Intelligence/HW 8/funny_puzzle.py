import heapq
import numpy as np

def state_check(state):
    """check the format of state, and return corresponding goal state.
       Do NOT edit this function."""
    non_zero_numbers = [n for n in state if n != 0]
    num_tiles = len(non_zero_numbers)
    if num_tiles == 0:
        raise ValueError('At least one number is not zero.')
    elif num_tiles > 9:
        raise ValueError('At most nine numbers in the state.')
    matched_seq = list(range(1, num_tiles + 1))
    if len(state) != 9 or not all(isinstance(n, int) for n in state):
        raise ValueError('State must be a list contain 9 integers.')
    elif not all(0 <= n <= 9 for n in state):
        raise ValueError('The number in state must be within [0,9].')
    elif len(set(non_zero_numbers)) != len(non_zero_numbers):
        raise ValueError('State can not have repeated numbers, except 0.')
    elif sorted(non_zero_numbers) != matched_seq:
        raise ValueError('For puzzles with X tiles, the non-zero numbers must be within [1,X], '
                          'and there will be 9-X grids labeled as 0.')
    goal_state = matched_seq
    for _ in range(9 - num_tiles):
        goal_state.append(0)
    return tuple(goal_state)

def get_manhattan_distance(from_state, to_state):
    """
    INPUT: 
        Two states (The first one is current state, and the second one is goal state)

    RETURNS:
        A scalar that is the sum of Manhattan distances for all tiles.
    """
    from_state = np.array(from_state)
    to_state = np.array(to_state)
    shape = (3,3)
    from_state = from_state.reshape(shape)
    to_state = to_state.reshape(shape)
    # print(from_state)
    # print(to_state)
    distance = 0
    #loop throught the from_state matrix to get the indices
    for i in range(len(from_state)):
        for j in range(len(from_state[i])):
            if from_state[i][j] != 0:
                #indices of the current tile in from_state
                fromInd = (i,j)
                #indices of the current tile in to_state
                toInd = np.where(to_state == from_state[i][j])
                toInd = (toInd[0][0], toInd[1][0])
                if fromInd != toInd:
                    distance += abs(fromInd[0]-toInd[0]) + abs(fromInd[1]-toInd[1])
    return distance

def naive_heuristic(from_state, to_state):
    """
    INPUT: 
        Two states (The first one is current state, and the second one is goal state)

    RETURNS:
        0 (but experimenting with other constants is encouraged)
    """
    distance = 0
    return distance

def sum_of_squares_distance(from_state, to_state):
    """
    INPUT: 
        Two states (The first one is current state, and the second one is goal state)

    RETURNS:
        A scalar that is the sum of squared distances for all tiles
    """
    from_state = np.array(from_state)
    to_state = np.array(to_state)
    shape = (3,3)
    from_state = from_state.reshape(shape)
    to_state = to_state.reshape(shape)
    
    distance = 0
    #loop throught the from_state matrix to get the indices
    for i in range(len(from_state)):
        for j in range(len(from_state[i])):
            if from_state[i][j] != 0:
                #indices of the current tile in from_state
                fromInd = (i,j)
                #indices of the current tile in to_state
                toInd = np.where(to_state == from_state[i][j])
                toInd = (toInd[0][0], toInd[1][0])
                if fromInd != toInd:
                    distance += (fromInd[0]-toInd[0])**2 + (fromInd[1]-toInd[1])**2
    return distance

def print_succ(state, heuristic=get_manhattan_distance):
    """
    INPUT: 
        A state (list of length 9)

    WHAT IT DOES:
        Prints the list of all the valid successors in the puzzle. 
    """

    # given state, check state format and get goal_state.
    goal_state = state_check(state)
    # please remove debugging prints when you submit your code.
    # print('initial state: ', state)
    # print('goal state: ', goal_state)

    succ_states = get_succ(state)

    for succ_state in succ_states:
        print(succ_state, "h={}".format(heuristic(succ_state,goal_state)))


def get_succ(state):
    """
    INPUT: 
        A state (list of length 9)

    RETURNS:
        A list of all the valid successors in the puzzle (don't forget to sort the result as done below). 
    """
    succ_states = []
    state = np.array(state)
    shape = (3,3)
    state = state.reshape(shape)
    
    rowInd = np.where(state == 0)[0]
    colInd = np.where(state == 0)[1]
    #up, left, down, right
    moves = [(-1,0),(0,-1),(1,0),(0,1)] 
    #for each 0/open tile
    for i in range(len(rowInd)):
        #row ind and col ind of 0 
        r,c = rowInd[i], colInd[i]
        for movesr, movesc in moves:
            #current row ind and col ind after moving the tile to oprn square
            currRow, currCol = r+movesr, c+movesc
            #check that currRow and currCol within range of matrix
            if (currRow >= 0 and currRow < len(state)) and (currCol >= 0 and currCol < len(state[0])):
                #check that the tile at the current row and column is not none or 0
                if state[currRow][currCol] != None and state[currRow][currCol] != 0:
                    mod = np.copy(state)
                    #swap the tiles 
                    mod[r][c], mod[currRow][currCol] = mod[currRow][currCol], mod[r][c]
                    # print(state)
                    # print(type(mod.flatten().tolist()))
                    succ_states.append(mod.flatten().tolist())
    return sorted(succ_states)


def solve(state, goal_state=[1, 2, 3, 4, 5, 6, 7, 0, 0], heuristic=get_manhattan_distance):
    """
    INPUT: 
        An initial state (list of length 9)

    WHAT IT SHOULD DO:
        Prints a path of configurations from initial state to goal state along  h values, number of moves, and max queue number in the format specified in the pdf.
    """
    
    # This is a format helper，which is only designed for format purpose.
    # define "solvable_condition" to check if the puzzle is really solvable
    # build "state_info_list", for each "state_info" in the list, it contains "current_state", "h" and "move".
    # define and compute "max_length", it might be useful in debugging
    # it can help to avoid any potential format issue.

    # given state, check state format and get goal_state.
    goal_state = state_check(state)
    state = tuple(state)
    #check the puzzle is solvable
    solvable_condition = is_solvable(state, goal_state)
    if not solvable_condition:
        # print(inv)
        print(False)
        return
    else:
        print(True)
    

    # initializations    
    h = heuristic(state,goal_state)
    pq = []
    state_info_list = {state: [h, (0, h, None)]}
    visited = set()
    max_length = 1
    #pq, (cost (g+h), state (g, h, parent))
    heapq.heappush(pq,(h, state, (0, h, None)))
    #loop through the priority queue
    while pq:
        max_length = max(max_length, len(pq))
        #get current smallest cost path
        currCost, currState, currPath = heapq.heappop(pq)
        #if already seen or cost is higher than previously seen path
        if currPath[0] > state_info_list[currState][1][0]:
            continue
        # if currState in visited: 
        #     continue
        if currState == goal_state:
            # backtrace and get the path 
            path = []
            parentInd = currState
            while parentInd is not None:
                _, newPath = state_info_list[parentInd]
                #state, h, g
                path.append((parentInd, newPath[1], newPath[0]))
                parentInd = newPath[2]
            path.reverse()
            
            #print the state, h, moves
            for state_info in path:
                current_state = list(state_info[0])
                h = state_info[1]
                move = state_info[2]
                print(current_state, "h={}".format(h), "moves: {}".format(move))
            print("Max queue length: {}".format(max_length))
            return 
        
        visited.add(currState)
        _, stateInfo = state_info_list[currState]
        #list of successors
        successors = get_succ(list(currState))
        for s in successors:
            s = tuple(s)
            sG = stateInfo[0]+ 1
            
            if s in visited:
                continue
           
            if s not in state_info_list or sG < state_info_list[s][1][0]:
                sH = heuristic(s,goal_state)
                sCost = sH+sG
                parentInd = currState
                state_info_list[s] = [sCost, (sG, sH, parentInd)]
                heapq.heappush(pq,(sCost, s, (sG, sH, parentInd))) 
            
          
    return                 

#check if the puzzle is solvable 
def is_solvable(state, goal = [1,2,3,4,5,7,8,0]):
    numZeros = 0
    for num in state:
        if num == 0:
            numZeros += 1
    #state is 7 tile or less - always solvable
    if numZeros > 1:
        return True
    else:
        stateInv = 0
        for i in range(len(state)):
            for j in range(i+1, len(state)):
                if state[i] != 0 and state[j] != 0 and goal[i] != 0 and goal[j] != 0:
                    if state[i] > state[j]:
                        stateInv += 1
        if stateInv % 2 == 1:
            return False
        else:
            return True
                

if __name__ == "__main__":
    """
    Feel free to write your own test code here to exaime the correctness of your functions. 
    Note that this part will not be graded.
    # """
    # print_succ([2,5,1,4,0,6,7,0,3])
    # print()
    #
    # print(get_manhattan_distance([2,5,1,4,0,6,7,0,3], [1, 2, 3, 4, 5, 6, 7, 0, 0]))
    # print()
    # print(get_succ([2,5,1,4,0,6,7,0,3]))
    # print()
    #not solvable
    # solve([2,5,1,4,0,6,7,0,3], heuristic=get_manhattan_distance)
    # print()
    solve([4,3,0,5,1,6,7,2,0], heuristic=get_manhattan_distance)
    print()
    solve([1,2,3,4,5,6,7,0,0], heuristic=get_manhattan_distance)
    print()
    # solve([0,1,3,4,2,5,7,0,6], heuristic=get_manhattan_distance)
    # print()
    # solve( [5, 4, 1, 7, 6, 2, 0, 3, 0], heuristic=get_manhattan_distance)
    # print()
    # solve( [5, 4, 1, 7, 6, 2, 0, 3, 0], heuristic=naive_heuristic)
    # print()
    # solve([0,1,3,4,5,6,7,2,0], heuristic=get_manhattan_distance)
    # print()
    
    # solve([1, 3, 4, 7, 0, 2, 5, 8, 6], heuristic=get_manhattan_distance)
    # print()
    # print(sum_of_squares_distance([2,5,1,4,0,6,7,0,3], [1, 2, 3, 4, 5, 6, 7, 0, 0]))
    

****************
* CircuitTracer
* CS 221 - Section 3
* 04/30/2021
* Eli Sorensen
****************

OVERVIEW:

    CircuitTracer is an implementation of a search algorithm which
    parses a valid input .dat file representing a circuit board layout,
    in order to generate a list of the best possible (shortest) trace
    paths that connect two circuit components on the board representation.


INCLUDED FILES:

    CircuitBoard.java - updated source file with added functionality
    CircuitTracer.java - updated source file with added functionality
    Storage.java - source file
    TraceState.java - source file
    README - this file


BUILDING AND RUNNING:

    From the directory containing all source files, compile the driver
    class (and all dependent classes) with the command:
    $ javac CircuitTracer.java

    Run the compiled CircuitTracer class with the command and required
    command-line arguments:
    $ java CircuitTracer <-s | -q> <-c | -g> file
    options:
    -s 		uses Stack storage configuration
    -q 		uses Queue storage configuration
    -c 		prints solutions to console
    -g 		displays solutions in a GUI window
    file 		the name of a valid .dat input file

    Console output will report a list of the best possible traces for the
    layout encoded within the provided input .dat file.


PROGRAM DESIGN:

    CircuitTracer is the main driver class for the Circuit Tracer project.
    Beyond the main method, it defines a function for printing usage instructions
    upon incorrectly utilized command-line arguments at runtime, and its
    constructor contains an implementation for a search algorithm that can
    efficiently discover the best (shortest) traced circuit paths from a starting
    point to an ending point on a given circuit board layout when utilized in
    tandem with CircuitTracer's supporting class files.

    In order for CircuitTracer's search algorithm to analyze all possible trace
    paths and determine the most straightforward solution, it relies upon three
    supporting classes: CircuitBoard, an object which parses and establishes
    a 2D array-based representation of the provided layout input file, TraceState,
    an object representing a search state that includes a possible trace path
    through the CircuitBoard, and Storage, a container for such TraceStates with
    the ability to store them in either a Stack- or Queue-based data structure.

    Upon compilation, CircuitTracer passes the filename of a referenced input
    file to CircuitBoard for parsing. CircuitBoard attempts to locate and read
    the file following formatting guidelines for a board layout and storing properly
    formatted layouts within a 2D array. It utilizes exception handling and
    conditional checks to ensure that only correctly-written layouts are
    returned to CircuitTracer for solution path searching. CircuitBoard specifies
    methods for retrieving layout data and its subsequent, key characteristics.

    CircuitTracer's search algorithm initializes a new Storage object with either
    a stack or queue data structure based on user input at runtime, as well as
    an empty list for storage of possible trace solutions. Following such preparation,
    the algorithm finds all possible shortest trace paths by utilizing each point adjacent
    to the starting component on the CircuitBoard layout as a foundation for directing its
    search. Each TraceState within the stateStore storage object is checked to be a
    valid solution using the TraceState isComplete() method. If the path is complete,
    its length is compared to the current amount of solutions the algorithm has already
    discovered. Paths with the same length as the current best are added, those longer
    are discarded, and if the length is shorter, the solutions list is cleared with the new
    shortest length solution being added to it. Paths that do not result in a solution are
    searched for open, adjacent spots, which are added back to stateStore to be searched
    as outlined. This process repeats until stateStore is empty, meaning all paths have
    been searched and the solution has been found.


 TESTING:

    CircuitTracerTester, a given source test class, was the central means that drove
    test-driven development of CircuitTracer. CircuitTracerTester was completed prior
    to the implementation of additional functionalities in CircuitTracer and its
    supporting classes. This resulted in increased confidence in CircuitTracer's
    capabilities serving their use as expected. CircuitTracerTester runs a total
    of five passes. The first pass tests the validity of CircuitBoard's constructor,
    ensuring that it correctly parses files and creates correct representations of
    them to pass to CircuitTracer for search and solution finding. The next two passes
    test CircuitTracer, both with valid and invalid input files, to ensure that it
    outputs correct solutions or exception handling to the console. The fourth pass
    tests for correct printUsage information with invalid command-line arguments, and
    the final pass tests the output to the console about GUI support, expecting a
    message about its functionality. My CircuitTracer does not implement a GUI, so
    there is just a succinct explanation and clean program exit.


  DISCUSSION:

    More test-driven development was a nice benefit to this project, but
    at the same time, I felt that beyond the initial pass of tests the
    CircuitBoard constructor tests, that CircuitTracerTester didn't
    provide as much concrete/useful feedback as previous test classes
    have for other projects in this class and CS 121. I'm not sure
    how I would address that if I were in charge, because it was
    primarily testing just that one search algorithm inside CircuitTracer.
    That said, I guess the printUsage tests were also helpful in
    ensuring that I ended up with effective feedback for users.

    I had a little bit of trouble during the initial parsing of the
    .dat files in CircuitBoard's constructor, due to the fact that
    I had forgotten our practice with doing so in GridMonitor and
    FormatChecker, but after I remembered my experience with those
    previous assignments, it was pretty easy to implement that
    functionality. After I completed the parsing and format checking,
    the actual implementation of the provided search algorithm was
    very straightforward. I did overcomplicate the conditional checks
    for adding initial TraceStates to the bestPaths List, but after
    I fixed that portion, my code passed all the tests regarding
    output of traced grids to the console.

    Overall, I feel that this assignment was beneficial to my understanding
    of the Stack and Queue ADTs and algorithm implementation.


 ANALYSIS:

    *  How does the choice of Storage configuration (stack vs queue) affect the sequence in
    which paths are explored in the search algorithm? (This requires more than a
    "stacks are LIFOs and queues are FIFOs" answer.)

    -  The choice between a tack or queue configuration has several implications. Seeing as
    Stacks have a LIFO approach, the search algorithm will explore all possible paths that
    come from one of the first initial TraceState objects before exploring other solutions
    resulting from utilization of the other original TraceState objects that are adjacent
    to the first component on any given board layout. Subsequently, a queue's FIFO approach
    means that the search algorithm will explore paths in the order that they are presented
    to the algorithm.

    * Is the total number of search states (possible paths) affected by the choice of
    stack or queue?

    - In the example scenario total number of search states within stateStore is slightly
    affected by the choice between stack and queue storage. The stack configuration
    will have a maximum of 4 TraceStates for only 1 search cycle, whereas the queue storage
    will end up storing a maximum of 6 TraceState objects for 2 search cycles.

    * Is using one of the storage structures likely to find a solution in fewer steps than
    the other? Always?

    - In the example scenario, it takes the stack storage a total of 19 steps, from initialization
    to clearing of stateStore, to find a solution. Conversely, the queue configuration takes
    a total of only 18 steps to complete, finding the solution of paths equal to size three in
    one less search state than the stack configuration. This is representative of the greater fact
    that generally speaking, the queue storage can find solutions faster than the stack if the
    order they are added to the queue is one where the first TraceState that will be removed is
    part of a solution path. This the best case scenario, in which a queue configuration is
    faster than a stack.

    * Does using either of the storage structures guarantee that the first solution found will be a
    shortest path?

    - No, the storage structures do not guarantee this--in the example provided, both happened to
    find the shortest path first, but other possible solutions were discovered. These solutions
    were just not as short as the ones the algorithm happened to find, so bestPaths was never
    cleared. Still, that does not mean this will always be the case, because the variability in
    board layout and size can just as easily result in a solution being too long and the algorithm
    actually clearing bestPaths in favor of a different, shorter solution.

    * How is memory use (the maximum number of states in Storage at one time) affected by the choice
    of underlying structure?

    - The number of total TraceStates as stored in the queue storage is greater than that of the
    stack storage, which means that the stack storage, though it took one more step to find
    the solutions to the example layout, uses less memory. The maximum number of elements in
    stateStore as a stack (4 in the example) is smaller than the maximum size of a
    queue-configured stateStore (6 in the example).

    * What is the Big-Oh runtime order for the search algorithm? Does it reflect the maximum size of
    Storage? Does it reflect the number of board positions? Does it reflect the number of paths explored?
    Does it reflect the maximum path length? Is it something else? What is 'n'? What is the primary
    input factor that increases the difficulty of the task?

    - The runtime of the search algorithm is O(n) because the addition of a while loop within the
    algorithm elevates the runtime from O(1) to O(n)--there aren't any other steps which repeat
    themselves every time a TraceState is examined as a possible solution to add to bestPaths.
    It does reflect the maximum size of Storage because the algorithm relies upon whether or
    not stateStore is empty, so they cannot be of greater orders than each other. It does not
    reflect the number of board positions because the board has a finite number of positions, so
    that runtime order is just O(1). This is the same case for maximum path length, because the
    largest possible path length is greater than the runtime of an algorithm which only searches
    for the smallest, most efficient trace (like CircuitTracer). In this instance, the 'n' in the
    O(n) runtime represents the amount of input data for this search algorithm. The primary input
    factor is the total size of a board, regardless of adjacent spaces, because the larger the board
    becomes, the greater total number of possible paths that must be searched for, regardless of
    whether or not they are actual solutions.
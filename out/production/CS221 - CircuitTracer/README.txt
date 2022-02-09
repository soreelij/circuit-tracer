****************
* CircuitTracer
* CS 221 - Section 3
* 04/30/2021
* Eli Sorensen
****************

OVERVIEW:

 CircuitTracer is an implementation of a search algorithm which
 parses a valid input .dat file representing a circuit board layout,
 in order to generate a list of the best possible, shortest paths
 that connect two circuit components on the board representation.


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
 search. Each TraceState within


 TESTING:

  How did you test your program to be sure it works and meets all of the
  requirements? What was the testing strategy? What kinds of tests were run?
  Can your program handle bad input? Is your program  idiot-proof? How do you
  know? What are the known issues / bugs remaining in your program?

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

 * How is memory use (the maximum number of states in Storage at one time) affected by the choice
   of underlying structure?

 * What is the Big-Oh runtime order for the search algorithm? Does it reflect the maximum size of
   Storage? Does it reflect the number of board posisions? Does it reflect the number of paths explored?
   Does it reflect the maximum path length? Is it something else? What is 'n'? What is the primary
   input factor that increases the difficulty of the task?


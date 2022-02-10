# Circuit Tracer

* Author: Elijah Sorensen

## Overview

CircuitTracer is an implementation of a search algorithm which
parses a valid input .dat file representing a circuit board layout,
in order to generate a list of the best possible (shortest) trace
paths that connect two circuit components on the board representation.

## Compiling and Using

From the directory containing all .java and .dat files, run the compiled CircuitTracer class with the command and required
command-line arguments:

`````
$ java CircuitTracer < -s | -q > < -c | -g > file

options:
-s 		uses Stack storage configuration
-q 		uses Queue storage configuration
-c 		prints solutions to console
-g 		displays solutions in a GUI window
file 	the name of a valid .dat input file
`````

Console output will report a list of the best possible traces for the
layout encoded within the provided input .dat file.

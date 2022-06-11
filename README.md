# logic-tool
## Overview
A little command line tool written in java to help handling of logic formulas. 
The current version includes a parser for clause sets, a truth-table generator 
and a Davis-Putnam SAT-Solver.

## Motivation
The development of the tool was inspired by the CS course "Logic and Formal Systems" I am attending as a student at Leibniz University Hannover (LUH).
Homework exercises were a little bit tedious and to build up my java skills I decided to let the computer do the work. Thanks to Prof. Heribert Vollmer (Institut für Theoretische Informatik) and Prof. Matthias Becker (Human Computer Interaction Group) for the inspiration !

I am sure there are many other and far more powerful tools out there, so if you use this one, just have a look into the source code especially of the DP SAT Solver, and compare it to the Algorithm given in the Script of the course. 
I was in doubt that the algorithm would work, during the implementation I fully understood why it is really working despite of its simplicity !

## Requirements
To use it, you need a computer with java run time and compiler installed (I am using java 1.8 but many older versions should do it, too)

## Building the tool
Change to the top level directory and execute the build.sh script.

## Using the tool
Invoke the tool with

    java logic/Main

You get a prompt of the tools CLI.

The tool understands the following commands:
* def \<identifier> \<formula>   - define a formula and store it as global symbol
* ls   - list all global symbols 
* tt \<identifier>   - produce a truth table dump of specified formula
* dpsat \<identifier>  - invoke the Davis-Putnam SAT Solver on the specified formula
* q  - quit the tool

## Usage Example

    Welcome to LOGIC ! (Version 0.0.1)
    > ls
    total 0 symbols
    > def phi {{q4,~q2},{~q4,q1},{q3,q2},{~q4,~q1},{~q3,q2}}
    > ls
    phi={{q4,~q2},{~q4,q1},{q3,q2},{~q4,~q1},{~q3,q2}}
    total 1 symbols
    > tt phi
        q4     q2     q1     q3 result
    -----------------------------------
       0      0      0      0      0   
       1      0      0      0      0   
       0      1      0      0      0   
       1      1      0      0      0   
       0      0      1      0      0   
       1      0      1      0      0   
       0      1      1      0      0   
       1      1      1      0      0   
       0      0      0      1      0   
       1      0      0      1      0   
       0      1      0      1      0   
       1      1      0      1      0   
       0      0      1      1      0   
       1      0      1      1      0   
       0      1      1      1      0   
       1      1      1      1      0   
    -----------------------------------
    > dpsat phi
    DPSat1 iteration 0: {{q4,~q2},{~q4,q1},{q3,q2},{~q4,~q1},{~q3,q2}}
    resolve {~q4,q1},{~q4,~q1} using q1 -> {~q4}
    DPSat1 iteration 1: {{q4,~q2},{q3,q2},{~q3,q2},{~q4}}
    resolve {q4,~q2},{q3,q2} using ~q2 -> {q4,q3}
    resolve {q4,~q2},{~q3,q2} using ~q2 -> {q4,~q3}
    DPSat1 iteration 2: {{~q4},{q4,q3},{q4,~q3}}
    resolve {q4,q3},{q4,~q3} using q3 -> {q4}
    DPSat1 iteration 3: {{~q4},{q4}}
    resolve {~q4},{q4} using ~q4 -> {}
    DPSat1 iteration 4: {{}}
    set contains empty clause: {{}}
    result=false
    > 



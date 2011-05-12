========================
Running the demo scripts
========================

The demo scripts can each be compiled and run with a single command
by following these steps:

1  Install Sun's JDK or JRE (version 1.4 or above).
2  Place a copy of linguist.jar in the folder with the scripts.
3  Open a console window.
4  Navigate to the folder containing the scripts.
5  Type the following command (for Demo1.ls):

  java -cp linguist.jar eu.linguist.LS Demo1.ls -r

Note: The extension of a Linguist script is lowercase L, lowercase S.


===========================
Command syntax for LS:
===========================

  java -cp <classfiles> eu.linguist.LS

Opens the Help browser at the index page.

  java -cp <classfiles> eu.linguist.LS help <keyword>

Opens the Help browser at the page for <keyword>.

  java -cp <classfiles> eu.linguist.LS <script>.ls

Compiles the script, checking for syntax errors.

  java -cp <classfiles> eu.linguist.LS <script>.ls -o

Compiles the script to <script>.lrun.

  java -cp <classfiles> eu.linguist.LS <script>.ls -r

Compiles the script and runs the result.

  java -cp <classfiles> eu.linguist.LS <script>.lrun

Runs a precompiled script.

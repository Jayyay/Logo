# slogo
A development environment that helps users write SLogo programs.

- Names: Rachel Bransom, Jay Wang, Sam Curtis, Yilun Zhou
- Date started: October 7th
- Date finished: November 8th
- Estimated number of hours: 200
- Sam and Yiun worked on front end and Jay and Rachel worked on back end
- Impression: good for buulding team work and trying out new design concepts and tools like reflection. 

**Resources Used**
- http://www.oodesign.com
- http://www.baeldung.com/convert-file-to-input-stream
- http://java-buddy.blogspot.com
- http://www.stackoverflow.com
- https://gist.github.com/
- https://examples.javacodegeeks.com

**Files Used**
- No files were used to start the project
- To test the project we made our own files; FakeDisplayable and FakeTrackable, which have been included

**Using the program**
- The terminal works just like the command line
- Simply type the commands into the terminal
- You can influence the program by interacting with the GUI (buttons)
- Clicking on values in the option menu inputs them into the terminal where you can change the command as needed

**Bugs**
- We currently have no known bugs

**Extensions Implemented**

- Backend: All the extra commands, Save/Load worksapce, Groupinp, Scope, and Recursion.


**Command Decisions**

- Tell: Tell [ x ] will set the current turtle who reacts to commands to be x only. For example, tell [ 5 ] will make the turtle with ID 5 the only turtle who reacts to following input commands. Same id, if appearing more than once in tell [], will only be considered once (with the order being its first appearance).

- If we have multiple turtles, "fd set :x + :x 10" works by evaluating :x once for each turtle, thus each turtle will move different distance. Similarly fd * id :x will move turtles by different distance as well.

- Grouping: ( fd 10 20 30 40 ) will work as if we do fd 100, and the same is for ( sum 1 2 3 4 ), which will simply add all the numbers.

- We do not need the DEFINE command, since the orginal procedure already supports recursion perfectly. DEFINE command will result in a syntax error.

**Extra Features for the Terminal**

- The user can use toggle switch to change between Enter to Submit and Ctrl-Enter to Submit. Pressing the other key (key combination) will create a new line. 

- The user can use up and down arrow keys to navigate through history (just like a normal terminal). However, navigation won't work when there are more than one lines (because the user is more likely to navigate through lines in multi-line command). 


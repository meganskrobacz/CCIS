# To-Do Application Project
This was the first significant group project I undertook during my academic career in a computer science program. We were tasked to build a program that would accept input from a command line regarding to-dos a user might want to add, complete, edit, or view (including filtering options). Our program would write any to-dos to a CSV saved on the user’s machine, and process input commands as necessary to edit a user’s list of to-dos and update the CSV file as necessary.

## Assumptions:<br>
ValidateCommand:<br>
- Customer generally knows what are the appropriate commands to input.<br>
- Any command will be immediately followed by its argument (if any).<br>
- The ToDos will not have additional fields beyond ID, description, completed, priority, due date and category.<br>
- Only one ToDo can be added at a time.<br>

Write:<br>
- If an optional field is not entered a "?" is written to the CSV.<br>

Sort-by-priority<br>
- If a priority is not given, it will be listed after ToDos with priorities.<br>

Sort-by-date<br>
- If a due date is not given, it will be listed after ToDos with dates.<br>

Display<br>
- The information is printed to the command line.<br>


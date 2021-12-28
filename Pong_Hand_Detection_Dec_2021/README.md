# AI Pong with Hand Detection Input
<i> Class Project for CS 5100, Foundations of Artificial Intelligence, NEU Vancouver, Fall 2021 <br/>
Authors: Megan Skrobacz, Arpit Semwal, Dongxiao Zhao, Fangrui Guo </i>

This implementation is based heavily on the code provided by metalburr, found here: https://github.com/metulburr/pong. We have used this code for our base UI, with the following changes to his code:
- Updated several classes to have our custom logic for easy, medium, and hard AI levels
- Updated several classes to account for hand detection input for the up and down paddle controls instead of keyboard
- Updated splash.py to have our names

Additionally, we utilize the MediaPipe Hands library for input, found here: https://google.github.io/mediapipe/solutions/hands.html. 

### Dependencies and Troubleshooting

You must have pygame installed in your environment for this to work. Use the following command to do so:

<code>pip install pygame</code>

To play this game successfully, if you are on a Windows machine, you may need to copy the libmpg123-0.dll from the pygame installation location on your local machine (typically in AppData) to C:\Windows\System\System32 for the sounds to work, and for the game not to crash.

You must also have mediapipe and its dependencies to work. Use the following commands to do so:

<code>pip install mediapipe</code><br/>
<code>pip install opencv-python</code>

### Using Hand Detection

To utilize the hand detection input, make sure that you have a webcam to use. The game will be looking for your hand, and keeping track specifically of your index finger and thumb. You may use the keyboard as normal to start a game, but when the game is live, it will use the webcam to move the right paddle. 

To move the paddle up: Increase the distance between your index finger and thumb <br/>
To move the paddle down: Increase the distance between your index finger and thumb <br/>
To keep the paddle stationary: Keep your hand still <br/>

The game will also open a second window when launched, which should show you how it is viewing your hand.

### Design and Levels

In this implementation, the user is on the right side of the board, and the AI is on the left. In all levels, the AI will only move the paddle when the ball is moving towards it, and will stay in place when the ball is moving towards the human player's paddle.

Our game has three levels:

#### Easy
On easy mode, the AI paddle uses a Markov Decision Process to try and "predict" where the ball will go. To do this, the AI breaks its side of the board up into 3 horizontal quadrants, each of equal height. Depending on what the equation decides is the most likely/has the highest score, the AI will move the paddle to the middle of that quadrant, with a little bit of variance while moving to keep things interesting. However, on easy mode the AI can only "see" the ball when it is on it's side of the "court". This means it does not have very much time to move appropriately and will oftentimes miss.

For the algorithm itself, the equation mainly uses the ball to calculate the transition function and the reward. For the transition function, the algorithm will look at whether the ball is moving up or down; if the ball is moving down, it will place a higher probability on the lower quadrants, and if it's moving up it will place a higher probability on the upper quadrants. For the reward function, the algorithm takes into account the location of the ball in relation to the actual quadrant itself, specifically the middle y coordinate of the quadrant. It will reward positively if the ball is very close or moderately close to the middle of the quadrant, and penalize if it is far away.

To run the game on easy difficulty: <code>python game.py -d easy</code>

#### Medium
On medium mode, the AI paddle only picks the ideal move 50% of the time -- specifically, placing the paddle in a position where the ball hits the center of the paddle. The remaining 50% of the time, the paddle has an equal probability to move up or move down. Additionally, the paddle will only move when the ball is on it's side of the "court" (i.e. on the left side of the board).

To run the game on medium difficulty: <code>python game.py -d medium</code>



#### Hard
On hard mode, the AI paddle only picks the ideal move 100% of the time. Additionally, the paddle will constantly position itself ideally when the ball is moving towards it, regardless of where it is on the board.

To run the game on hard difficulty: <code>python game.py -d hard</code>


### Possible Improvements
For future iterations of this game, I would love to create a level which utilizes reinforcement learning, as I think this technique would be ideal for a game such as Pong.
<h1> NEUcre Project </h1>
This was my first big project using C for my Algorithms class. This was a take on the game Eucre (https://en.wikipedia.org/wiki/Euchre). Although some starter code was provided to us, we were responsible for actually implementing the functions and logic.

Below is my write-up for the assignment I completed at the time.

<h2> Overview of Tasks and Development Process</h2>
<h3> Milestone 1 </h3>
For this assignment, I broke it down into a few basic tasks. The first, after pulling the starter files and copying them into my own repo, was to write skeletons for the functions required in deck.c. To do that, I copied the comments and structures as broken down in the a4.h header file. Then, I also looked at some of the Card functions that would probably need to be implemented in order for those functions to work properly (i.e. CreateCard() and DestroyCard()). Then, I would implement logic/code to make sure these functions work, and tested that by running the test file, with the un-implemented/part 2 functions commented out. Finally, I would run a valgrind once my functions were working to make sure I didn't have any memory leaks.<br><br>

<h3> Milestone 2 </h3>
This milestone really involved implementing the functions of the game, as well as the hand. I broke this down into four major areas - first, getting the structure of the hand working (such as creating a blank hand and destroying it when necessary); second, implementing functions that affect the contents of said hand (such as adding and removing cards from the hand); third, creating functions that affect the deck (shuffle, etc); and finally, functions that keep track of things like score and legal moves.

<h2> Key Takeaways</h2>
<h3> Milestone 1</h3>
Overall, I found this assignment quite challenging, particularly when it came to working with valgrind, of which I have had no real exposure. However, overall many of the functions were more or less straightforward. I found that malloc'ing memory to the heap instead of the stack really seemed to help when working with bigger items (such as arrays); however, it was indeed tricky to remember to free those nodes whenever we no longer needed them. I definitely feel that more practice in this area should sharpen my skills. Additionally, I often felt that it was tempting to use a simple for loop in places, but once you start valgrinding things you realize just how much memory that takes up. As such, I really found myself re-using a lot of the functions I already implemented and tested, which ultimately is best practice anyway.

<h3> Milestone 2</h3>
While Milestone 1 was quite challenging, Milestone 2 was a completely new level by comparison. As I was coding, I found that I really relied heavily on drawing things out, as the nested double loop was almost impossible for me otherwise to keep track of. I also felt that this assignment really helped solidify my understanding of why pointers are so important, and the power that they can provide to a programmer; if we worked directly with the data itself instead of a pointer that referenced the area we were changing, we would have a LOT less flexibility.

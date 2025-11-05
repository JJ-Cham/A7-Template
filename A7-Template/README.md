# A7 DIY SpellChecker

Your readme should include the following information. **Each student needs to submit all of this information themselves, even when pair programming.**

## General Information

Programming Partner Name (if you'd like to be graded together):

Other Collaborators (and kudos to helpful members of the class):

Any references used besides JavaDoc and course materials:

## Assignment Reflection

Please reflect on your experience with this assignment. What was most challenging? What was most interesting?

The most challenging part was debugging the file reading logic in WordValidation. At first, the program wasn’t loading words.txt correctly, which made all the spell-checking tests fail silently. It took careful inspection of the working directory, file placement, and Scanner behavior to realize that Java looks for files relative to the runtime location, not the source folder. Adding diagnostic print statements like System.out.println(System.getProperty("user.dir")) helped pinpoint the issue.

Another challenge was designing the nearMisses method to cover all five edit types — deletions, insertions, substitutions, transpositions, and splits — without generating duplicates. It required a methodical approach to string manipulation and edge-case testing, especially for short or oddly structured words. 

The most interesting part was implementing the logic for "splits" in nearMisses. Unlike the other edit types, splits involve checking two separate substrings against the dictionary and combining them into a single suggestion.
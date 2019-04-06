# COSC 310 - Haodan Xue
Assignment 3 for COSC 310

# Table of Contents
```
1.  Brief Description
2.  Function List and System Improvement
2.1.    TestConversation class
2.2.    Talking class
2.3.    Utils class
2.3.1.  The public static ArrayList getSynonym (String words) method
2.3.2.  The Public static String sentiment (String line) method
2.3.3.  The public static ArrayList < String > getPOS (String field, String line) method
2.3.4.  The public static ArrayList < String > getNamed Entity Recognition (String field, String line) method
2.3.5.  The Public static Array List < String > get Coreference Resolution (String line) method
2.3.6.  The public static Boolean check (String sentence) method
2.4.    MyConversation class
3.  Sample Output 
4.  Development Schedule
```

# Brief description
On the basis of the previous assignment, I added a function of doing a psychological test to do a psychological test for a person, in order to determine whether or not his/her mental state during the current period is positive or negative. In addition to GUI, Stanford NLP and WordNet toolkits are also used.

![UML](https://github.com/haodanxue/COSC310-Assignment3-Haodan-Xue/blob/1th-feature/UML_Assignment3.jpg)

![DFD](https://github.com/haodanxue/COSC310-Assignment3-Haodan-Xue/blob/1th-feature/Level0_DFD_Haodan.jpg)


# Function List and System Improvement
```
2.1 TestConversation class
    Server-side Class Start Services. In the TestConversation class, I added a function menu to choose between therapy testing or psychological testing. If the input function does not match, the user will be asked to choose again.

2.2 Talking class
Client-side GUI interface. During chatting, the reason why it may take very long time and not receive responses from the server is because the tool analysis of OpenNLP is too slow. During this period, please do not enter anything. Otherwise, the input will be considered by the server as the input of next question. 

2.3 Utils class
2.3.1  The public static ArrayList getSynonym (String words) method.
•   Function: Synonym recognition gets all synonyms of parameter words.

2.3.2  Public static String sentiment (String line) method.
•   Function: Sentiment analysis to analyze whether or not the emotions of sentences are positive or negative.

2.3.3  The public static ArrayList < String > getPOS (String field, String line) method.
•   Function: POS tagging to obtain the corresponding part of speech word set.

2.3.4  The public static ArrayList < String > getNamed Entity Recognition (String field, String line) method.
•   Function: Named entity recognition retrieves a collection of named entities of the corresponding type.

2.3.5  The Public static Array List < String > get Coreference Resolution (String line) method.
•   Function: Coreference Resolution takes the same word key as the person in the sentence, which means the person in the sentence is the same.

2.3.6  The public static Boolean check (String sentence) method.
•   Function: Check for erroneous words.

2.4 MyConversation class
 Psychological test class. 
•   In this psychometric logic, you first ask the user about his/her occupation. Then, according to the Named entity record function, you first look at the words in the answer to see if the answer contains the words appeared in the word.txt. The word.txt is a lexicon. 
If the answer does not contain the words appeared in the word.txt, this means that the input is wrong. If the right words were said to be wrong words, it is because the information of the lexicon is incomplete. You can add a new column and find the word which its type is "TITLE" in the answer. This word is the occupation in the answer. 
•   Then ask the user's gender. POS tagging function finds the word which its part of speech is "NN" in the answer. That is, the noun, which is the gender noun in the answer. 
•   Then start the psychological test. Whenever asking a question, the corresponding answer will be analyzed by Sentiment Analysis function to determine whether the emotion of the answer is positive or negative. After the final test, the emotion of the user will be eventually determined based on the analysis result. 
```

# Sample Output
![](https://github.com/haodanxue/COSC310-Assignment3-Haodan-Xue/blob/1th-feature/A3_SampleOutput1.png)

![](https://github.com/haodanxue/COSC310-Assignment3-Haodan-Xue/blob/1th-feature/A3_SampleOutput2.png)
# Development schedule
![](https://github.com/haodanxue/COSC310-Assignment3-Haodan-Xue/blob/master/A3__GanttChart1.png)

![](https://github.com/haodanxue/COSC310-Assignment3-Haodan-Xue/blob/master/A3__GanttChart2.png)

=======
>>>>>>> branch '2th-feature' 

https://github.com/haodanxue/COSC310-Assignment3-Haodan-Xue/tree/2th-feature


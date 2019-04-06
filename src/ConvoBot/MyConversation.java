package ConvoBot;

import Testing.Utils;

import java.io.IOException;

public class MyConversation {

    private int negative;
    private int positive;

    public void test(String line){
        PrintMessage.messageFromBot(line);
        String response = PrintMessage.messageFromUser();
        while (!Utils.check(response)){
            response = PrintMessage.messageFromUser();
        }
        try {
            if(Utils.sentiment(response).contains("negative")){
                negative++;
            }else {
                positive++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    };

    public MyConversation() throws IOException {
        negative=0;
        positive=0;
        PrintMessage.messageFromBot("hello, I am your Psychotherapy, robot nice,What are you doing now?");
        String response = PrintMessage.messageFromUser();
        while (!Utils.check(response)){
            PrintMessage.messageFromBot("you misspelled the word, please re-enter");
            response = PrintMessage.messageFromUser();
        }
        if(response.split(" ").length==1) {
            PrintMessage.messageFromBot("So your job is " + response);
        }else {
            PrintMessage.messageFromBot("So your job is " + Utils.getNamedEntityRecognition("TITLE",response).get(0));
        }


        PrintMessage.messageFromBot("Are you a man or a woman?");
        response = PrintMessage.messageFromUser();
        while (!Utils.check(response)){
            response = PrintMessage.messageFromUser();
        }

        PrintMessage.messageFromBot("So you are a " + Utils.getPOS("NN",response).get(0));


        test("Do you have a good relationship with your colleagues?");

        test("And can you complete your task on time?");

        test("Are you full of hope for the future?");


        if(positive>0&&positive>negative){
            PrintMessage.messageFromBot("Your mental condition is good, keep a positive attitude, you will have a good life");
        }else {
            PrintMessage.messageFromBot("You have a lot of pressure during this time, relax your stress, tell yourself every day that you are the best, you will do better.");
        }

        PrintMessage.messageFromBot("bye!");
    }
}

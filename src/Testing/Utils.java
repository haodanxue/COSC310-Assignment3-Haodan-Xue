package Testing;

import java.io.*;
import java.net.URL;
import java.util.*;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;
import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.*;

public class Utils {

  public static HashSet wordSet;


  //Get synonyms
  public static ArrayList getSynonym(String words) throws IOException {
    ArrayList arrayList = new ArrayList();
    //Create an URL which points to the catalog of the WordNet dictionary
    String wnhome = System.getenv("WNHOME");
    String path = wnhome + File.separator + "dict";
    URL url = new URL("file", null, path);

    //Create dictionary object and open it
    IDictionary dict = new Dictionary(url);
    dict.open();

    // look up first sense of the word "go"
    //IIndexWord idxWord2 = dict.
    IIndexWord idxWord =dict.getIndexWord("go", POS.VERB);
    IWordID wordID = idxWord.getWordIDs().get(0) ; // 1st meaning
    IWord word = dict.getWord(wordID);
    ISynset synset = word.getSynset (); //ISynset is an interface of a word's synonym set.


    // iterate over words associated with the synset
    for(IWord w : synset.getWords())
      arrayList.add(w.getLemma());//Every synonym in the synonym set.
    return arrayList;
  }

  //Function: Emotion analysis --> analysis whether the emotion of the answer is positive or negative. 
  public static String sentiment(String line) throws IOException {

    Properties props = new Properties();
    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");

    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

    Annotation annotation;

    annotation = new Annotation(line);

    pipeline.annotate(annotation);

    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
    if (sentences != null && ! sentences.isEmpty()) {
      CoreMap sentence = sentences.get(0);
      return sentence.get(SentimentCoreAnnotations.SentimentClass.class);
    }

    return null;

  }

  //function: retrieves the word set for the corresponding part of speech: For example: I am a student. "student" is type named "NN" which represents noun.
  public static ArrayList<String> getPOS(String field,String line){
    ArrayList<String> arrayList = new ArrayList<>();
    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");    // There are seven Annotators
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);    //Processing in order

    Annotation document = new Annotation(line);    // Using text to create an empty annotation
    pipeline.annotate(document);                   // Execute all the annotators (7 annotators) to text

    // "sentences" contains all of the analysis result. Retrieving the result by traversing it. 
    List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

    for(CoreMap sentence: sentences) {
      for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {

        String word = token.get(CoreAnnotations.TextAnnotation.class);            // Retrieve word segmentation
        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);     // Retrieving part-of-speech tagging
        if(pos.equals(field)){
          arrayList.add(word);
        }
      }
    }
    return arrayList;
  }

  
  //function: retrieve the named collection of objects for the corresponding category. For example, "I am a student" where "student" is an object of TITLE, however, "I", "am", "a" are type of zero.
  public static  ArrayList<String>  getNamedEntityRecognition(String field,String line){
    ArrayList<String> arrayList = new ArrayList<>();
    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");    // There are Annotators
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);    // Processing in order


    Annotation document = new Annotation(line);
    pipeline.annotate(document);

    List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

    for(CoreMap sentence: sentences) {
      for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {

        String word = token.get(CoreAnnotations.TextAnnotation.class);            // Retrieve word segmentation
        String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);    // Retrieve named objects recognition result
        if(ne.equals(field)){
          arrayList.add(word);
        }
      }
    }
    return arrayList;
  }

  //function: retrieve the word which is equivalent to the referent in a sentence. If the key is the same, this represents that the referent is the same. For example, "I love you, but you don't love me." "I" and "me" point to the same person.
  public static ArrayList<String> getCoreferenceResolution(String line){

    ArrayList<String> arrayList = new ArrayList<>();
    Properties props = new Properties();
    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

    Annotation annotation = new Annotation(line);
    pipeline.annotate(annotation);
    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

    Map<Integer, CorefChain> corefChains =
            annotation.get(CorefCoreAnnotations.CorefChainAnnotation.class);
    if (corefChains == null) { return null; }
    for (Map.Entry<Integer,CorefChain> entry: corefChains.entrySet()) {
      for (CorefChain.CorefMention m : entry.getValue().getMentionsInTextualOrder()) {
        // We need to subtract one since the indices count from 1 but the Lists start from 0
        List<CoreLabel> tokens = sentences.get(m.sentNum - 1).get(CoreAnnotations.TokensAnnotation.class);
        // We subtract two for end: one for 0-based indexing, and one because we want last token of mention not one following.
        arrayList.add(entry.getKey()+","+m);
      }
    }
    return arrayList;
  }

  //function: check if there are words that have spelling mistakes. If the words of input are not in the file, this means the input is wrong.
  public static boolean check(String sentence){
    if (wordSet == null){
        wordSet= new HashSet();
      try {
        FileReader fileReader = new FileReader("word.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine())!=null){
          wordSet.add(line);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
//
    String[] words = sentence.replace(".","").replace("!","").replace(",","").split(" ");
    for (int i=0;i<words.length;i++){
      if ((wordSet.add(words[i]))){
        wordSet.remove(words[i]);
        return false;
      }
    }
    return true;
  }


  public static void main(String[] args) throws IOException {
//    System.out.println(Utils.sentiment("I feel like good!"));
//    System.out.println(Utils.check("I am a good man"));
//    System.out.println(Utils.getNamedEntityRecognition("TITLE","I am a student").get(0));//return student
    System.out.println(Utils.getPOS("NN","I am a man").get(0));//return student
//      ArrayList<String> arrayList = Utils.getCoreferenceResolution("I love you,but you don't love me.");
//      for(String str:arrayList){
//        System.out.println(str);
//      }
  }

}

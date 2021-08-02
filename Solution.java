import java.io.*;
import java.util.*;

public class Solution {



    public static void main(String[] args) throws IOException {
        String path = "CodingChallenge.txt";
        String text = getText(path);
        String[] sentences = text.split("[.?!]");
        Map<String, Integer> wordCounts = new HashMap<>();
        Map<String, String> lastSentences = new HashMap<>();
        int count = countWords(sentences,wordCounts,lastSentences);
        Pair[] top10Words = getTopKWords(10, wordCounts);
        String mostUsedWord =top10Words[0].getWord();
        String lastSentence = lastSentences.get(mostUsedWord);


        System.out.println("*******Result********");
        System.out.println("Total number of words: "+ count);
        System.out.println("Top 10 words with their corresponding counts");
        for (Pair pair: top10Words){
            System.out.println(pair.getWord()+" : "+ pair.getCount());
        }
        System.out.println("Last sentence with most used word");
        System.out.println(lastSentence);




    }

    /**
     * Reads the file with the given file path
     * @param path input file path
     * @return text in the file
     * @throws IOException when file reading fails
     */
    public static String getText(String path) throws IOException {
        
        StringBuilder stringBuilder = new StringBuilder();
        //try with resource
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            String line = bufferedReader.readLine();
            while(line!=null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }

        return stringBuilder.toString();


    }

    /**
     * populates the wordCount and lastSentences map from the given array of sentences
     * @param sentences array of input sentences
     * @param wordCounts Hashmap to store word and its count
     * @param lastSentences HashMap to store word and the last sentence it is used
     * @return the total number of words
     */
    public static int countWords(String[] sentences,  Map<String, Integer> wordCounts,  Map<String, String> lastSentences ){
        int count =0;
        for(String sentence: sentences){
            if(!sentence.isEmpty()){
                String[] words = sentence.split("[\"\\s,:;/]+");
                for(String word: words){
                    word = word.trim().toLowerCase();
                    if(word.isEmpty())
                        continue;
                    wordCounts.put(word, wordCounts.getOrDefault(word,0)+1);
                    lastSentences.put(word,sentence);
                    count++;
                }
            }
        }
        return count;

    }

    /**
     * Returns the top k most used word in descending order along with their frequencies
     * @param k number of most used words
     * @param wordCount HashMap containing the counts of each word
     * @return top k most used to words populated in array of Pairs
     */
    public static Pair[] getTopKWords(int k, Map<String,Integer> wordCount){
        Pair[] res = new Pair[k];

        PriorityQueue<Pair> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Pair::getCount));
        for(Map.Entry<String, Integer> entry: wordCount.entrySet()){
            priorityQueue.offer(new Pair(entry.getKey(),entry.getValue()));
            if(priorityQueue.size()>k){
                priorityQueue.poll();
            }
        }



        for(int i=k-1; i>=0; i--){
            if(priorityQueue.isEmpty())
                break;
            res[i] = priorityQueue.poll();
        }
        return res;
    }


}

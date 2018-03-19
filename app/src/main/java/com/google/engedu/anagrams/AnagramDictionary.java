/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    ArrayList<String> wordlist;
    HashMap<String,ArrayList<String>> lettersToWord;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        lettersToWord = new HashMap<>();
        wordlist = new ArrayList<String>();
        while((line = in.readLine()) != null)
        {
            String word = line.trim();
            wordlist.add(word);
            if (lettersToWord.containsKey(sortLetters(word)))
            {
                ArrayList<String> anagrams = lettersToWord.get(sortLetters(word));
                anagrams.add(word);
                lettersToWord.put(sortLetters(word),anagrams);
            }
            else
            {
                ArrayList<String> anagrams = new ArrayList<>();
                anagrams.add(word);
                lettersToWord.put(sortLetters(word),anagrams);
            }
        }
    }

    public String sortLetters(String input_word)
    {
        char word_array[] = input_word.toCharArray();
        Arrays.sort(word_array);
        return new String(word_array);
    }

    public boolean isGoodWord(String word, String base) {
        if (word.contains(base) || !wordlist.contains(word))
            return false;
        else
            return true;
    }

    public List<String> getAnagrams(String targetWord) {
        List<String> result = new ArrayList<>();
        result = lettersToWord.get(sortLetters(targetWord));
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> anagrams;
        ArrayList<String> result = new ArrayList<String>();
        for(char alpha='a';alpha<='z';alpha++)
        {
            String temp_word = word + alpha ;
            String sorted = sortLetters(temp_word);

            if (lettersToWord.containsKey(sorted))
            {
                anagrams = lettersToWord.get(sorted);
                for(int i=0;i<anagrams.size();i++)
                {
                    if(!(anagrams.get(i).contains(word)))
                    {
                        result.add(anagrams.get(i));
                    }
                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        ArrayList<String> anagrams;
        String result;
        result="STOP";
        int length = wordlist.size();
        for (int i=0;i<length;i++) {
            int no = random.nextInt(length);
            result = wordlist.get(no);
            anagrams = lettersToWord.get(sortLetters(result));
            if (anagrams.size() > 1)
                break;
        }
        return result;
    }
}

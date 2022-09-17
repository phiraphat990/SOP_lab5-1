package com.example.lab5;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class WordPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    protected Word words;
    public WordPublisher(){
        words = new Word();
    }
    @RequestMapping(value="/addBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> addBadWord(@PathVariable("word") String s){
        words.badWords.add(s);
        return words.badWords;
    }
    @RequestMapping(value="/delBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s){
        words.badWords.remove(s);
        return words.badWords;
    }
    @RequestMapping(value="/addGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> addGoodWord(@PathVariable("word") String s){
        words.goodWords.add(s);
        return words.goodWords;
    }
    @RequestMapping(value="/delGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s){
        words.goodWords.remove(s);
        return words.goodWords;
    }

    @RequestMapping(value="/proof/{sentence}", method = RequestMethod.GET)
    public String proofSentence(@PathVariable("sentence") String s){
        //เช็ค
        boolean isbad = false;
        boolean isgood = false;

        for(String word: words.goodWords){
            if(s.contains(word)){
                isgood = true;
                break;
            }
        }

        for(String word: words.badWords){
            if(s.contains(word)){
                isbad = true;
                break;
            }
        }

        if(isgood && isbad){
            rabbitTemplate.convertAndSend("Fanout","",s);
            return "Found Bad & Good Word";
        }
        else if(isgood){
            rabbitTemplate.convertAndSend("Direct","good",s);
            return "Found Good Word";
        }
        else if(isbad){
            rabbitTemplate.convertAndSend("Direct","bad",s);
            return "Found Bad Word";
        }else {
            return  "";
        }

}}

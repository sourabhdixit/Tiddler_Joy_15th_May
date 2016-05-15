package com.whizkidzmedia.tiddlerjoy.Utilities;

import com.squareup.okhttp.MediaType;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildVideo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sourabh Dixit on 15-03-2016.
 */
public class AppConstants {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static ArrayList<String> videoUrl = new ArrayList<>(Arrays.asList("3xqqj9o7TgA","cE3LzPQgQmk","RhDDIRZ4Lxo","r4Ew_-SHI7Q","aRkBmEUNR3Q","5oZxcimHWXE","Qgq8nZqYNmE","AL96lTu-4Yw")) ;
    public static ArrayList<String> videoUiTag = new ArrayList<>(Arrays.asList("Rhythm","Story","Activity","Wildcard","Rhythm","Story","Activity","Wildcard")) ;
    public static String[] beautifulWorldTags = new String[]{" "," "," "," "," "," "," "," "};
    public static String[] allAboutMeTags = new String[]{"Myself"," "," "," ","Myself","Self Control","Fitness"," "};
    public static String[] communicationTags = new String[]{"Music","Literacy","Literacy","Literacy","Dance"," ","Dance", "Literacy"};
    public static String[] creativityTags = new String[]{" ","Problem Solving","Visual Arts","Role Play"," "," ","Performing Arts","Problem Solving"};
    public static String[] earlyMathsTags = new String[]{" "," "," "," "," "," "," "," "};
    public static ArrayList<String> videoTitles = new ArrayList<>(Arrays.asList("The Finger Family Song","Bob The Train's Alphabet Adventure","Wobbly Clown!, Minute Make, Mister Maker","Handy Manny - 'The Great Garage Rescue' Sneak Clip!","Head Shoulders Knees and Toes Nursery Rhyme","The Monkey and the Crocodile Story"
    ,"Debbie Doo & Friends! - Let's Star Jump!","Learn About The Letter A - Preschool Activity"));
    public static ArrayList<ChildVideo> CHILDVIDEOS;
    public static ArrayList<String> myselfTopics,mathsTopics,creativityTopics,worldAroundMeTopics,languageTopics;

    public static void addVideos()
    {
        CHILDVIDEOS = new ArrayList<ChildVideo>();
        for(int i=0;i<8;i++)
        {
            ChildVideo video = new ChildVideo();
            video.videoId=String.valueOf(i);
            video.videoUrl = videoUrl.get(i);
            video.videoUiTag = videoUiTag.get(i);
            video.videoTitle = videoTitles.get(i);
            video.allAboutMeTags = allAboutMeTags[i];
            video.beautifulWorldTags = beautifulWorldTags[i];
            video.communicationTags = communicationTags[i];
            video.creativityTags = creativityTags[i];
            video.earlyMathsTags = earlyMathsTags[i];
            video.isVideoWatched = false;
            video.save();
            CHILDVIDEOS.add(video);
        }
    }

    public static void initTopics() {
        myselfTopics = new ArrayList<String>();
        myselfTopics.add("Food");
        myselfTopics.add("Hygiene");
        myselfTopics.add("Safety");
        myselfTopics.add("Security");
        myselfTopics.add("Fitness");
        myselfTopics.add("Self Care");
        myselfTopics.add("Self Control");
        myselfTopics.add("Myself");
        myselfTopics.add("Social Skills");
        myselfTopics.add("Celebrate Diversity");
        mathsTopics = new ArrayList<String>();
        mathsTopics.add("Critical Thinking");
        mathsTopics.add("Numbers");
        mathsTopics.add("Visual Spatial");
        creativityTopics = new ArrayList<String>();
        creativityTopics.add("Problem Solving");
        creativityTopics.add("Role Play");
        creativityTopics.add("Visual Arts");
        creativityTopics.add("Performing Arts");
        worldAroundMeTopics = new ArrayList<String>();
        worldAroundMeTopics.add("World Around Me");
        worldAroundMeTopics.add("Living World");
        worldAroundMeTopics.add("My Planet");
        worldAroundMeTopics.add("People & Places");
        worldAroundMeTopics.add("How Things Work");
        worldAroundMeTopics.add("Technology");
        languageTopics = new ArrayList<String>();
        languageTopics.add("Language Development");
        languageTopics.add("Signs and Symbols");
        languageTopics.add("Visual Communication");
        languageTopics.add(" Creative Expression");
//        languageTopics.add("Gesture");
//        languageTopics.add("Sign Language");
//        languageTopics.add("Graphic");
//        languageTopics.add("Color");
//        languageTopics.add("Symbol");
//        languageTopics.add("Maps");
//        languageTopics.add("English");
//        languageTopics.add("Hindi");
//        languageTopics.add("Literacy");
    }
    public static List<String> getMyselfTopics(int start, int end)
    {
        if(end>myselfTopics.size())
            end=myselfTopics.size();
        return myselfTopics.subList(start, end);
    }
    public static List<String> getMathsTopics(int start, int end)
    {   if(end>mathsTopics.size())
            end=mathsTopics.size();
        return mathsTopics.subList(start, end);
    }
    public static List<String> getCreativityTopics(int start, int end)
    {   if(end>creativityTopics.size())
            end=creativityTopics.size();
        return creativityTopics.subList(start, end);
    }
    public static List<String> getCommunicationTopics(int start, int end)
    {   if(end>languageTopics.size())
            end=languageTopics.size();
        return  languageTopics.subList(start, end);
    }
    public static List<String> getWorldAroundMeTopics(int start, int end)
    {   if(end>worldAroundMeTopics.size())
            end=worldAroundMeTopics.size();
        return  worldAroundMeTopics.subList(start, end);
    }

    public static ArrayList<String> getMyselfTopics()
    {
        return  myselfTopics;
    }
    public static ArrayList<String> getMathsTopics()
    {
        return  mathsTopics;
    }
    public static ArrayList<String> getCreativityTopics()
    {
        return creativityTopics;
    }
    public static ArrayList<String> getCommunicationTopics()
    {
        return languageTopics;
    }
    public static ArrayList<String> getWorldAroundMeTopics()
    {
        return  worldAroundMeTopics;
    }
}

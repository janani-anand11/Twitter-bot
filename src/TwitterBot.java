import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class TwitterBot {
    private static PrintStream consolePrint;
    public static void main(String[] args) throws TwitterException, IOException {
        TwitterMethods tweet = new TwitterMethods(consolePrint);
        Scanner sc = new Scanner(System.in);

        System.out.println("Options:");
        System.out.println("1.Tweet\n2.Show home feed.\n3.Show feed of an account.\n4.Search tweets.\n");
        ;
        System.out.println("Enter the option number:");

        int op = sc.nextInt();
        try {
            switch (op) {
                case 1: tweet.tweetmethod();
                    break;
                case 2: tweet.showTimeline();
                    break;
                case 3: tweet.twitterFeed();
                    break;
                case 4: tweet.searchTweet();
                    break;
                default:
                    System.out.println("Enter the correct option number.");
            }
        }
        catch(TwitterException e){
            if(e.isCausedByNetworkIssue()){
                System.out.println("Please check your network and try again.");
            }
        }
    }
}

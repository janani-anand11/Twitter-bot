import twitter4j.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TwitterMethods {
    private Twitter twitter;
    private List<Status> statuses;
    private PrintStream consolePrint;
    private Scanner sc;
    private String user;

    public TwitterMethods(PrintStream console) {
        twitter = TwitterFactory.getSingleton();
        consolePrint = console;
        statuses = new ArrayList<Status>();
        sc = new Scanner(System.in);
    }

    public void tweetmethod() throws TwitterException, IOException {
        System.out.println("Enter the tweet:");
        boolean flag = true;
        try {
            while (flag) {
                String msg = sc.nextLine();
                if (msg.length() <= 280) {
                    twitter.updateStatus(msg);
                    System.out.println("\nYour tweet was successfully posted on twitter!!\n");
                    flag = false;
                } else
                    System.out.println("Tweet is exceeding character limit(280).\nEnter the tweet:");
            }
        }
        catch(TwitterException e) {
            if (e.getErrorMessage()!= null && e.getErrorMessage().equals("Status is a duplicate.")) {
                System.out.println("The tweet has been already posted. Post a new tweet");
                tweetmethod();
            }
            else if(e.isCausedByNetworkIssue()) {
                System.out.println("Please check your network and try again.");
            }
        }
    }

    public void showTimeline() throws TwitterException, IOException {
        statuses = twitter.getHomeTimeline();
        System.out.println("Showing home timeline: ");
        for (Status status : statuses) {
            System.out.println(status.getUser().getName() + ":" +
                    status.getText());
        }
    }

    public void twitterFeed() throws TwitterException, IOException {
        System.out.println("Enter account name:");
        String handle = sc.nextLine();
        try {
            if (handle.length() >= 1) {
                statuses = twitter.getUserTimeline(handle);
            }
            else {
                user = twitter.verifyCredentials().getScreenName();
                statuses = twitter.getUserTimeline();
            }
            System.out.println("Showing @" + user + "'s user timeline.");
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
        } catch(TwitterException e) {
                if(e.isCausedByNetworkIssue()) {
                    System.out.println("Please check your network and try again.");
                }
                else{
                    System.out.println("Failed to get timeline: " + e.getErrorMessage());
                }
            }
        }

    public void searchTweet() throws TwitterException, IOException {
        System.out.println("Enter the term you want to search:");
        String search = sc.nextLine();
        Query query = new Query(search);
        QueryResult result = twitter.search(query);
        for (Status status : result.getTweets()) {
            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }
    }
}

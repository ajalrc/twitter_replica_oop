
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Twitter twitter= new Twitter();
        twitter.postTweet(1,5,"The best teacher for the success is Mr.failure");

        // User 1's news feed should return a list with 1 tweet id -> [5].
        System.out.println(twitter.getNewsFeed(1));

// User 1 follows user 2.
        twitter.follow(1, 2);

// User 2 posts a new tweet (id = 6).
        twitter.postTweet(2, 6,"Definitely true and the second teacher is the empty pocket");

// User 1's news feed should return a list with 2 tweet ids -> [6, 5].
// Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
        System.out.println(twitter.getNewsFeed(1));

// User 1 unfollows user 2.
        twitter.unfollow(1, 2);

// User 1's news feed should return a list with 1 tweet id -> [5],
// since user 1 is no longer following user 2.
        System.out.println(twitter.getNewsFeed(1));
    }
}
class Twitter {
    class tweet {
        int tweettime;
        int tweetid;
        tweet next;
        String message;
        public tweet(int tweetid,int tweettime,String message){
            this.tweetid=tweetid;
            this.tweettime=tweettime;
            this.next=null;
            this.message=message;
        }
    }

    int time;
    HashMap<Integer, tweet> collecttweet;//this is to keep track of the who and what were the tweets.
    HashMap<Integer, Set<Integer>> collectusers;//this is to keep track of who followed whom

    /**
     * Initialize your data structure here.
     */
    public Twitter() {
        time=0;
        collecttweet=new HashMap<>();
        collectusers =new HashMap<>();
    }
    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId,String message) {
        /**
         * Here first I am looking if that user is in the followers table and so if he is
         * then he might have been tweeting from before so, I need to make sure that his latest
         * tweet comes first and so, we do that in the if loop using next poiting to previous
         * else we just add the tweet in the hashmap tweets
         */
        if(collecttweet.containsKey(userId)){
            tweet tweet=new tweet(tweetId,time++,message);
            tweet.next=collecttweet.get(userId);
            collecttweet.put(userId,tweet);
        }
        else{
            collecttweet.put(userId,new tweet(tweetId,time++,message));
        }

    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
     */

    public List<Dictionary<Integer,String>> getNewsFeed(int userId) {
        //System.out.println(collecttweet);
        //System.out.println(collectusers);
        /**
         * Here basically, first I am seeing if the user is in the followers map and if yes
         * add that tweet to queue and see if he has any followers in the and if yes then see if those
         * people have any tweets and all all the tweets in the queue and then comapre the time when
         * they were posted and have that order in the queue.
         */
        List<Dictionary<Integer,String>> list=new ArrayList<>();
        PriorityQueue<tweet> priorityQueue=new PriorityQueue<>(new Comparator<tweet>() {
            /**
             * Baiscally the comparator uses this logic to compare the time for which the tweets were posted.
             -1 : o1 < o2
             0 : o1 == o2
             +1 : o1 > o2
             */
            public int compare(tweet t1, tweet t2) {
                if((t1.tweettime-t2.tweettime)>0) return -1;
                else return 1;
            }
        });
        if(collecttweet.containsKey(userId)){
            priorityQueue.offer(collecttweet.get(userId));
        }
        if(collectusers.containsKey(userId)){
            for(Integer followeee:collectusers.get(userId)){
                if(collecttweet.containsKey(followeee)){
                    priorityQueue.offer(collecttweet.get(followeee));
                }
            }
        }


        while(!priorityQueue.isEmpty() && list.size()<10){
            tweet nexttweet=priorityQueue.poll();
            Dictionary<Integer,String> dictionary = new Hashtable<>();
            dictionary.put(nexttweet.tweettime,nexttweet.message);
            list.add(dictionary);
            //this below line is to make sure that if the same user has the tweet then arrange that in priority queue based
            //on the time it was posted.
            if(nexttweet.next!=null) priorityQueue.offer(nexttweet.next);
        }
        return list;
    }

    public void follow(int followerId, int followeeId) {
        if(followeeId==followerId) return;
        if(collectusers.containsKey(followerId)){
            collectusers.get(followerId).add(followeeId);
        }
        else{
            Set<Integer> newfollowee= new HashSet<>();
            newfollowee.add(followeeId);
            collectusers.put(followerId,newfollowee);
        }
        //System.out.println(collectusers);
    }
    /**Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow ( int followerId, int followeeId){
        if(followeeId==followerId) return;
        if(collectusers.containsKey(followerId)){
            collectusers.get(followerId).remove(followeeId);
        }
    }
}


import java.util.*;

public class Main {

    public static void main(String[] args) {

    }
}
class Twitter {
    class tweet {
        int tweettime;
        int tweetid;
        tweet next;
        public tweet(int tweetid,int tweettime){
            this.tweetid=tweetid;
            this.tweettime=tweettime;
            this.next=null;
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
    public void postTweet(int userId, int tweetId) {
        /**
         * Here first I am looking if that user is in the followers table and so if he is
         * then he might have been tweeting from before so, I need to make sure that his latest
         * tweet comes first and so, we do that in the if loop using next poiting to previous
         * else we just add the tweet in the hashmap tweets
         */
        if(collecttweet.containsKey(userId)){
            tweet tweet=new tweet(tweetId,time++);
            tweet.next=collecttweet.get(userId);
            collecttweet.put(userId,tweet);
        }
        else{
            collecttweet.put(userId,new tweet(tweetId,time++));
        }

    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
     */

    public List<Integer> getNewsFeed(int userId) {
        //System.out.println(collecttweet);
        //System.out.println(collectusers);
        /**
         * Here basically, first I am seeing if the user is in the followers map and if yes
         * add that tweet to queue and see if he has any followers in the and if yes then see if those
         * people have any tweets and all all the tweets in the queue and then comapre the time when
         * they were posted and have that order in the queue.
         */
        List<Integer> list=new ArrayList<>();
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
            list.add(nexttweet.tweetid);
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

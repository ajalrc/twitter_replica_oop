# twitter_replica_oop

Design a simplified version of Twitter where users can post tweets, follow/unfollow another user and is able to see the 10 most recent tweets in the user's news feed. Your design should support the following methods:

postTweet(userId, tweetId): Compose a new tweet. getNewsFeed(userId): Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. follow(followerId, followeeId): Follower follows a followee. unfollow(followerId, followeeId): Follower unfollows a followee.

Update that I made: Instead of the tweetid, I also need to write the twitter message and display the message. I used hashtable to keep track of the tweetid and the message/tweet.

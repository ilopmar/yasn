package yasn

import yasn.user.User
import yasn.timeline.Timeline

class YasnRedisService {
    static transactional = false

    def redisService

    void addFollower(User follower, User user) {
        if (user.id != follower.id) {
            redisService.sadd "followers.${user.id}", follower.id.toString()
        }
    }

    Boolean isFollower(User follower, User user) {
        return redisService.sismember("followers.${follower.id}", user.id.toString())
    }

    void updateTimeline(User user, Timeline timeline) {
        def t = System.nanoTime()

        println "Updating the timeline of the followers in Redis"

        def followers = this.followerIds(user)

        String timelineId = timeline.id.toString()

        redisService.withPipeline { pipeline ->
            // Add to user timeline
            pipeline.lpush("timeline.${user.id}", timelineId)

            // Add timeline object to timeline followers
            followers.eachWithIndex { followerId, idx ->
                if (idx % 10000 == 0) {
                    println idx
                }
                pipeline.lpush("timeline.${followerId}", timelineId)
            }
        }

        def t2 = System.nanoTime()
        println "Time --> " + ((t2 - t)/1000000)
   }

    private Set followerIds(User user) {
        println "Getting followers of ${user}"
        return redisService.smembers("followers.${user.id}")
    }
}

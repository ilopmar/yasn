import yasn.relation.FollowRelation
import yasn.timeline.Timeline
import yasn.user.Role
import yasn.user.User
import yasn.user.UserRole

class BootStrap {

    def fakerService
    def relationService
    def yasnRedisService
    def redisService

    def init = { servletContext ->
        this.createDummyData()
    }

    def destroy = {
    }

    private createDummyData() {
        def role = new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)
        def bioJustin = "#MusicMondays is in effect. Unlock #FilmFridays - SO MUCH LOVE FOR THE FANS...you are always there for me and I will always be there for you. MUCH LOVE. thanks All Around The World · http://youtube.com/justinbieber"
        def justinbieber = new User(username: 'justinbieber', name: 'Justin Bieber', password: 'pass', bio: bioJustin).save(flush: true, failOnError: true)
        UserRole.create(justinbieber, role)

        def bioHannah = "california face. with a down south rump. #BANGERZ PLUTO · http://mileycyrus.com"
        def hannahmontana = new User(username: 'hannahmontana', name: 'Hannah Montana', password: 'pass', bio: bioHannah).save(flush: true, failOnError: true)
        UserRole.create(hannahmontana, role)

        def bioJohn = "I'm John Doe :-)"
        def johndoe = new User(username: 'johndoe', name: 'John Doe', password: 'pass', bio: bioJohn).save(flush: true, failOnError: true)
        UserRole.create(johndoe, role)

        println "Creating users..."
        50.times { n ->
            String name = "${fakerService.esFirstName()} ${fakerService.esLastName()}"
            def tmpUser = new User(username: "user${n}", password: 'pass', name: name, bio: fakerService.paragraph(1)).save(flush: true, failOnError: true)
            UserRole.create(tmpUser, role)
        }

        println "Creating relations..."
        250.times {
            def userA = randomUser
            def userB = randomUser
            relationService.addFollower(userA, userB)
            yasnRedisService.addFollower(userA, userB)
        }

        // Justin <-> Hannah
        relationService.addFollower(justinbieber, hannahmontana)
        relationService.addFollower(hannahmontana, justinbieber)
        yasnRedisService.addFollower(justinbieber, hannahmontana)
        yasnRedisService.addFollower(hannahmontana, justinbieber)

        // John Doe -> Justin & Hannah
        relationService.addFollower(johndoe, hannahmontana)
        relationService.addFollower(johndoe, justinbieber)
        yasnRedisService.addFollower(johndoe, hannahmontana)
        yasnRedisService.addFollower(johndoe, justinbieber)

        // Clear timelines and followes for test users
        redisService.ltrim "timeline.1", -1, 0
        redisService.ltrim "timeline.2", -1, 0
        redisService.ltrim "timeline.3", -1, 0
        redisService.del "followers.1"
        redisService.del "followers.2"
        redisService.del "followers.3"

        // Create one million followers for justin and hannah
        println "Creating followers..."
        redisService.withPipeline { pipeline ->
            1000001.times {
                pipeline.sadd "followers.1", it.toString()
                pipeline.sadd "followers.2", it.toString()
            }
            // Create some followers for John
            301.times {
                pipeline.sadd "followers.3", it.toString()
            }

            // Removes the users from being "auto-followers"
            pipeline.srem "followers.1", "1"
            pipeline.srem "followers.2", "2"
            pipeline.srem "followers.3", "3"

            // Justin and Hannah don't follow John
            pipeline.srem "followers.3", "1"
            pipeline.srem "followers.3", "2"
        }

        println "Creating timelines..."
        1000.times { n ->
            def rndUser = randomUser
            def tl = new Timeline(user: rndUser, content: fakerService.paragraph(2)).save()

            if (n % 100 == 0) {
                def tl1 = new Timeline(user: justinbieber, content: fakerService.paragraph(2)).save()
                def tl2 = new Timeline(user: hannahmontana, content: fakerService.paragraph(2)).save()
            }
        }

        println "Creating timelines for users..."
        1000.times { idx ->
            def tlJustin = this.randomNumbers()
            def tlHannah = this.randomNumbers()
            def tlJohn = this.randomNumbers()
            redisService.lpush "timeline.1", tlJustin[idx].toString()
            redisService.lpush "timeline.2", tlHannah[idx].toString()
            redisService.lpush "timeline.3", tlJohn[idx].toString()
        }
    }

    private User getRandomUser() {
        User.executeQuery('from User order by random()', [max: 1])[0]
    }

    private List<Long> randomNumbers() {
        def list = (1..1000).collect { it }
        Collections.shuffle(list)
        return list
    }
}

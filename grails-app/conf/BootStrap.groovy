import yasn.relation.FollowRelation
import yasn.timeline.Timeline
import yasn.user.Role
import yasn.user.User
import yasn.user.UserRole

class BootStrap {

    def fakerService
    def relationService

    def init = { servletContext ->
        this.createDummyData()
    }

    def destroy = {
    }

    private createDummyData() {
        def role = new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)
        def user = new User(username: 'user', password: 'pass').save(flush: true, failOnError: true)
        UserRole.create(user, role)

        20.times { n ->
            def tmpUser = new User(username: "user${n}", password: 'pass').save(flush: true, failOnError: true)
            UserRole.create(tmpUser, role)
        }

        40.times {
            relationService.addFollower(randomUser, randomUser)
        }

        50.times {
            new Timeline(user: randomUser, content: fakerService.paragraph(1)).save(flush: true, failOnError: true)
        }
    }

    private User getRandomUser() {
        User.executeQuery('from User order by random()', [max: 1])[0]
    }
}

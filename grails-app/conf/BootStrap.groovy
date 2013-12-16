import yasn.relation.FollowRelation
import yasn.timeline.Timeline
import yasn.user.Role
import yasn.user.User
import yasn.user.UserRole

class BootStrap {

    def fakerService

    def init = { servletContext ->
        this.createDummyData()
    }

    def destroy = {
    }

    private createDummyData() {
        def role = new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)
        def user = new User(username: 'user', password: 'pass').save(flush: true, failOnError: true)
        UserRole.create(user, role)

        10.times { n ->
            def tmpUser = new User(username: "user${n}", password: 'pass').save(flush: true, failOnError: true)
            UserRole.create(tmpUser, role)

            new FollowRelation(user: user, follower: tmpUser).save(flush: true, failOnError: true)
        }

        50.times { n ->
            def rndUser = User.executeQuery('from User order by random()', [max: 1])
            new Timeline(user: rndUser, content: fakerService.paragraph(1)).save(flush: true, failOnError: true)
        }
    }
}

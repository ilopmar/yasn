import yasn.user.Role
import yasn.user.User
import yasn.user.UserRole

import yasn.relation.FollowRelation

class BootStrap {

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

            def fr = new FollowRelation(user: user, follower: tmpUser).save(flush: true, failOnError: true)
        }
    }
}

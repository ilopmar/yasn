package yasn.timeline

import yasn.user.User

class Timeline {

    User user
    String content
    Date dateCreated

    static constraints = {
    }

    static mapping = {
        content type: 'text'
    }

}
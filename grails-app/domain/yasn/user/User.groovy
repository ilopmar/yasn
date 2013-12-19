package yasn.user

class User {

    transient springSecurityService

    String username
    String password
    String name
    String bio
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Date dateCreated
    Date lastUpdated

    static transients = ['springSecurityService']

    static constraints = {
        username blank: false, unique: true
        password blank: false
        bio nullable: true
    }

    static mapping = {
        table "yasn_user"
        password column: '`password`'
        bio type: 'text'
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}

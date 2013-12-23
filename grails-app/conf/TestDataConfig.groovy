testDataConfig {
    sampleData {
        'yasn.user.User' {
            username = {-> "user${String.valueOf(new Date().time)}" }
        }
    }
}
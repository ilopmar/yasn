class UrlMappings {

    static mappings = {

        name logoutIndex: "/logout" {controller = 'logout'; action = 'index'}

        name root: "/" { controller = 'login'; action = 'index' }
        name home: "/home" { controller = 'timeline'; action = 'home' }

        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }

        "500"(view:'/error')
    }
}

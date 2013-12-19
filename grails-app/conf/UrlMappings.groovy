class UrlMappings {

    static mappings = {

        name logoutIndex: "/logout" {controller = 'logout'; action = 'index'}

        name root: "/" { controller = 'login'; action = 'index' }
        name home: "/home" { controller = 'timeline'; action = 'home' }
        name publish: "/publish" { controller = 'timeline'; action = [POST: 'publish'] }
        name timeline: "/timeline" { controller = 'timeline'; action = 'timeline' }

        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }

        "500"(view:'/error')
    }
}

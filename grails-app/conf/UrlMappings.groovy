class UrlMappings {

    static mappings = {

        name logoutIndex: "/logout" {controller = 'logout'; action = 'index'}

        name root: "/" { controller = 'login'; action = [GET: 'index'] }

        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }

        "500"(view:'/error')
    }
}

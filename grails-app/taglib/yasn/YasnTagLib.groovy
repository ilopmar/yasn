package yasn

class YasnTagLib {

    static namespace = 'yasn'

    def parseTextToLink = { attrs ->
        def text = (attrs.text ?: "").encodeAsHTML()
        def newtext = ""

        if (text) {
            // expresion for two types url
            // - http://www.domain.com/page
            // - www.domain.subdomain.subsub.com/pepe
            // - NO type kaleidos.net
            def exp = /((https?|ftp|file):\/\/[a-zA-Z0-9+&@#\/%?=~_|!:,.;\-]*[a-zA-Z0-9+&@#\/%=~_\-|])|([a-zA-Z0-9]+\.[a-zA-Z0-9+&@#\/%=~_\-|]+\.([a-zA-Z0-9+&@#\/%=~_\-|]\.?)+)/
            def matcher = ( text =~ exp )
            newtext = text.replaceAll(exp, {"""<a rel='nofollow' target='_blank' href='${it[0].contains("://")?it[0]:"http://"+it[0]}'>${it[0]}</a>"""}).replaceAll('\n', '<br/>')
        }

        out << newtext
    }
}
<g:each in="${timeline}" var="item">
    <g:render template="/timeline/item" model="[timeline: item]" />
</g:each>
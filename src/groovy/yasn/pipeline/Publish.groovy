package yasn.pipeline

import yasn.ro.PublishRequest

interface Publish {
    PublishRequest publish(PublishRequest publishRequest)
}
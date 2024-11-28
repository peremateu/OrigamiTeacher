package furhatos.app.origamiteacher.flow

import furhatos.app.origamiteacher.flow.main.Idle
import furhatos.app.origamiteacher.setting.distanceToEngage
import furhatos.app.origamiteacher.setting.maxNumberOfUsers
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice

val Init : State = state() {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        furhat.voice = Voice("Matthew")
        /** start the interaction */
        goto(Idle)
    }
}

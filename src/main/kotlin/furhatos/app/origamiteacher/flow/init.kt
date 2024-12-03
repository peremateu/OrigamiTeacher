package furhatos.app.origamiteacher.flow

import furhatos.app.origamiteacher.flow.main.Idle
import furhatos.app.origamiteacher.setting.distanceToEngage
import furhatos.app.origamiteacher.setting.maxNumberOfUsers
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.flow.kotlin.voice.Voice

val Init : State = state() {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        furhat.voice = Voice("Alex")
        /** start the interaction */
        goto(Idle)
    }
}

package furhatos.app.origamiteacher.flow

import furhatos.app.origamiteacher.flow.main.Idle
import furhatos.app.origamiteacher.setting.distanceToEngage
import furhatos.app.origamiteacher.setting.encouragement
import furhatos.app.origamiteacher.setting.maxNumberOfUsers
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice

val Init : State = state() {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        furhat.voice = Voice("Alex")
        /** start the interaction */
        onButton("Encouragement", color = Color.Green, size = Size.Large) {
            encouragement = 1
            goto(Idle)
        }
        onButton("No encouragement", color = Color.Red, size = Size.Large) {
            encouragement = 0
            goto(Idle)
        }

    }
}

package furhatos.app.origamiteacher.flow

import furhatos.app.origamiteacher.FurhatAskedAttention
import furhatos.app.origamiteacher.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.skills.emotions.UserGestures
import furhatos.app.origamiteacher.setting.attention_calls



val Parent: State = state {

    onUserLeave(instant = true) {
        when {
            users.count == 0 -> goto(Idle)
            it == users.current -> furhat.attend(users.other)
        }
    }

    onUserEnter(instant = true) {
        furhat.glance(it)
    }

}
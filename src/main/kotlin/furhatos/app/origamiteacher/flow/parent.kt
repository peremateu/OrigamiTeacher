package furhatos.app.origamiteacher.flow

import furhatos.app.origamiteacher.FurhatAskedAttention
import furhatos.app.origamiteacher.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.skills.emotions.UserGestures


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

    onUserAttend(instant = true) {user ->
        if (!user.isAttendingFurhat()) {furhat.say("Can you please pay attention?", abort = true)
            users
            .current.FurhatAskedAttention = true
        } else {
            if (users.current.FurhatAskedAttention) {
                furhat.say("Thank you", abort = true)
                users.current.FurhatAskedAttention = false
            }
        }
    }

    onUserGesture(UserGestures.Smile) {
        furhat.say("I'm glad you are having a good time, you have a lovely smile")
    }


}
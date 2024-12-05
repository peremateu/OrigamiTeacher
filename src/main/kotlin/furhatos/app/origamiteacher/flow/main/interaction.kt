package furhatos.app.origamiteacher.flow.main

import furhatos.app.origamiteacher.FurhatAskedAttention
import furhatos.app.origamiteacher.flow.Parent
import furhatos.app.origamiteacher.nlu.RequestNextStep
import furhatos.app.origamiteacher.nlu.RequestRepeat
import furhatos.app.origamiteacher.setting.attention_calls
import furhatos.app.origamiteacher.setting.steps
import furhatos.app.origamiteacher.step
import furhatos.app.origamiteacher.timeOfCompliments
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.skills.emotions.UserGestures
import java.time.Instant

val Introduction = state(parent = Parent) {
    onEntry {
            users.current.timeOfCompliments = Instant.now().epochSecond.toInt()
            /*furhat.say("I will give you an instruction and wait for you to complete it.")
            furhat.say("When you are done, you can tell me to give you the next step.")
            furhat.say("If you need me to repeat the instruction again, just tell me.")*/
            furhat.say("I will be your personal origami instructor today.")
            furhat.ask("Are you ready?")
    }
    onResponse<Yes> {
        furhat.say("Perfect!")
        goto(GivingSteps)
    }

    onResponse<No> {
        /*furhat.say("Okay, let me repeat it")
        furhat.say("I will give you an instruction and wait for you to complete it.")
        furhat.say("When you are done, you can tell me to give you the next step.")
        furhat.say("If you need me to repeat the instruction again, just tell me.")*/
        furhat.say("Take a deep breath.")
        furhat.ask("Are you ready now?")
    }
}

val GivingSteps = state(parent = Parent) {
    onEntry {
        furhat.say("Start by grabbing your piece of paper")
        furhat.listen()
    }

    onResponse<RequestRepeat> {
        val step = users.current.step
        furhat.say("Okay, here goes the same instruction.")
        furhat.say(steps[step])
        furhat.listen()
    }

    onResponse<RequestNextStep> {
        users.current.step++
        val step = users.current.step
        furhat.say(steps[step])
        furhat.listen()
    }

    onUserAttend() {user ->
        if (!user.isAttendingFurhat()) {
            furhat.say(attention_calls[users.current.FurhatAskedAttention], abort = true)
            if (users.current.FurhatAskedAttention < 3) {
                users.current.FurhatAskedAttention++
            }
            furhat.listen()
        } else {
            if (users.current.FurhatAskedAttention > 0) {
                furhat.say("Thank you", abort = true)
                delay(500)
                furhat.say(steps[users.current.step])
                furhat.listen()
            }
        }
    }

    onUserGesture(UserGestures.Smile) {
        if (Instant.now().epochSecond.toInt() - users.current.timeOfCompliments > 10) {
            users.current.timeOfCompliments = Instant.now().epochSecond.toInt()
            furhat.say("I'm glad you are having a good time, you have a lovely smile")
            furhat.listen()
        } else {
            furhat.gesture(Gestures.Smile)
        }

    }

}

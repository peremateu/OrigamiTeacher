package furhatos.app.origamiteacher.flow.main

import furhatos.app.origamiteacher.flow.Parent
import furhatos.app.origamiteacher.nlu.RequestNextStep
import furhatos.app.origamiteacher.nlu.RequestRepeat
import furhatos.app.origamiteacher.setting.comments
import furhatos.app.origamiteacher.setting.encouragement
import furhatos.app.origamiteacher.setting.steps
import furhatos.app.origamiteacher.step
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Introduction = state(parent = Parent) {
    onEntry {
            furhat.say("I will give you an instruction and wait for you to complete it.")
            furhat.say("When you are done, you can tell me to give you the next step.")
            furhat.say("If you need me to repeat the instruction again, just tell me.")
            furhat.ask("Is it clear?")
    }
    onResponse<Yes> {
        furhat.say("Perfect!")
        goto(GivingSteps)
    }

    onResponse<No> {
        furhat.say("Okay, let me repeat it")
        furhat.say("I will give you an instruction and wait for you to complete it.")
        furhat.say("When you are done, you can tell me to give you the next step.")
        furhat.say("If you need me to repeat the instruction again, just tell me.")
        furhat.ask("Is it clear now?")
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
        if (step in comments) {
            furhat.say(comments[step]?.get(encouragement) ?: "There was an error (this will never happen here)")
            furhat.listen()
        }
        furhat.say(steps[step])
        furhat.listen()

        /* add the head movements */
    }
}

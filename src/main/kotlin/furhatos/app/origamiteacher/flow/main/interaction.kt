package furhatos.app.origamiteacher.flow.main

import furhatos.app.origamiteacher.flow.Parent
import furhatos.app.origamiteacher.nlu.RequestNextStep
import furhatos.app.origamiteacher.nlu.RequestRepeat
import furhatos.app.origamiteacher.setting.*
import furhatos.app.origamiteacher.step
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.Location

val Introduction = state(parent = Parent) {
    onEntry {
            if (avoidRepeat == 0) {
                furhat.say("My name is Furhat, and I'll be your personal origami instructor")
                furhat.attend(Location.DOWN)
                furhat.say("In front of you there is a piece of paper that we'll use to fold a paper fish together.")
                furhat.attend(users.current)
                furhat.say("Just tell me when you are done with a step and I will give you the next one.")
                furhat.say("I can also repeat it if you want.")
                avoidRepeat++
            }
            furhat.ask("Is everything clear?")
    }
    onResponse<Yes> {
        furhat.say("Perfect!")
        if (avoidRepeat == 1) avoidRepeat = 0
        goto(GivingSteps)
    }

    onResponse<No> {
        furhat.say("Okay, let me repeat it")
        furhat.attend(Location.DOWN)
        furhat.say("In front of you there is a piece of paper that we'll use to fold a paper fish together.")
        furhat.attend(users.current)
        furhat.say("Just tell me when you are done with a step and I will give you the next one.")
        furhat.say("I can also repeat it if you want.")
        furhat.ask("Is it clear now?")
    }
}

val GivingSteps = state(parent = Parent) {
    onEntry {
        val step = users.current.step
        if (avoidRepeat == 0) {
            furhat.say(comments[step]?.get(encouragement) ?: "There was an error (this will never happen here)")
            avoidRepeat++
        }
        furhat.attend(users.current)
        furhat.say(steps[step])
        furhat.attend(Location.DOWN)
        furhat.listen(timeout = 30000)
    }

    onResponse<RequestRepeat> {
        val step = users.current.step
        furhat.attend(users.current)

        furhat.say("Okay, here goes the same instruction.")
        furhat.say(steps[step])
        furhat.attend(Location.DOWN)
        furhat.listen(timeout = 30000)
    }

    onResponse<RequestNextStep> {
        users.current.step++
        val step = users.current.step
        furhat.attend(users.current)
        if (step in comments) {
            furhat.say(comments[step]?.get(encouragement) ?: "There was an error (this will never happen here)")
            delay(1000)
        }
        furhat.attend(users.current)
        furhat.say(steps[step])
        furhat.attend(Location.DOWN)

        if(users.current.step == steps.size - 1) {
            delay(500)
            furhat.say("If you want, you can draw the eye and some stripes to make it look nice.")
            furhat.say(comments[step]?.get(encouragement) ?: "There was an error (this will never happen here)")
            terminate()
        }else{
            furhat.listen(timeout = 30000)
        }

    }
}

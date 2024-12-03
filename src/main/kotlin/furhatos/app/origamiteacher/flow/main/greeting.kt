package furhatos.app.origamiteacher.flow.main

import furhatos.app.origamiteacher.flow.Parent
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val Greeting : State = state(Parent) {
    onEntry {
        random(
            {   furhat.say("Hi there") },
            {   furhat.say("Oh, hello there") }
        )
        /*furhat.say("I am here to teach you to do a fish with origami.")*/
        goto(Introduction)
    }
}
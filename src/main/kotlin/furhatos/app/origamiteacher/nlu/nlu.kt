package furhatos.app.origamiteacher.nlu

import furhatos.nlu.ComplexEnumEntity
import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.nlu.ListEntity
import furhatos.nlu.common.Number
import furhatos.util.Language

class RequestRepeat: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Can you repeat?",
            "Repeat",
            "Can you repeat the step?")
    }
}

class RequestNextStep: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Next step",
            "I'm done",
            "I have done it",
            "Continue",
            "Okay",
            "I have it")
    }
}
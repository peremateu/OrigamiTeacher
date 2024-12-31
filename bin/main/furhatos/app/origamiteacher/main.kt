package furhatos.app.origamiteacher

import furhatos.app.origamiteacher.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class OrigamiTeacherSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}

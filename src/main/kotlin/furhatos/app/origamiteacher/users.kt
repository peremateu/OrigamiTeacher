package furhatos.app.origamiteacher

import furhatos.flow.kotlin.NullSafeUserDataDelegate
import furhatos.records.User

// Associate an order to a user
var User.step by NullSafeUserDataDelegate { 0 }

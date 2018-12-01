package com.test.contactapp.data.objbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
class Email {
    @Id
    var id: Long = 0
    var email_address: String = ""
    lateinit var contact: ToOne<Contact>
    lateinit var emailType: ToOne<EmailTypeLookup>
}
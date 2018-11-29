package com.test.contactapp.data.objbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne



@Entity
class Phone {

    @Id
    var phoneId: Long = 0
    var phone_number: String = ""
    //phone type
    var phone_type: String = ""

    lateinit var contact: ToOne<Contact>
}
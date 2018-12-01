package com.test.contactapp.data.objbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique
import io.objectbox.relation.ToOne



@Entity
class Phone {

    @Id
    var phoneId: Long = 0

    @Unique
    var phone_number: String = ""

    //phone type
    lateinit var phone_type :ToOne<PhoneTypeLookup>

    lateinit var contact: ToOne<Contact>


}
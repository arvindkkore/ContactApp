package com.test.contactapp.data.objbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany


@Entity
class Contact {
    @Id
    var contactid: Long = 0
    var first_name: String = ""
    var last_name: String = ""
    var nickname: String = ""
    var note: String = ""

    //list of contact
    lateinit var phones:ToMany<Phone>
    //list of address
    lateinit var addresses:ToMany< Address>
    //list of emails
    lateinit var emails:ToMany<Email>

    // list of websites
    lateinit var websites:ToMany<Website>





}
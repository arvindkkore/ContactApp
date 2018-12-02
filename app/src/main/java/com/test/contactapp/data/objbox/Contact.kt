package com.test.contactapp.data.objbox

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne


@Entity
class Contact {
    @Id
    var contactid: Long = 0
    @Unique
    var first_name: String = ""
    var last_name: String = ""
    var nickname: String = ""
    var note: String = ""
    var companyName :String = ""
    var designation :String = ""

    //list of Phone numbers
    @Backlink
    lateinit var phones:ToMany<Phone>


    //list of address
    @Backlink
    lateinit var addresses:ToMany< Address>

    //list of emails
    @Backlink
    lateinit var emails:ToMany<Email>

    // list of websites
    @Backlink
    lateinit var websites:ToMany<Website>

    // list of dates
    @Backlink
    lateinit var dates:ToMany<Date>




}
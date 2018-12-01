package com.test.contactapp.data.objbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
class AddressTypeLookup
{
    @Id
    var id: Long = 0
    @Unique
    var addressType: String = ""
}
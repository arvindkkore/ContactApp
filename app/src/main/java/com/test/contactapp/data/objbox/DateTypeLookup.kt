package com.test.contactapp.data.objbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
data class DateTypeLookup(@Id var id: Long=0,@Unique var dateType: String ="")
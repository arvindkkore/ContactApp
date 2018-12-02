package com.test.contactapp.data.models

import com.test.contactapp.data.objbox.AddressTypeLookup
import com.test.contactapp.data.objbox.DateTypeLookup
import com.test.contactapp.data.objbox.EmailTypeLookup
import com.test.contactapp.data.objbox.PhoneTypeLookup

data class LookupData(
    var phoneLookup: List<PhoneTypeLookup>,
    var emaiLookup: List<EmailTypeLookup> ,
    var addressLookup: List<AddressTypeLookup>,
    var dateLookup: List<DateTypeLookup> )

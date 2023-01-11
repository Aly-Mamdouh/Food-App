package com.alimamdouh.food.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealConverter {

    @TypeConverter
    fun fromAnyToString(attr:Any?):String{
        if(attr==null)
            return ""
        return attr.toString()
    }
    @TypeConverter
    fun fromStringToAny(attr:String?):Any{
        if(attr==null)
            return ""
        return attr
    }
}
package com.saddam.challenge4binar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
//data class user act as an entity, extend to parcelable
@Entity
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) var id : Int?,
    @ColumnInfo(name = "username") var username : String?,
    @ColumnInfo(name = "email") var email : String?,
    @ColumnInfo(name = "password") var password : String?,
    @ColumnInfo(name = "konfirmasi_password") var konfirmasiPassword : String?
) : Parcelable
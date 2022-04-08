package com.saddam.challenge4binar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//Note data class, sebagai entity. Extend ke parcelable
@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) var id : Int?,
    @ColumnInfo(name = "judul") var judul : String?,
    @ColumnInfo(name = "catatan") var catatan : String?
) : Parcelable
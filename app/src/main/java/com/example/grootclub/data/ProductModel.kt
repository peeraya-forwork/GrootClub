package com.example.grootclub.data

import android.os.Parcel
import android.os.Parcelable

class ProductModel {
    data class Stadium(
        val id: Int = 0,
        val section: String? = "",
        val imv: String? = "",
        val name: String? = "",
        val detail: String? = ""
    ): Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(section)
            parcel.writeString(imv)
            parcel.writeString(name)
            parcel.writeString(detail)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Stadium> {
            override fun createFromParcel(parcel: Parcel): Stadium {
                return Stadium(parcel)
            }

            override fun newArray(size: Int): Array<Stadium?> {
                return arrayOfNulls(size)
            }
        }
    }
}
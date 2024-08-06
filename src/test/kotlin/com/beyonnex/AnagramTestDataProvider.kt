package com.beyonnex

object AnagramTestDataProvider {
    @JvmStatic
    fun provideEmptyOrNullInputTestData(): List<Array<String?>> {
        return listOf(
            arrayOf(null, "silent"),
            arrayOf("listen", null),
            arrayOf(null, null),
            arrayOf("", "silent"),
            arrayOf("listen", "")
        )
    }
}
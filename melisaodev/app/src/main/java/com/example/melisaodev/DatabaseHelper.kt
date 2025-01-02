package com.example.melisaodev

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "NewsDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "news"
        private const val COLUMN_ID = "id"
        private const val COLUMN_CATEGORY = "category"
        private const val COLUMN_IMAGE = "image"
        private const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CATEGORY TEXT,
                $COLUMN_IMAGE INTEGER,
                $COLUMN_DESCRIPTION TEXT
            )
        """.trimIndent()
        
        db.execSQL(createTable)
        
        // Varsayılan verileri ekle
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    private fun insertInitialData(db: SQLiteDatabase) {
        val newsData = arrayOf(
            NewsItem("Dünya", R.drawable.listdunya, """
                Son Dakika: Dünya genelinde önemli gelişmeler yaşanıyor!
                
                • İklim değişikliği ile mücadele kapsamında yeni anlaşmalar imzalandı
                • BM Genel Kurulu'nda küresel sorunlar masaya yatırıldı
                • Uzay araştırmalarında çığır açan keşifler yapıldı
                • Uluslararası barış görüşmeleri devam ediyor
                
                Dünya gündeminden seçilen önemli başlıklar ve detaylı analizler için haberin devamı...
            """.trimIndent()),
            
            NewsItem("Ekonomi", R.drawable.ekonomi, """
                Ekonomi Gündemi: Piyasalarda Son Durum
                
                • Merkez Bankası faiz kararını açıkladı
                • Döviz kurlarında son durum ve uzman yorumları
                • Borsa İstanbul'da günün kazandıranları
                • Altın fiyatlarında son gelişmeler
                • Kripto para piyasasındaki dalgalanmalar
                
                Detaylı piyasa analizleri ve uzman görüşleri için tıklayın...
            """.trimIndent()),
            
            NewsItem("Sanat", R.drawable.sanat, """
                Sanat Dünyasından Özel Haberler
                
                • İstanbul Modern'de yeni sergi: "Çağdaş Sanatın İzinde"
                • Dünyaca ünlü ressamın eserleri Türkiye'de
                • Devlet Tiyatroları'nın yeni sezon programı açıklandı
                • Uluslararası Film Festivali başlıyor
                • Genç sanatçılardan dikkat çeken projeler
                
                Sanat dünyasının nabzını tutan haberler için okumaya devam edin...
            """.trimIndent()),
            
            NewsItem("Spor", R.drawable.spor, """
                Spor Gündemi: Öne Çıkan Gelişmeler
                
                • Süper Lig'de haftanın maç sonuçları
                • Milli takımımızdan önemli galibiyet
                • Şampiyonlar Ligi'nde son durum
                • Basketbol Süper Ligi'nde heyecan dorukta
                • Transfer döneminde sürpriz gelişmeler
                
                Spor dünyasından tüm gelişmeler ve maç analizleri için tıklayın...
            """.trimIndent()),
            
            NewsItem("Magazin", R.drawable.magazin, """
                Magazin Dünyasının Gündem Yaratan Haberleri
                
                • Ünlü çiftten sürpriz evlilik kararı
                • Yılın en çok beklenen film galası gerçekleşti
                • Ödüllü oyuncudan yeni proje müjdesi
                • Müzik dünyasında yeni işbirlikleri
                • Sosyal medyanın konuştuğu magazin olayları
                
                Magazin dünyasının en özel haberleri ve röportajları için devamını okuyun...
            """.trimIndent())
        )

        newsData.forEach { news ->
            val values = ContentValues().apply {
                put(COLUMN_CATEGORY, news.category)
                put(COLUMN_IMAGE, news.imageResId)
                put(COLUMN_DESCRIPTION, news.description)
            }
            db.insert(TABLE_NAME, null, values)
        }
    }

    fun getNewsByCategory(category: String): NewsItem? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_CATEGORY, COLUMN_IMAGE, COLUMN_DESCRIPTION),
            "$COLUMN_CATEGORY = ?",
            arrayOf(category),
            null,
            null,
            null
        )

        Log.d("DatabaseHelper", "Searching for category: $category")
        
        return if (cursor.moveToFirst()) {
            val imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            Log.d("DatabaseHelper", "Found description: $description")
            cursor.close()
            NewsItem(category, imageResId, description)
        } else {
            Log.e("DatabaseHelper", "No data found for category: $category")
            cursor.close()
            null
        }
    }
}

data class NewsItem(
    val category: String,
    val imageResId: Int,
    val description: String
) 
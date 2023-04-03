package com.example.sortowaniav2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sortowania: Rabina-Karpa | Bayera-Moore'a | KMP(Knutha-Morrisa-Prata) | Brute Force

        var tab : IntArray = intArrayOf()

        fun removeLastItem(array: IntArray): IntArray {
            return array.copyOf(array.lastIndex)
        }

        fun czysc() {
            for (i in 0..tab.size - 1) {
                tab = removeLastItem(tab)
            }
        }

        // sortowanie Rabina-Karpa
        fun rabinKarpSort(arr: IntArray) {
            val n = arr.size
            if (n <= 1) return

            val bucketSize = 10 // rozmiar kubełka
            val buckets = Array(bucketSize) { mutableListOf<Int>() }

            // wyznaczenie wartości maksymalnej i obliczenie liczby cyfr w każdej liczbie
            var maxVal = arr[0]
            for (i in 1 until n) {
                if (arr[i] > maxVal) {
                    maxVal = arr[i]
                }
            }
            val numDigits = (Math.log10(maxVal.toDouble()) + 1).toInt()

            // sortowanie kubełkowe z wykorzystaniem hashowania
            var exp = 1
            for (i in 0 until numDigits) {
                for (j in 0 until n) {
                    val index = (arr[j] / exp) % bucketSize
                    buckets[index].add(arr[j])
                }
                var pos = 0
                for (j in 0 until bucketSize) {
                    for (k in 0 until buckets[j].size) {
                        arr[pos++] = buckets[j][k]
                    }
                    buckets[j].clear()
                }
                exp *= bucketSize
            }
        }

        // sortowanie Bayera-Moore'a
        fun bayerMooreSort(arr: IntArray) {
            val n = arr.size
            val bucketStarts = IntArray(256) { 0 }
            val bucket1 = IntArray(256) { 0 }
            val bucket2 = IntArray(256) { 0 }
            var currentBucket = 0

            for (shift in 0 until 32 step 8) {
                for (i in 0 until 256) {
                    bucket1[i] = 0
                }
                for (i in 0 until n) {
                    val digit = (arr[i] ushr shift) and 0xff
                    bucket1[digit]++
                }
                bucketStarts[0] = 0
                for (i in 1 until 256) {
                    bucketStarts[i] = bucketStarts[i-1] + bucket1[i-1]
                }
                for (i in 0 until n) {
                    val digit = (arr[i] ushr shift) and 0xff
                    bucket2[bucketStarts[digit]] = arr[i]
                    bucketStarts[digit]++
                }
                System.arraycopy(bucket2, 0, arr, 0, n)
            }
        }


        fun losuj(ile: Int, ktore: Int) {
            czysc()
            for (i in 0..ile - 1) {
                tab += (1..9).random()
            }

            if(ktore == 1) // Rabina-Karpa
                rabinKarpSort(tab)
            else if(ktore == 2) // Bayer-Moore
                bayerMooreSort(tab)

        }



        findViewById<Button>(R.id.button).setOnClickListener {
            var rkStart = System.currentTimeMillis()
            for(i in 0..findViewById<EditText>(R.id.iler).text.toString().toInt())
                losuj(findViewById<EditText>(R.id.ilee).text.toString().toInt(), 1)
            var rkTime = System.currentTimeMillis() - rkStart;
            findViewById<TextView>(R.id.rklbl).text = rkTime.toString() + " milisekund"

            var bmStart = System.currentTimeMillis()
            for(i in 0..findViewById<EditText>(R.id.iler).text.toString().toInt())
                losuj(findViewById<EditText>(R.id.ilee).text.toString().toInt(), 2)
            var bmTime = System.currentTimeMillis() - bmStart;
            findViewById<TextView>(R.id.bmlbl).text = bmTime.toString() + " milisekund"

            var kmpStart = System.currentTimeMillis()
            for(i in 0..findViewById<EditText>(R.id.iler).text.toString().toInt())
                losuj(findViewById<EditText>(R.id.ilee).text.toString().toInt(), 3)
            var kmpTime = System.currentTimeMillis() - kmpStart;
            findViewById<TextView>(R.id.kmplbl).text = kmpTime.toString() + " milisekund"

            var bfStart = System.currentTimeMillis()
            for(i in 0..findViewById<EditText>(R.id.iler).text.toString().toInt())
                losuj(findViewById<EditText>(R.id.ilee).text.toString().toInt(), 4)
            var bfTime = System.currentTimeMillis() - bfStart;
            findViewById<TextView>(R.id.bflbl).text = bfTime.toString() + " milisekund"


        }


    }
}